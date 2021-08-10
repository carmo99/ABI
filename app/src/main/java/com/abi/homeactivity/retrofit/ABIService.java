package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestCrearUsuario;
import com.abi.homeactivity.retrofit.request.RequestInformacion;
import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.response.ResponseClasificacion;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ABIService
{
    @POST("api/auth/login/")
    Call<ResponseLogIn> responselogin (@Body RequestLogin requestLogin);

    @POST("api/usuarios")
    Call<ResponseLogIn> responseSingUp (@Body RequestCrearUsuario requestCrearUsuario);

    @GET("api/informacion/todas/I_LEGAL")
    Call<ResponseClasificacion> responseClasificacionLegal ();

    @GET("api/informacion/todas/I_DIA")
    Call<ResponseClasificacion> responseClasificacionDia ();

}
