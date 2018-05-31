package com.gagahblwgmail.jhotel_android_anggorogagahn;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
        final EditText inputName = findViewById(R.id.inputName);
        final EditText emailInputReg = findViewById(R.id.inputEmailReg);
        final EditText passInputReg = findViewById(R.id.inputPassReg);
        final Button buttonRegis = findViewById(R.id.buttonRegister);

        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                final String name = inputName.getText().toString();
                final String email = emailInputReg.getText().toString();
                final String password = passInputReg.getText().toString();

                if(TextUtils.isEmpty(name)){
                    inputName.setError( "Name is required!" );
                } else if(TextUtils.isEmpty(email)){
                    emailInputReg.setError("Email is required!");
                }else if(TextUtils.isEmpty(password)){
                    passInputReg.setError("Password is required!");
                }else{
                    Response.Listener<String> responseListener = new Response.Listener<String> () {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                if(jsonResponse!=null) {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Registration Success").create().show();
                                }
                            } catch (JSONException e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Failed.").create().show();
                            }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(name,email,password,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
}