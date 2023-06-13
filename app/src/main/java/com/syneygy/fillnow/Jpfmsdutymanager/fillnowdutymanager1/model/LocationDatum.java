package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationDatum implements Serializable{


    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("logitude")
    @Expose
    private String logitude;
    @SerializedName("floor")
    @Expose
    private Object floor;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("rack")
    @Expose
    private Object rack;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("nb_of_asstes")
    @Expose
    private String nbOfAsstes;
    @SerializedName("all_assets")
    @Expose
    private String allAssets;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_designation")
    @Expose
    private String contactPersonDesignation;
    @SerializedName("contact_person_email")
    @Expose
    private String contactPersonEmail;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("contact_person_sms_alert")
    @Expose
    private String contactPersonSmsAlert;
    @SerializedName("contact_person_email_alert")
    @Expose
    private String contactPersonEmailAlert;
    @SerializedName("contact_person_web_alert")
    @Expose
    private String contactPersonWebAlert;
    @SerializedName("contact_person_phone")
    @Expose
    private String contactPersonPhone;
    @SerializedName("gst_number")
    @Expose
    private String gstNumber;
    @SerializedName("daily_limit")
    @Expose
    private String dailyLimit;
    @SerializedName("monthly_limit")
    @Expose
    private String monthlyLimit;
    @SerializedName("adhoc_limit")
    @Expose
    private String adhocLimit;
    @SerializedName("login_mast_id")
    @Expose
    private String loginMastId;
    @SerializedName("location_serial_no")
    @Expose
    private String locationSerialNo;
    @SerializedName("daily_balance")
    @Expose
    private String dailyBalance;
    @SerializedName("monthly_balance")
    @Expose
    private String monthlyBalance;
    @SerializedName("adhoc_balance")
    @Expose
    private String adhocBalance;
    @SerializedName("login_id")
    @Expose
    private String loginId;
    @SerializedName("normal_delivery_fee")
    @Expose
    private String normalDeliveryFee;
    @SerializedName("express_delivery_fee")
    @Expose
    private String expressDeliveryFee;
    @SerializedName("priority_delivery_fee")
    @Expose
    private String priorityDeliveryFee;

    @SerializedName("happy_hour_discount")
    @Expose
    private String happy_hour_discount;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    private final static long serialVersionUID = 1797545240115183755L;

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

    public Object getFloor() {
        return floor;
    }

    public void setFloor(Object floor) {
        this.floor = floor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getRack() {
        return rack;
    }

    public void setRack(Object rack) {
        this.rack = rack;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNbOfAsstes() {
        return nbOfAsstes;
    }

    public void setNbOfAsstes(String nbOfAsstes) {
        this.nbOfAsstes = nbOfAsstes;
    }

    public String getAllAssets() {
        return allAssets;
    }

    public void setAllAssets(String allAssets) {
        this.allAssets = allAssets;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonDesignation() {
        return contactPersonDesignation;
    }

    public void setContactPersonDesignation(String contactPersonDesignation) {
        this.contactPersonDesignation = contactPersonDesignation;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getContactPersonSmsAlert() {
        return contactPersonSmsAlert;
    }

    public void setContactPersonSmsAlert(String contactPersonSmsAlert) {
        this.contactPersonSmsAlert = contactPersonSmsAlert;
    }

    public String getContactPersonEmailAlert() {
        return contactPersonEmailAlert;
    }

    public void setContactPersonEmailAlert(String contactPersonEmailAlert) {
        this.contactPersonEmailAlert = contactPersonEmailAlert;
    }

    public String getContactPersonWebAlert() {
        return contactPersonWebAlert;
    }

    public void setContactPersonWebAlert(String contactPersonWebAlert) {
        this.contactPersonWebAlert = contactPersonWebAlert;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(String monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getAdhocLimit() {
        return adhocLimit;
    }

    public void setAdhocLimit(String adhocLimit) {
        this.adhocLimit = adhocLimit;
    }

    public String getLoginMastId() {
        return loginMastId;
    }

    public void setLoginMastId(String loginMastId) {
        this.loginMastId = loginMastId;
    }

    public String getLocationSerialNo() {
        return locationSerialNo;
    }

    public void setLocationSerialNo(String locationSerialNo) {
        this.locationSerialNo = locationSerialNo;
    }

    public String getDailyBalance() {
        return dailyBalance;
    }

    public void setDailyBalance(String dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public String getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(String monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public String getAdhocBalance() {
        return adhocBalance;
    }

    public void setAdhocBalance(String adhocBalance) {
        this.adhocBalance = adhocBalance;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNormalDeliveryFee() {
        return normalDeliveryFee;
    }

    public void setNormalDeliveryFee(String normalDeliveryFee) {
        this.normalDeliveryFee = normalDeliveryFee;
    }

    public String getExpressDeliveryFee() {
        return expressDeliveryFee;
    }

    public void setExpressDeliveryFee(String expressDeliveryFee) {
        this.expressDeliveryFee = expressDeliveryFee;
    }

    public String getPriorityDeliveryFee() {
        return priorityDeliveryFee;
    }

    public void setPriorityDeliveryFee(String priorityDeliveryFee) {
        this.priorityDeliveryFee = priorityDeliveryFee;
    }

    public String getHappy_hour_discount() {
        return happy_hour_discount;
    }

    public void setHappy_hour_discount(String happy_hour_discount) {
        this.happy_hour_discount = happy_hour_discount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
