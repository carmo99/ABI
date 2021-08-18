package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.retrofit.request.RequestContacto;
import com.abi.homeactivity.retrofit.request.RequestContrasenia;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.request.RequestPerfil;
import com.abi.homeactivity.retrofit.request.RequestRegistraGadget;
import com.abi.homeactivity.retrofit.response.ResponseFoto;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;
import com.abi.homeactivity.retrofit.response.ResponseObtenerContactos;
import com.abi.homeactivity.retrofit.response.ResponseRegistraGadget;
import com.abi.homeactivity.retrofit.response.ResponsetPerfil;

import retrofit2.Call;
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

    @PUT("api/usuarios/gadget")
    Call<ResponseRegistraGadget> registrarGadget(@Body RequestRegistraGadget requestRegistraGadget);;

    @GET("api/usuarios")
    Call<ResponsetPerfil> getPerfil();

    @PUT("api/usuarios")
    Call<ResponseLogIn> actualizarPerfil(@Body RequestPerfil requestPerfil);

    @PUT("api/usuarios/contrasenia")
    Call<ResponseLogIn> actualizarContrasenia(@Body RequestContrasenia requestContrasenia);

    @GET("api/premium/contactos")
    Call <ResponseObtenerContactos> obtenercontactos();

}
