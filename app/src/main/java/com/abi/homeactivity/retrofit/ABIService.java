package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestCrearUsuario;
import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.response.ResponseLogin;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ABIService
{
    @POST("api/auth/login")
    Call<ResponseLogin> responselogin (@Body RequestLogin requestLogin);

    @POST("api/usuarios")
    Call<ResponseLogin> responseSingUp (@Body RequestCrearUsuario requestCrearUsuario);



}
