
package com.abi.homeactivity.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOTP {

    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseOTP() {
    }

    /**
     * 
     * @param msg
     */
    public ResponseOTP(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
