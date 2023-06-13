package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Root {
    private Boolean succ;
    private String type;
    private String publicMsg;
    private List<Object> errCodes = new ArrayList<Object>();
    private List<Order> order = new ArrayList<Order>();
    @SerializedName("delivery_inprogress")
    private List<DeliveryInprogres> deliveryInprogress = new ArrayList<DeliveryInprogres>();
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
    public List<Order> getOrder() {
        return order;
    }
    public void setOrder(List<Order> order) {
        this.order = order;
    }
    public List<DeliveryInprogres> getDeliveryInprogress() {
        return deliveryInprogress;
    }
    public void setDeliveryInprogress(List<DeliveryInprogres> deliveryInprogress) {
        this.deliveryInprogress = deliveryInprogress;
    }

}
