package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Progress implements Parcelable {

    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("fuel_price")
    @Expose
    private String fuelPrice;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("current_lat")
    @Expose
    private String currentLat;
    @SerializedName("current_long")
    @Expose
    private String currentLong;
    @SerializedName("valve_bypass")
    @Expose
    private Boolean valveBypass;
    @SerializedName("lock_bypass")
    @Expose
    private Boolean lockBypass;
    @SerializedName("atg_bypass")
    @Expose
    private Boolean atgBypass;
    @SerializedName("location_bypass")
    @Expose
    private Boolean locationBypass;
    @SerializedName("franchise_bypass")
    @Expose
    private Boolean franchiseBypass;
    @SerializedName("fr_current_lat")
    @Expose
    private String frCurrentLat;
    @SerializedName("fr_current_long")
    @Expose
    private String frCurrentLong;
    @SerializedName("delivered_data")
    @Expose
    private String deliveredData;
    @SerializedName("order_status_type")
    String status_type;

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getFrCurrentLat() {
        return frCurrentLat;
    }

    public void setFrCurrentLat(String frCurrentLat) {
        this.frCurrentLat = frCurrentLat;
    }

    public String getFrCurrentLong() {
        return frCurrentLong;
    }

    public void setFrCurrentLong(String frCurrentLong) {
        this.frCurrentLong = frCurrentLong;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(String fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(String currentLat) {
        this.currentLat = currentLat;
    }

    public String getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(String currentLong) {
        this.currentLong = currentLong;
    }

    public Boolean getValveBypass() {
        return valveBypass;
    }

    public void setValveBypass(Boolean valveBypass) {
        this.valveBypass = valveBypass;
    }

    public Boolean getLockBypass() {
        return lockBypass;
    }

    public void setLockBypass(Boolean lockBypass) {
        this.lockBypass = lockBypass;
    }

    public Boolean getAtgBypass() {
        return atgBypass;
    }

    public void setAtgBypass(Boolean atgBypass) {
        this.atgBypass = atgBypass;
    }

    public Boolean getLocationBypass() {
        return locationBypass;
    }

    public void setLocationBypass(Boolean locationBypass) {
        this.locationBypass = locationBypass;
    }

    public Boolean getFranchiseBypass() {
        return franchiseBypass;
    }

    public void setFranchiseBypass(Boolean franchiseBypass) {
        this.franchiseBypass = franchiseBypass;
    }

    public String getDeliveredData() {
        return deliveredData;
    }

    public void setDeliveredData(String deliveredData) {
        this.deliveredData = deliveredData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locationId);
        dest.writeString(this.locationName);
        dest.writeString(this.fuelPrice);
        dest.writeString(this.paymentStatus);
        dest.writeString(this.discount);
        dest.writeString(this.discountType);
        dest.writeString(this.currentLat);
        dest.writeString(this.currentLong);
        dest.writeString(this.deliveredData);
        dest.writeValue(this.valveBypass);
        dest.writeValue(this.lockBypass);
        dest.writeValue(this.atgBypass);
        dest.writeValue(this.locationBypass);
        dest.writeValue(this.franchiseBypass);
        dest.writeString(this.frCurrentLat);
        dest.writeString(this.frCurrentLong);
    }

    public Progress() { }

    protected Progress(Parcel in) {
        this.locationId = in.readString();
        this.locationName = in.readString();
        this.fuelPrice = in.readString();
        this.paymentStatus = in.readString();
        this.discount = in.readString();
        this.discountType = in.readString();
        this.currentLat = in.readString();
        this.currentLong = in.readString();
        this.deliveredData = in.readString();
        this.valveBypass = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.lockBypass = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.atgBypass = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.locationBypass = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.franchiseBypass = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.frCurrentLat=in.readString();
        this.frCurrentLong=in.readString();
    }

    public static final Parcelable.Creator<Progress> CREATOR = new Parcelable.Creator<Progress>() {
        @Override
        public Progress createFromParcel(Parcel source) {
            return new Progress(source);
        }

        @Override
        public Progress[] newArray(int size) {
            return new Progress[size];
        }
    };
}
