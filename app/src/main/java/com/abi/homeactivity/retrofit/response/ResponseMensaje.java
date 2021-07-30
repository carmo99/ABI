
package com.abi.homeactivity.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMensaje {

    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("estado")
    @Expose
    private Boolean estado;
    @SerializedName("mensajeAyuda")
    @Expose
    private String mensajeAyuda;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("gadget")
    @Expose
    private String gadget;
    @SerializedName("fotoDia")
    @Expose
    private String fotoDia;
    @SerializedName("uid")
    @Expose
    private String uid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseMensaje() {
    }

    /**
     * 
     * @param uid
     * @param estado
     * @param fotoDia
     * @param gadget
     * @param correo
     * @param mensajeAyuda
     * @param telefono
     * @param nombre
     * @param rol
     */
    public ResponseMensaje(String rol, Boolean estado, String mensajeAyuda, String nombre, String correo, String telefono, String gadget, String fotoDia, String uid) {
        super();
        this.rol = rol;
        this.estado = estado;
        this.mensajeAyuda = mensajeAyuda;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.gadget = gadget;
        this.fotoDia = fotoDia;
        this.uid = uid;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getMensajeAyuda() {
        return mensajeAyuda;
    }

    public void setMensajeAyuda(String mensajeAyuda) {
        this.mensajeAyuda = mensajeAyuda;
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

    public String getGadget() {
        return gadget;
    }

    public void setGadget(String gadget) {
        this.gadget = gadget;
    }

    public String getFotoDia() {
        return fotoDia;
    }

    public void setFotoDia(String fotoDia) {
        this.fotoDia = fotoDia;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
