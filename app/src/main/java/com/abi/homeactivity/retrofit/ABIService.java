package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.response.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ABIService
{
    @POST("api/auth/login")
    Call<ResponseLogin> responselogin (@Body RequestLogin requestLogin);

}
