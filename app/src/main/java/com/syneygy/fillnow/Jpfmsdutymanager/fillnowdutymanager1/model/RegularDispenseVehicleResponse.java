package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegularDispenseVehicleResponse implements Serializable {

    @SerializedName("succ")
    @Expose
    private Boolean succ;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("public_msg")
    @Expose
    private String publicMsg;

//    @SerializedName("_err_codes")
//    @Expose
//    private ErrCodes errCodes;

    @SerializedName("data")
    @Expose
    private Datum datum;

    private final static long serialVersionUID = -3536753339521107387L;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublicMsg() {
        return publicMsg;
    }

    public void setPublicMsg(String publicMsg) {
        this.publicMsg = publicMsg;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }
}