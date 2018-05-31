package com.android.jhotel_android_AnggoroGagahN;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    private static int currentUserId;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dashboard_layout);
        Button btn_pesan = findViewById(R.id.btn_pesan);
        Button btn_lihatPesanan = findViewById(R.id.btn_lihatPesanan);
        Button btn_infoHotel = findViewById(R.id.btn_infoHotel);
        Button btn_gallery = findViewById(R.id.btn_gallery);

        Intent idCustIntent = getIntent();
        Bundle b = idCustIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
        }

        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menampilkan screen pesan
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("id_customer",currentUserId);
                startActivity(i);
            }
        });

        btn_lihatPesanan.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelesaiPesananActivity.class);
                i.putExtra("id_customer",currentUserId);
                startActivity(i);
            }
        });

        btn_infoHotel.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                // Menampilkan screen pesan
                Intent i = new Intent(getApplicationContext(), InfoHotelActivity.class);
                startActivity(i);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View view) {
                // Menampilkan screen tempat
                Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle()+" berhasil", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.logoutmenu:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        //finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
