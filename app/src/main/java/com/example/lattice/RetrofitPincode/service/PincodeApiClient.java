package com.example.lattice.RetrofitPincode.service;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PincodeApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ChuckerInterceptor(context))
                .build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.postalpincode.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}
