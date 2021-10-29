
package com.abi.homeactivity.modelos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("fotoPerfil")
    @Expose
    private String fotoPerfil;
    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("estado")
    @Expose
    private Boolean estado;
    @SerializedName("mensajeAyuda")
    @Expose
    private String mensajeAyuda;
    @SerializedName("ubicacionEmergencia")
    @Expose
    private List<String> ubicacionEmergencia = null;
    @SerializedName("nombreEmergencia")
    @Expose
    private List<String> nombreEmergencia = null;
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
    @SerializedName("contactoEmergencia1")
    @Expose
    private ContactoEmergencia1 contactoEmergencia1;
    @SerializedName("contactoEmergencia2")
    @Expose
    private ContactoEmergencia2 contactoEmergencia2;
    @SerializedName("contactoEmergencia3")
    @Expose
    private ContactoEmergencia3 contactoEmergencia3;
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
    public Usuario() {
    }

    /**
     * 
     * @param estado
     * @param fotoPerfil
     * @param fotoDia
     * @param gadget
     * @param nombreEmergencia
     * @param contactoEmergencia3
     * @param mensajeAyuda
     * @param contactoEmergencia2
     * @param nombre
     * @param contactoEmergencia1
     * @param rol
     * @param ubicacionEmergencia
     * @param uid
     * @param correo
     * @param telefono
     */
    public Usuario(String fotoPerfil, String rol, Boolean estado, String mensajeAyuda, List<String> ubicacionEmergencia, List<String> nombreEmergencia, String nombre, String correo, String telefono, String gadget, ContactoEmergencia1 contactoEmergencia1, ContactoEmergencia2 contactoEmergencia2, ContactoEmergencia3 contactoEmergencia3, String fotoDia, String uid) {
        super();
        this.fotoPerfil = fotoPerfil;
        this.rol = rol;
        this.estado = estado;
        this.mensajeAyuda = mensajeAyuda;
        this.ubicacionEmergencia = ubicacionEmergencia;
        this.nombreEmergencia = nombreEmergencia;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.gadget = gadget;
        this.contactoEmergencia1 = contactoEmergencia1;
        this.contactoEmergencia2 = contactoEmergencia2;
        this.contactoEmergencia3 = contactoEmergencia3;
        this.fotoDia = fotoDia;
        this.uid = uid;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
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

    public List<String> getUbicacionEmergencia() {
        return ubicacionEmergencia;
    }

    public void setUbicacionEmergencia(List<String> ubicacionEmergencia) {
        this.ubicacionEmergencia = ubicacionEmergencia;
    }

    public List<String> getNombreEmergencia() {
        return nombreEmergencia;
    }

    public void setNombreEmergencia(List<String> nombreEmergencia) {
        this.nombreEmergencia = nombreEmergencia;
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

    public ContactoEmergencia1 getContactoEmergencia1() {
        return contactoEmergencia1;
    }

    public void setContactoEmergencia1(ContactoEmergencia1 contactoEmergencia1) {
        this.contactoEmergencia1 = contactoEmergencia1;
    }

    public ContactoEmergencia2 getContactoEmergencia2() {
        return contactoEmergencia2;
    }

    public void setContactoEmergencia2(ContactoEmergencia2 contactoEmergencia2) {
        this.contactoEmergencia2 = contactoEmergencia2;
    }

    public ContactoEmergencia3 getContactoEmergencia3() {
        return contactoEmergencia3;
    }

    public void setContactoEmergencia3(ContactoEmergencia3 contactoEmergencia3) {
        this.contactoEmergencia3 = contactoEmergencia3;
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
