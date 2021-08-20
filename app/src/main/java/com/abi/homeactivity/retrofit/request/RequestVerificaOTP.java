
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestVerificaOTP {

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("otp")
    @Expose
    private String otp;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestVerificaOTP() {
    }

    /**
     * 
     * @param correo
     * @param otp
     */
    public RequestVerificaOTP(String correo, String otp) {
        super();
        this.correo = correo;
        this.otp = otp;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
