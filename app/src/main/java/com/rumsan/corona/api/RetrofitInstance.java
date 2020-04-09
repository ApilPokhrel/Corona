package com.rumsan.corona.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rumsan.corona.config.Variables.PROD_URL;

public class RetrofitInstance {

    private static Retrofit instance;

    public static Retrofit getInstance(){
        String URL = PROD_URL;

        if(instance == null){
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            instance =  new Retrofit.Builder().baseUrl(URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return instance;
    }
}
