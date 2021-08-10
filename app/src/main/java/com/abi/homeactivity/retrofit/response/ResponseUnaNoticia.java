
package com.abi.homeactivity.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUnaNoticia {

    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("contenido")
    @Expose
    private String contenido;
    @SerializedName("foto")
    @Expose
    private String foto;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUnaNoticia() {
    }

    /**
     * 
     * @param contenido
     * @param foto
     * @param titulo
     */
    public ResponseUnaNoticia(String titulo, String contenido, String foto) {
        super();
        this.titulo = titulo;
        this.contenido = contenido;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
