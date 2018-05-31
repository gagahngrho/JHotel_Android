package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();
    private int currentUserId;
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final Button pesanan = findViewById(R.id.pesanan);

        Intent idCustIntent = getIntent();
        Bundle b = idCustIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
        }

        expListView = findViewById(R.id.lvExp);

        refreshList();
    }

    public void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    JSONObject e = jsonResponse.getJSONObject(0).getJSONObject("hotel");
                    JSONObject lokasi = e.getJSONObject("lokasi");
                    Hotel h = new Hotel(e.getInt("id"),e.getString("nama"),
                            new Lokasi(lokasi.getDouble("x"), lokasi.getDouble("y"), lokasi.getString("deskripsi")),
                            e.getInt("bintang"));

                    listHotel.add(h);

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject room = jsonResponse.getJSONObject(i);
                        Room room1 = new Room(room.getString("nomorKamar"), room.getString("statusKamar"),
                                room.getDouble("dailyTariff"), room.getString("tipeKamar"));
                        listRoom.add(room1);
                    }

                    childMapping.put(listHotel.get(0), listRoom);
                    listAdapter = new MenuListAdapter(MainActivity.this, listHotel, childMapping);
                    expListView.setAdapter(listAdapter);
                    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            Room selected = childMapping.get(listHotel.get(groupPosition)).get(childPosition);
                            Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);
                            intent.putExtra("id_customer", currentUserId);
                            intent.putExtra("nomor_kamar", selected.getRoomNumber());
                            intent.putExtra("dailyTariff", selected.getDailyTariff());
                            intent.putExtra("id_hotel", listHotel.get(groupPosition).getId());
                            startActivity(intent);
                            return false;
                        }}
                    );
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}