package com.gagahblwgmail.jhotel_android_anggorogagahn;

import com.android.volley.toolbox.StringRequest;import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class PesananBatalRequest extends StringRequest
{
    private static final String PesananSelesai_URL = "http://10.0.2.2:8080/cancelpesanan";
    private Map<String, String> params;

    public PesananBatalRequest(int idPesanan, Response.Listener<String> listener) {
        super(Method.POST, PesananSelesai_URL, listener, null);
        params = new HashMap<>();
        params.put("idPesanan", String.valueOf(idPesanan));
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}

