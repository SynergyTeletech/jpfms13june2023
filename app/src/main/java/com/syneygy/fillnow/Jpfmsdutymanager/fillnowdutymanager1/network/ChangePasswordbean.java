package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ChangePasswordbean implements Serializable
{
    @SerializedName("succ")
    private Boolean succ;
    @SerializedName("type")
    private String type;
    @SerializedName("public_msg")
    private String publicMsg;
    @SerializedName("_err_codes")
    private ArrayList<String> errCodes = null;

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

    public ArrayList<String> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(ArrayList<String> errCodes) {
        this.errCodes = errCodes;
    }
}
