
package com.abi.homeactivity.retrofit.response;

import com.abi.homeactivity.modelos.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogIn {

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseLogIn() {
    }

    /**
     * 
     * @param msg
     * @param usuario
     * @param token
     */
    public ResponseLogIn(Usuario usuario, String token, String msg) {
        super();
        this.usuario = usuario;
        this.token = token;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
