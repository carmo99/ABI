
package com.abi.homeactivity.retrofit.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsetPerfil {

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
    public ResponsetPerfil() {
    }

    /**
     * 
     * @param correo
     * @param telefono
     * @param nombre
     */
    public ResponsetPerfil(String nombre, String correo, String telefono) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
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
