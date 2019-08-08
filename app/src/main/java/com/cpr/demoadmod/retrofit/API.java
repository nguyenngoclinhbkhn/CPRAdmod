package com.cpr.demoadmod.retrofit;

import com.cpr.demoadmod.model.Application;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("list_apps.php")
    Call<JsonElement> listApp();
}
