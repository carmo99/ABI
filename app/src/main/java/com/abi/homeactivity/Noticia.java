
package com.abi.homeactivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noticia {

    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("titulo")
    @Expose
    private String titulo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Noticia() {
    }

    /**
     * 
     * @param foto
     * @param titulo
     * @param id
     */
    public Noticia(String foto, String id, String titulo) {
        //super();
        this.foto = foto;
        this.id = id;
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
