package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePassword {
    @SerializedName("succ")
    @Expose
    private Boolean succ;
    @SerializedName("public_msg")
    @Expose
    private String publicMsg;
    @SerializedName("_err_codes")
    @Expose
    private List<Object> errCodes = null;
    @SerializedName("data")
    @Expose
    private List<Object> data = null;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getPublicMsg() {
        return publicMsg;
    }

    public void setPublicMsg(String publicMsg) {
        this.publicMsg = publicMsg;
    }

    public List<Object> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(List<Object> errCodes) {
        this.errCodes = errCodes;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}