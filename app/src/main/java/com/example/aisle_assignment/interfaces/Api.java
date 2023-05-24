package com.example.aisle_assignment.interfaces;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @POST("users/phone_number_login")
    Call<ResponseBody> loginApi(@Body HashMap<String, String> params);

    @POST("users/verify_otp")
    Call<ResponseBody> optApi(@Body HashMap<String, String> params);


    @GET("users/test_profile_list")
    Call<ResponseBody> getNotes(@Header("Authorization") String authToken);

}
