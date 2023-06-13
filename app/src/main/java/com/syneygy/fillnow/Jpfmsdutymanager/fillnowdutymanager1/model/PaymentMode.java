package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentMode {


    public class Datum {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public String toString() {
            return "Datum{" +
                    "categoryName='" + categoryName + '\'' +
                    '}';
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



