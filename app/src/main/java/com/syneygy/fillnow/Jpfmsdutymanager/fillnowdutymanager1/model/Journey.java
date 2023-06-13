package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Journey {
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("staus")
    @Expose
    private String staus;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("login_id")
    @Expose
    private String loginId;
    @SerializedName("created_datatime")
    @Expose
    private String createdDatatime;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("logitude")
    @Expose
    private String logitude;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_phone")
    @Expose
    private String contactPersonPhone;
    @SerializedName("fname")
    @Expose
    private String fname;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getCreatedDatatime() {
        return createdDatatime;
    }

    public void setCreatedDatatime(String createdDatatime) {
        this.createdDatatime = createdDatatime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }







}
