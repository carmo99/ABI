
package com.abi.homeactivity.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactoEmergencia3 {

    @SerializedName("fotoPerfil")
    @Expose
    private String fotoPerfil;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("telefono")
    @Expose
    private String telefono;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ContactoEmergencia3() {
    }

    /**
     * 
     * @param fotoPerfil
     * @param correo
     * @param id
     * @param telefono
     * @param nombre
     */
    public ContactoEmergencia3(String fotoPerfil, String id, String nombre, String correo, String telefono) {
        super();
        this.fotoPerfil = fotoPerfil;
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
