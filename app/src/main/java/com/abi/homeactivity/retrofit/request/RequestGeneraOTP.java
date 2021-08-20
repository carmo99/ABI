
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGeneraOTP {

    @SerializedName("correo")
    @Expose
    private String correo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestGeneraOTP() {
    }

    /**
     * 
     * @param correo
     */
    public RequestGeneraOTP(String correo) {
        super();
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
