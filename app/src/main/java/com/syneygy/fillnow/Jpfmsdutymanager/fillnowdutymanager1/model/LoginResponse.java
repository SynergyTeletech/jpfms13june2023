package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginResponse implements Serializable {

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
    public ArrayList<String> errCodes = null;

    @SerializedName("data")
    @Expose
    public ArrayList<Datum> data;

    @SerializedName("order")
    @Expose
    public List<Order> order;

    @SerializedName("delivery_inprogress")
    @Expose
    public List<DeliveryInProgress> delivery_inProgress;

    @SerializedName("vehicle_data")
    @Expose
    public List<VehicleDatum> vehicle_data;

    @SerializedName("franchise_data")
    @Expose
    public List<FranchiseDatum > franchise_data;

    @SerializedName("Progress")
    @Expose
    private Progress progress;

    @SerializedName("dispensedata")
    @Expose
    private DispenseData dispenseData;



    public LoginResponse() { }

//    public LoginResponse(Boolean succ, String type, String publicMsg, ArrayList<String> errCodes, ArrayList<Datum> data) {
//        this.succ = succ;
//        this.type = type;
//        this.publicMsg = publicMsg;
//        this.errCodes = errCodes;
//        this.data = data;
//    }


    public LoginResponse(Boolean succ, String type, String publicMsg, ArrayList<String> errCodes, ArrayList<Datum> data, List<Order> order, List<DeliveryInProgress> delivery_inProgress, List<VehicleDatum> vehicle_data, Progress progress) {
        this.succ = succ;
        this.type = type;
        this.publicMsg = publicMsg;
        this.errCodes = errCodes;
        this.data = data;
        this.order = order;
        this.delivery_inProgress = delivery_inProgress;
        this.vehicle_data = vehicle_data;
        this.progress = progress;
    }

    public Boolean getSucc() { return succ; }

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

    public ArrayList<String> getErrCodes() { return errCodes; }

    public void setErrCodes(ArrayList<String> errCodes) {
        this.errCodes = errCodes;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<DeliveryInProgress> getDelivery_inProgress() {
        return delivery_inProgress;
    }

    public void setDelivery_inProgress(List<DeliveryInProgress> delivery_inProgress) { this.delivery_inProgress = delivery_inProgress; }

    public List<VehicleDatum> getVehicle_data() {
        return vehicle_data;
    }

    public void setVehicle_data(List<VehicleDatum> vehicle_data) { this.vehicle_data = vehicle_data; }

    public List<FranchiseDatum> getFranchise_data() {
        return franchise_data;
    }

    public void setFranchise_data(List<FranchiseDatum> franchise_data) { this.franchise_data = franchise_data; }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }


    public DispenseData getDispenseData() {
        return dispenseData;
    }

    public void setDispenseData(DispenseData dispenseData) {
        this.dispenseData = dispenseData;
    }


}
