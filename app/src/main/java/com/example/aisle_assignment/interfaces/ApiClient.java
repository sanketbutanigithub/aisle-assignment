package com.example.aisle_assignment.interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://app.aisle.co/V1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Api api = retrofit.create(Api.class);
}
