package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseeList {


    public class Datum {

        @SerializedName("data_auto_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("product_id")
        @Expose
        private String product_id;

        @SerializedName("product_name")
        @Expose
        private String product_name;

        public String getId() {
            return id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


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
    private List<Object> errCodes = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Object> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(List<Object> errCodes) {
        this.errCodes = errCodes;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
