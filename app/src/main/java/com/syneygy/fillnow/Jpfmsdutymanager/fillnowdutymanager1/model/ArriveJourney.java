package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.SerializedName;

public class ArriveJourney {
    @SerializedName("transaction_id")
    String transactionId;
    @SerializedName("order_id")
    String orderId;
    @SerializedName("staus")
    String status;
    @SerializedName("login_id")
    String loginId;

    public ArriveJourney() {
    }

    public ArriveJourney(String transactionId, String orderId, String status, String loginId) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.status = status;
        this.loginId = loginId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
