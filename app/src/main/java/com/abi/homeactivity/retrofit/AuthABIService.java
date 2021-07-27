package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.response.ResponseFoto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthABIService {

    @GET("api/premium/foto")
    Call<ResponseFoto> getFotoDia();
}
