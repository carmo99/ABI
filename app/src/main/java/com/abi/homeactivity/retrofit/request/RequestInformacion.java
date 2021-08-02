package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestInformacion {

    @SerializedName("clasificacion")
    @Expose
    private String clasificacion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestInformacion() {
    }

    /**
     * 
     * @param clasificacion
     */
    public RequestInformacion(String clasificacion) {
        super();
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

}
