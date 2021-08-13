
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestContrasenia {

    @SerializedName("contrasenia")
    @Expose
    private String contrasenia;
    @SerializedName("contraseniaNueva")
    @Expose
    private String contraseniaNueva;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestContrasenia() {
    }

    /**
     * 
     * @param contraseniaNueva
     * @param contrasenia
     */
    public RequestContrasenia(String contrasenia, String contraseniaNueva) {
        super();
        this.contrasenia = contrasenia;
        this.contraseniaNueva = contraseniaNueva;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

}
