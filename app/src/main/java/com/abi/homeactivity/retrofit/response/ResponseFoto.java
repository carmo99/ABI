
package com.abi.homeactivity.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFoto {

    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseFoto() {
    }

    /**
     * 
     * @param url
     */
    public ResponseFoto(String url) {
        super();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
