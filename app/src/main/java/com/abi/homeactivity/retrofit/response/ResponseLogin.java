
package com.abi.homeactivity.retrofit.response;

import com.abi.homeactivity.retrofit.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseLogin() {
    }

    /**
     * 
     * @param usuario
     * @param token
     */
    public ResponseLogin(Usuario usuario, String token) {
        super();
        this.usuario = usuario;
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
