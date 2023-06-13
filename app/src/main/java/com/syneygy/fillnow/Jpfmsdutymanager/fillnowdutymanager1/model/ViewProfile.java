package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewProfile implements Serializable {

    @SerializedName("succ")
    @Expose
    private Boolean succ;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("public_msg")
    @Expose
    private String publicMsg;

    @SerializedName("_err_codes")
    @Expose
    public ArrayList<String> errCodes = null;

    @SerializedName("data")
    @Expose
    public List<ViewProfileData> data;


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

    public List<ViewProfileData> getData() {
        return data;
    }

    public void setData(List<ViewProfileData> data) {
        this.data = data;
    }
}
