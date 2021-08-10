package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestContacto;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.response.ResponseFoto;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthABIService {

    @GET("api/premium/foto")
    Call<ResponseFoto> getFotoDia();

    @PUT("api/premium/mensaje")
    Call<ResponseMensaje> updateMensajeAyuda(@Body RequestMensaje requestMensaje);

    @POST("api/auth/verificacion")
    Call<ResponseLogIn> verificaJWT();

    @PUT("api/premium/contactoNuevo")
    Call<ResponseLogIn> registrarContacto(@Body RequestContacto requestContacto);
}
