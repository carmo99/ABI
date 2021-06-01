package com.abi.homeactivity;

public class Informacion
{
    private String tituloInformacion;
    private String urlFoto;

    public Informacion(String tituloInformacion, String urlFoto)
    {
        this.tituloInformacion = tituloInformacion;
        this.urlFoto = urlFoto;
    }

    public String getTituloInformacion() {
        return tituloInformacion;
    }

    public void setTituloInformacion(String tituloInformacion) {
        this.tituloInformacion = tituloInformacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
