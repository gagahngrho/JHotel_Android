package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nameInput = (EditText) findViewById(R.id.inputRegFullName);
        final EditText emailInput = (EditText) findViewById(R.id.inputEmail);
        final EditText passInput = (EditText) findViewById(R.id.inputPass);
        final Button registButton = (Button) findViewById(R.id.buttonRegister);

        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String password = passInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration success.");
                                builder.create();
                                builder.show();

                                Intent registIntLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(registIntLogin);
                            }
                        } catch(JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registration failed.");
                            builder.create();
                            builder.show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });




    }
}
