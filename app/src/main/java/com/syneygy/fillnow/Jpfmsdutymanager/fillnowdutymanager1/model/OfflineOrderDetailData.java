package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfflineOrderDetailData {
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
    @SerializedName("offline_data")
    @Expose
    private List<DeliveryInProgress> offlineData = null;

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

    public List<DeliveryInProgress> getOfflineData() {
        return offlineData;
    }

    public void setOfflineData(List<DeliveryInProgress> offlineData) {
        this.offlineData = offlineData;
    }
    public class OfflineDatum {

        @SerializedName("Progress")
        @Expose
        private Progress progress;
        @SerializedName("assets")
        @Expose
        private List<Asset> assets = null;
        @SerializedName("order")
        @Expose
        private List<Order> order = null;

        public Progress getProgress() {
            return progress;
        }

        public void setProgress(Progress progress) {
            this.progress = progress;
        }

        public List<Asset> getAssets() {
            return assets;
        }

        public void setAssets(List<Asset> assets) {
            this.assets = assets;
        }

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

    }

}
