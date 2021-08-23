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
import com.abi.homeactivity.retrofit.response.ResponseUnaNoticia;
import com.abi.homeactivity.retrofit.response.ResponsetPerfil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @Multipart
    @POST("api/usuarios/foto")
    Call<ResponseLogIn> cambiarFotoPerfil(@Part MultipartBody.Part image, @Part("archivo") RequestBody description);

    @Multipart
    @PUT("api/premium/foto")
    Call<ResponseLogIn> cambiarFotoDia(@Part MultipartBody.Part image, @Part("archivo") RequestBody description);

    @POST("api/premium/borrar/contactos/{contacto}")
    Call<ResponseLogIn> borrarContacto(@Path("contacto") String NoContacto);
}
