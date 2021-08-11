
package com.abi.homeactivity.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRegistraGadget {

    @SerializedName("gadget")
    @Expose
    private String gadget;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestRegistraGadget() {
    }

    /**
     * 
     * @param gadget
     */
    public RequestRegistraGadget(String gadget) {
        super();
        this.gadget = gadget;
    }

    public String getGadget() {
        return gadget;
    }

    public void setGadget(String gadget) {
        this.gadget = gadget;
    }

}
