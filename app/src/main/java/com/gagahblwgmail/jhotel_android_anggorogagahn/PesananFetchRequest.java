package com.gagahblwgmail.jhotel_android_anggorogagahn;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anggoro Gagah on 5/6/2018.
 */

public class PesananFetchRequest extends StringRequest {
    private static final String PesananFetch_URL = "http://192.168.1.101:8080/pesanancustomer/";
    private Map<String, String> params;

    public PesananFetchRequest(String id_customer, Response.Listener<String> listener) {
        super(Method.GET, PesananFetch_URL +id_customer, listener, null);
        params = new HashMap<>();
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
