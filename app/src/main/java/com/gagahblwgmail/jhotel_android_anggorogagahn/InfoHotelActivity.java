package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class InfoHotelActivity extends Activity{
    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_hotel_layout);

        fetchHotel();
    }

    public void fetchHotel(){
        final TextView namaHotel = findViewById(R.id.nama_hotel);
        final TextView lokasiHotel = findViewById(R.id.lokasi);
        final TextView bintangHotel = findViewById(R.id.bintang);
        final TextView nomorKamar1 = findViewById(R.id.nomor_kamar1);
        final TextView statusKamar1 = findViewById(R.id.status_kamar1);
        final TextView dailyTariff1 = findViewById(R.id.daily_tariff1);
        final TextView tipeKamar1 = findViewById(R.id.tipe_kamar1);
        final TextView nomorKamar2 = findViewById(R.id.nomor_kamar2);
        final TextView statusKamar2 = findViewById(R.id.status_kamar2);
        final TextView dailyTariff2 = findViewById(R.id.daily_tariff2);
        final TextView tipeKamar2 = findViewById(R.id.tipe_kamar2);
        final TextView nomorKamar3 = findViewById(R.id.nomor_kamar3);
        final TextView statusKamar3 = findViewById(R.id.status_kamar3);
        final TextView dailyTariff3 = findViewById(R.id.daily_tariff3);
        final TextView tipeKamar3 = findViewById(R.id.tipe_kamar3);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    JSONObject hotel = jsonResponse.getJSONObject(0).getJSONObject("hotel");
                    JSONObject lokasi = hotel.getJSONObject("lokasi");
                    namaHotel.setText(hotel.getString("nama"));
                    lokasiHotel.setText(lokasi.getString("deskripsi"));
                    bintangHotel.setText(String.valueOf(hotel.getInt("bintang")));

                    JSONObject room1 = jsonResponse.getJSONObject(0);
                    nomorKamar1.setText(room1.getString("nomorKamar"));
                    statusKamar1.setText(room1.getString("statusKamar"));
                    dailyTariff1.setText(String.valueOf(room1.getDouble("dailyTariff")));
                    tipeKamar1.setText(room1.getString("tipeKamar"));

                    JSONObject room2 = jsonResponse.getJSONObject(1);
                    nomorKamar2.setText(room2.getString("nomorKamar"));
                    statusKamar2.setText(room2.getString("statusKamar"));
                    dailyTariff2.setText(String.valueOf(room2.getDouble("dailyTariff")));
                    tipeKamar2.setText(room2.getString("tipeKamar"));

                    JSONObject room3 = jsonResponse.getJSONObject(2);
                    nomorKamar3.setText(room3.getString("nomorKamar"));
                    statusKamar3.setText(room3.getString("statusKamar"));
                    dailyTariff3.setText(String.valueOf(room3.getDouble("dailyTariff")));
                    tipeKamar3.setText(room3.getString("tipeKamar"));
                }catch (JSONException e1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoHotelActivity.this);
                    builder.setMessage("Data gagal dimuat")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(InfoHotelActivity.this, DashboardActivity.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = newRequestQueue(this);
        queue.add(menuRequest);
    }
}
