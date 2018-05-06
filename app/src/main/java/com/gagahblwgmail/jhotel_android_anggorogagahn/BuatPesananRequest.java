package com.gagahblwgmail.jhotel_android_anggorogagahn;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest
{
    private static final String BuatPesanan_URL = "http://10.0.2.2:8080/bookpesanan";
    private Map<String, String> params;

    public BuatPesananRequest(int jumlah_hari, int id_customer, int id_hotel, String nomor_kamar,
                              Response.Listener<String> listener) {
        super(Method.POST, BuatPesanan_URL, listener, null);
        params = new HashMap<>();
        params.put("jumlahHari", String.valueOf(jumlah_hari));
        params.put("idCustomer", String.valueOf(id_customer));
        params.put("idHotel", String.valueOf(id_hotel));
        params.put("nomorKamar", nomor_kamar);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}