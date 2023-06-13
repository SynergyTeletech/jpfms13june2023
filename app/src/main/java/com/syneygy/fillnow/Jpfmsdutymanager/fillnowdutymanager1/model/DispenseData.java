package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DispenseData implements Serializable {

    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;

    @SerializedName("balance_qty")
    @Expose
    private int balance_qty;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getBalance_qty() {
        return balance_qty;
    }

    public void setBalance_qty(int balance_qty) {
        this.balance_qty = balance_qty;
    }

}
