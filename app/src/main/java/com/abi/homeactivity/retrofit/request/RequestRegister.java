
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRegister {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("contrasenia")
    @Expose
    private String contrasenia;
    @SerializedName("telefono")
    @Expose
    private String telefono;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestRegister() {
    }

    /**
     * 
     * @param correo
     * @param contrasenia
     * @param telefono
     * @param nombre
     */
    public RequestRegister(String nombre, String correo, String contrasenia, String telefono) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
