package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class MenuActivity extends AppCompatActivity {

    MenuListAdapter listAdapter;
    ExpandableListView expListView;

    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<>();
    private int currentUserId;

    HashMap<String, Hotel> hotelHashMap = new HashMap<>();
    HashMap<String, ArrayList<Room>> roomsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button pesananButton = (Button) findViewById(R.id.selesai);

        Intent i = getIntent();
        currentUserId = i.getIntExtra("idCustomer", 0);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expanded_menu);

        // preparing list data
        refreshList();

        //listAdapter = new MenuListAdapter(this, listHotel, childMapping);

        // setting list adapter
        //expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Room selectedRoom = childMapping.get(listHotel.get(groupPosition)).get(childPosition);
                Hotel selectedHotel = listHotel.get(groupPosition);

                Intent intBuatPesanan = new Intent(MenuActivity.this, BuatPesananActivity.class);
                intBuatPesanan.putExtra("idCustomer", currentUserId);
                intBuatPesanan.putExtra("roomNumber", selectedRoom.getRoomNumber());
                intBuatPesanan.putExtra("idHotel", selectedHotel.getId());
                intBuatPesanan.putExtra("tariff", selectedRoom.getDailyTariff());
                MenuActivity.this.startActivity(intBuatPesanan);
                return false;
            }
        });

        pesananButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSelesaiPesanan = new Intent(MenuActivity.this, SelesaiPesananActivity.class);
                intSelesaiPesanan.putExtra("idCustomer", currentUserId);
                MenuActivity.this.startActivity(intSelesaiPesanan);
            }
        });
    }

    protected void refreshList()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int i=0 ; i<jsonResponse.length() ; i++) {
                        JSONObject temp = jsonResponse.getJSONObject(i).getJSONObject("hotel");
                        JSONObject lokasi = temp.getJSONObject("lokasi");
                        JSONObject room_temp = jsonResponse.getJSONObject(i);

                        Hotel h = new Hotel(temp.getInt("id"), temp.getString("nama"),
                                new Lokasi(lokasi.getDouble("x"), lokasi.getDouble("y"), lokasi.getString("deskripsi")),
                                temp.getInt("bintang"));
                        hotelHashMap.put(h.getNama(), h);
                        Room room = new Room(room_temp.getString("nomorKamar"), room_temp.getString("statusKamar"),
                                room_temp.getDouble("dailyTariff"), room_temp.getString("tipeKamar"));

                        if(!roomsMap.containsKey(h.getNama())) {
                            ArrayList<Room> listRoom = new ArrayList<>();
                            listRoom.add(room);
                            roomsMap.put(h.getNama(), listRoom);
                        } else {
                            ArrayList<Room> listRoom = roomsMap.get(h.getNama());
                            listRoom.add(room);
                            roomsMap.put(h.getNama(), listRoom);
                        }
                    }

                    for(String key : hotelHashMap.keySet()) {
                        listHotel.add(hotelHashMap.get(key));
                        childMapping.put(hotelHashMap.get(key), roomsMap.get(key));
                    }

                    listAdapter = new MenuListAdapter(MenuActivity.this, listHotel, childMapping);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = newRequestQueue(MenuActivity.this);
        queue.add(menuRequest);
    }


}
