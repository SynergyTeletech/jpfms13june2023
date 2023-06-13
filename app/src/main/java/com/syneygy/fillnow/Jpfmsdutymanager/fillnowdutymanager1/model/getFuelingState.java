package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getFuelingState {
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
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


}