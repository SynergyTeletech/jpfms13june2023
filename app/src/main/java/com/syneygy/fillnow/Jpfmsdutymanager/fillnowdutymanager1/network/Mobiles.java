package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mobiles {
    @SerializedName("succ")
    private Boolean succ;
    @SerializedName("type")
    private String type;
    @SerializedName("public_msg")
    private String public_msg;
    @SerializedName("_err_codes")
    private List<Object> _err_codes;
    @SerializedName("data")
    private Otp otp;

    public Boolean getSucc() {
        return succ;
    }

    public Otp getOtp() {
        return otp;
    }

    public void setOtp(Otp otp) {
        this.otp = otp;
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

    public String getPublic_msg() {
        return public_msg;
    }

    public void setPublic_msg(String public_msg) {
        this.public_msg = public_msg;
    }

    public List<Object> get_err_codes() {
        return _err_codes;
    }

    public void set_err_codes(List<Object> _err_codes) {
        this._err_codes = _err_codes;
    }
}
