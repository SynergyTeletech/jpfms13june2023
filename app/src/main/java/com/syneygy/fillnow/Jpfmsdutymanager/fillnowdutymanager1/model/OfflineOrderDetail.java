package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfflineOrderDetail implements Serializable {

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("fuel")
    @Expose
    private String fuel;
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
    @SerializedName("branch_id")
    @Expose
    private String branchId;
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
    @SerializedName("performa_invoice_no")
    @Expose
    private String performaInvoiceNo;
    @SerializedName("fname")
    @Expose
    private String fname;


    public OfflineOrderDetail(String transactionId, String flag, String vehicleId, String orderId, String orderType, String fuel, String locationId, String staus, String qty, String loginId, String createdDatatime, String locationName, String branchId, String address, String latitude, String logitude, String contactPersonName, String contactPersonPhone, String performaInvoiceNo, String fname) {
        this.transactionId = transactionId;
        this.flag = flag;
        this.vehicleId = vehicleId;
        this.orderId = orderId;
        this.orderType = orderType;
        this.fuel = fuel;
        this.locationId = locationId;
        this.staus = staus;
        this.qty = qty;
        this.loginId = loginId;
        this.createdDatatime = createdDatatime;
        this.locationName = locationName;
        this.branchId = branchId;
        this.address = address;
        this.latitude = latitude;
        this.logitude = logitude;
        this.contactPersonName = contactPersonName;
        this.contactPersonPhone = contactPersonPhone;
        this.performaInvoiceNo = performaInvoiceNo;
        this.fname = fname;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public String getPerformaInvoiceNo() {
        return performaInvoiceNo;
    }

    public void setPerformaInvoiceNo(String performaInvoiceNo) {
        this.performaInvoiceNo = performaInvoiceNo;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
