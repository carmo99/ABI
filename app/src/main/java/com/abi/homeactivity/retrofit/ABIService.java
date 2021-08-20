package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestCrearUsuario;
import com.abi.homeactivity.retrofit.request.RequestGeneraOTP;
import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.request.RequestOlvideContrasenia;
import com.abi.homeactivity.retrofit.request.RequestVerificaOTP;
import com.abi.homeactivity.retrofit.response.ResponseClasificacion;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponseOTP;
import com.abi.homeactivity.retrofit.response.ResponseUnaNoticia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("api/informacion/{idNoticia}")
    Call<ResponseUnaNoticia> responseNoticia(@Path("idNoticia") String idNoticia);

    @POST("api/auth/generarOTP")
    Call<ResponseOTP> responseGeneraOTP (@Body RequestGeneraOTP requestGeneraOTP);

    @POST("api/auth/verificar/OTP")
    Call<ResponseOTP> responseVerificaOTP (@Body RequestVerificaOTP requestVerificaOTP);

    @POST("api/auth/cambiar/contrasenia")
    Call<ResponseOTP> responseCambiarContrOlvidada (@Body RequestOlvideContrasenia requestOlvideContrasenia);



}
