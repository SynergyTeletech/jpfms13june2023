package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AcceptedOrdersResponse implements Serializable {

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
    private List<String> errCodes = null;
    @SerializedName("order")
    @Expose
    private List<Order> order = null;
    @SerializedName("delivery_inprogress")
    @Expose
    private List<Order> deliveryInprogress = null;

    private final static long serialVersionUID = -922010214492546430L;

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

    public List<String> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(List<String> errCodes) {
        this.errCodes = errCodes;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Order> getDeliveryInprogress() {
        return deliveryInprogress;
    }

    public void setDeliveryInprogress(List<Order> deliveryInprogress) {
        this.deliveryInprogress = deliveryInprogress;
    }
}