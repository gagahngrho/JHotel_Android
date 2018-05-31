package com.android.jhotel_android_AnggoroGagahN;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MenuRequest extends StringRequest {
    private static final String Menu_URL = "http://192.168.43.47:8080/vacantrooms";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, Menu_URL, listener, null);
        params = new HashMap<>();
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
