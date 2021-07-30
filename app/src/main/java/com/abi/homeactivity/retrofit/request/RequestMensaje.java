
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestMensaje {

    @SerializedName("mensajeAyuda")
    @Expose
    private String mensajeAyuda;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestMensaje() {
    }

    /**
     * 
     * @param mensajeAyuda
     */
    public RequestMensaje(String mensajeAyuda) {
        super();
        this.mensajeAyuda = mensajeAyuda;
    }

    public String getMensajeAyuda() {
        return mensajeAyuda;
    }

    public void setMensajeAyuda(String mensajeAyuda) {
        this.mensajeAyuda = mensajeAyuda;
    }

}
