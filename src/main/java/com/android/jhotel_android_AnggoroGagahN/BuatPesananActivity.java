package com.android.jhotel_android_AnggoroGagahN;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {
    private int currentUserid, banyakHari, idHotel;
    private double tariff;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);
        Intent buatPesananIntent = getIntent();
        Bundle b = buatPesananIntent.getExtras();
        if(b!=null){
            currentUserid = b.getInt("id_customer");
            idHotel = b.getInt("id_hotel");
            tariff = b.getDouble("dailyTariff");
            roomNumber = b.getString("nomor_kamar");
        }

        final TextView textViewRoomNumber = findViewById(R.id.room_number);
        final TextView textViewTariff = findViewById(R.id.tariff);
        final TextView textViewTotalBiaya = findViewById(R.id.total_biaya);
        final EditText durasiInput = findViewById(R.id.durasi_hari);
        final Button pesanButton = findViewById(R.id.pesan);
        final Button hitungButton = findViewById(R.id.hitung);
        final Button kembaliButton = findViewById(R.id.kembali);

        pesanButton.setVisibility(View.INVISIBLE);
        kembaliButton.setVisibility(View.INVISIBLE);
        textViewRoomNumber.setText(roomNumber);
        textViewTariff.setText(String.valueOf(tariff));
        textViewTotalBiaya.setText("0");

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(durasiInput.getText().toString())){
                    durasiInput.setError("Durasi is required!");
                }else {
                    banyakHari = Integer.parseInt(durasiInput.getText().toString());
                    textViewTotalBiaya.setText(String.valueOf(tariff * banyakHari));
                    pesanButton.setVisibility(View.VISIBLE);
                    hitungButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String jumlah_hari = String.valueOf(banyakHari);
                final String id_customer = String.valueOf(currentUserid);
                final String id_hotel = String.valueOf(idHotel);
                final String nomor_kamar = roomNumber;
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Pesan Sukses").create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Pesan Gagal.").create().show();
                        }
                    }
                };
                BuatPesananRequest buatPesanRequest = new BuatPesananRequest
                        (jumlah_hari,id_customer,id_hotel,nomor_kamar, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesanRequest);
                kembaliButton.setVisibility(View.VISIBLE);
                pesanButton.setVisibility(View.INVISIBLE);
            }
        });

        kembaliButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BuatPesananActivity.this, DashboardActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id_customer",currentUserid);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }
}
