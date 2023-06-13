package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostOrder {

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;

    @SerializedName("balance_qty")
    @Expose
    private float balance_qty;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public float getBalance_qty() {
        return balance_qty;
    }

    public void setBalance_qty(float balance_qty) {
        this.balance_qty = balance_qty;
    }
}
