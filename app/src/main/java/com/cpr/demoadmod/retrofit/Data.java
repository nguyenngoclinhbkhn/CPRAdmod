package com.cpr.demoadmod.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Data {
    public static Retrofit retrofit;

    public static Retrofit createRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://cprcorp.com/cprsoftware/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static API getListApp(){
        return createRetrofit().create(API.class);
    }
}
