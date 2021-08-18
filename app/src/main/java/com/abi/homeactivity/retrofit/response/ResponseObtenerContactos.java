
package com.abi.homeactivity.retrofit.response;


import com.abi.homeactivity.modelos.ContactoEmergencia1;
import com.abi.homeactivity.modelos.ContactoEmergencia2;
import com.abi.homeactivity.modelos.ContactoEmergencia3;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseObtenerContactos {

    @SerializedName("contactoEmergencia1")
    @Expose
    private ContactoEmergencia1 contactoEmergencia1;
    @SerializedName("contactoEmergencia2")
    @Expose
    private ContactoEmergencia2 contactoEmergencia2;
    @SerializedName("contactoEmergencia3")
    @Expose
    private ContactoEmergencia3 contactoEmergencia3;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseObtenerContactos() {
    }

    /**
     * 
     * @param contactoEmergencia3
     * @param contactoEmergencia2
     * @param contactoEmergencia1
     */
    public ResponseObtenerContactos(ContactoEmergencia1 contactoEmergencia1, ContactoEmergencia2 contactoEmergencia2, ContactoEmergencia3 contactoEmergencia3) {
        super();
        this.contactoEmergencia1 = contactoEmergencia1;
        this.contactoEmergencia2 = contactoEmergencia2;
        this.contactoEmergencia3 = contactoEmergencia3;
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

}
