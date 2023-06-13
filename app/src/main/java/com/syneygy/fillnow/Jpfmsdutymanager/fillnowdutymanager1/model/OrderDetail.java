package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail implements Parcelable {

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
    @SerializedName("order_date")
    @Expose
    private String orderDate;
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

    @SerializedName("ticket_id")
    @Expose
    private String ticket_id;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("lead_number")
    @Expose
    private String lead_number;
    @SerializedName("order_status_type")
    String status_type;

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.transactionId);
        dest.writeString(this.flag);
        dest.writeString(this.vehicleId);
        dest.writeString(this.orderId);
        dest.writeString(this.orderDate);
        dest.writeString(this.orderType);
        dest.writeString(this.fuel);
        dest.writeString(this.locationId);
        dest.writeString(this.staus);
        dest.writeString(this.qty);
        dest.writeString(this.loginId);
        dest.writeString(this.createdDatatime);
        dest.writeString(this.locationName);
        dest.writeString(this.branchId);
        dest.writeString(this.address);
        dest.writeString(this.latitude);
        dest.writeString(this.logitude);
        dest.writeString(this.contactPersonName);
        dest.writeString(this.contactPersonPhone);
        dest.writeString(this.performaInvoiceNo);
        dest.writeString(this.fname);
        dest.writeString(this.ticket_id);
        dest.writeString(this.customer_name);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.transactionId = in.readString();
        this.flag = in.readString();
        this.vehicleId = in.readString();
        this.orderId = in.readString();
        this.orderDate = in.readString();
        this.orderType = in.readString();
        this.fuel = in.readString();
        this.locationId = in.readString();
        this.staus = in.readString();
        this.qty = in.readString();
        this.loginId = in.readString();
        this.createdDatatime = in.readString();
        this.locationName = in.readString();
        this.branchId = in.readString();
        this.address = in.readString();
        this.latitude = in.readString();
        this.logitude = in.readString();
        this.contactPersonName = in.readString();
        this.contactPersonPhone = in.readString();
        this.performaInvoiceNo = in.readString();
        this.fname = in.readString();
        this.ticket_id=in.readString();
        this.customer_name=in.readString();
    }

    public static final Parcelable.Creator<OrderDetail> CREATOR = new Parcelable.Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };

    public String getLead_number() {
        return lead_number;
    }

    public void setLead_number(String lead_number) {
        this.lead_number = lead_number;
    }
}
