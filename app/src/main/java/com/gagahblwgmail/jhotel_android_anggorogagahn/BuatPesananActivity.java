package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class BuatPesananActivity extends AppCompatActivity
{
    private int currentUserId, banyakHari, idHotel;
    private double tariff;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        final TextView room_number = findViewById(R.id.room_number);
        final TextView tariffView = findViewById(R.id.tariff);
        final EditText durasi = findViewById(R.id.durasi_hari);
        final TextView total = findViewById(R.id.total_biaya);
        final Button hitungButton = findViewById(R.id.hitung);
        final Button pesanButton = findViewById(R.id.pesan);

        Intent i = getIntent();
        currentUserId = i.getIntExtra("idCustomer", 0);
        idHotel = i.getIntExtra("idHotel", 0);
        tariff = i.getDoubleExtra("tariff", 0);
        roomNumber = i.getStringExtra("roomNumber");

        pesanButton.setVisibility(View.INVISIBLE);
        room_number.setText(roomNumber);
        tariffView.setText(String.valueOf(this.tariff));
        total.setText("0");

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banyakHari = Integer.valueOf(durasi.getText().toString());

                BigDecimal bd1 = new BigDecimal(tariff);
                BigDecimal bd2 = new BigDecimal(Double.valueOf(String.valueOf(banyakHari)));
                BigDecimal result = bd1.multiply(bd2);
                total.setText(String.valueOf(result.doubleValue()));
                hitungButton.setVisibility(View.INVISIBLE);
                pesanButton.setVisibility(View.VISIBLE);


            }
        });

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Pesanan sukses")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Pesanan gagal dibuat")
                                    .create()
                                    .show();
                        }
                    }
                };

                BuatPesananRequest buatPesananRequest = new BuatPesananRequest(banyakHari, currentUserId, idHotel, roomNumber, responseListener);
                RequestQueue queue = newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesananRequest);
            }
        });
    }
}