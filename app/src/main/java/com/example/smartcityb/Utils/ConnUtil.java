package com.example.smartcityb.Utils;

import com.example.smartcityb.Public.ApiServe;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnUtil {

    public static String getPath() {
        return "http://124.93.196.45:10001/";
    }

    public static ApiServe creServe() {
        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("http://124.93.196.45:10001/").
                build();
        ApiServe apiServe = retrofit.create(ApiServe.class);
        return apiServe;
    }

    public static ApiServe creServe2() {
        Retrofit retrofit = new Retrofit.
                Builder().
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl("http://124.93.196.45:10001/").
                build();
        ApiServe apiServe = retrofit.create(ApiServe.class);
        return apiServe;
    }

}
