package com.gagahblwgmail.jhotel_android_anggorogagahn;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest
{
    private static final String PesananFetch_URL = "http://10.0.2.2:8080/pesanancustomer/";
    private Map<String, String> params;

    public PesananFetchRequest(String idCustomer, Response.Listener<String> listener) {
        super(Method.GET, PesananFetch_URL + idCustomer, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}


