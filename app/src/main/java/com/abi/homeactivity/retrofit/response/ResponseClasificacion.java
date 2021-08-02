
package com.abi.homeactivity.retrofit.response;

import java.util.List;

import com.abi.homeactivity.Noticia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseClasificacion {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("noticias")
    @Expose
    private List<Noticia> noticias = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseClasificacion() {
    }

    /**
     * 
     * @param total
     * @param noticias
     */
    public ResponseClasificacion(Integer total, List<Noticia> noticias) {
        super();
        this.total = total;
        this.noticias = noticias;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

}
