package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class SelesaiPesananActivity extends AppCompatActivity {

    private int idCustomer;
    private int idPesanan;
    private double tariff;
    private int jumlahHari;
    private String tanggalPesan;

    private Button selesaiButton;
    private Button cancelButton;
    private TextView id_pesanan;
    private TextView tarif;
    private TextView jumlah_hari;
    private TextView tanggal_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        Intent i = getIntent();
        idCustomer = i.getIntExtra("idCustomer", 0);

        selesaiButton = findViewById(R.id.selesai);
        cancelButton = findViewById(R.id.batal);
        id_pesanan = findViewById(R.id.idPesanan);
        tarif = findViewById(R.id.tariff);
        jumlah_hari = findViewById(R.id.jumlahHari);
        tanggal_pesan = findViewById(R.id.tanggalPesan);

        selesaiButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        id_pesanan.setVisibility(View.INVISIBLE);
        tanggal_pesan.setVisibility(View.INVISIBLE);
        tarif.setVisibility(View.INVISIBLE);
        jumlah_hari.setVisibility(View.INVISIBLE);

        fetchPesanan();

        selesaiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan berhasil.");
                                builder.create();
                                builder.show();

                                selesaiButton.setVisibility(View.INVISIBLE);
                            }
                        } catch(JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan gagal.");
                            builder.create();
                            builder.show();
                        }


                    }
                };

                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(idPesanan, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan berhasil dibatalkan.");
                                builder.create();
                                builder.show();

                                selesaiButton.setVisibility(View.INVISIBLE);
                            }
                        } catch(JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan gagal dibatalkan.");
                            builder.create();
                            builder.show();
                        }


                    }
                };

                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(idPesanan, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananBatalRequest);
            }
        });
    }

    protected void fetchPesanan()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    JSONObject pesanan = jsonResponse.getJSONObject(0);
                    if(jsonResponse != null) {
                        selesaiButton.setVisibility(View.VISIBLE);
                        cancelButton.setVisibility(View.VISIBLE);
                        id_pesanan.setVisibility(View.VISIBLE);
                        tanggal_pesan.setVisibility(View.VISIBLE);
                        tarif.setVisibility(View.VISIBLE);
                        jumlah_hari.setVisibility(View.VISIBLE);

                        idPesanan = pesanan.getInt("id");
                        tanggalPesan = pesanan.getString("tanggalPesan");
                        tariff = pesanan.getJSONObject("room").getDouble("dailyTariff");
                        jumlahHari = pesanan.getInt("jumlahHari");

                        id_pesanan.setText(String.valueOf(pesanan.getInt("id")));
                        tanggal_pesan.setText(pesanan.getString("tanggalPesan"));
                        tarif.setText(String.valueOf(pesanan.getJSONObject("room").getDouble("dailyTariff")));
                        jumlah_hari.setText(String.valueOf(pesanan.getInt("jumlahHari")));
                    } else {
                        Intent intBackMenu = new Intent(SelesaiPesananActivity.this, MenuActivity.class);
                        SelesaiPesananActivity.this.startActivity(intBackMenu);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        PesananFetchRequest pesananFetchRequest = new PesananFetchRequest(String.valueOf(idCustomer), responseListener);
        RequestQueue queue = newRequestQueue(SelesaiPesananActivity.this);
        queue.add(pesananFetchRequest);
    }
}
