
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLogin {

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("contrasenia")
    @Expose
    private String contrasenia;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestLogin() {
    }

    /**
     * 
     * @param correo
     * @param contrasenia
     */
    public RequestLogin(String correo, String contrasenia) {
        super();
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
