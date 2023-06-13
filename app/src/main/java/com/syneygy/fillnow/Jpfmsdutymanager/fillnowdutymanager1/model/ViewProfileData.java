package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewProfileData implements Serializable {

    @SerializedName("location_id")
    @Expose
    public String location_id;

    @SerializedName("location_name")
    @Expose
    public String location_name;

    @SerializedName("latitude")
    @Expose
    public String latitude;

    @SerializedName("logitude")
    @Expose
    public String logitude;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("contact_person_name")
    @Expose
    public String contact_person_name;

    @SerializedName("company_name")
    @Expose
    public String company_name;

    @SerializedName("contact_person_designation")
    @Expose
    public String contact_person_designation;

    @SerializedName("contact_person_email")
    @Expose
    public String contact_person_email;

    @SerializedName("contact_person_phone")
    @Expose
    public String contact_person_phone;

    @SerializedName("gst_number")
    @Expose
    public String gst_number;

    @SerializedName("profile_img")
    @Expose
    public String profile_img;

//    @SerializedName("floor")
//    @Expose
//    public String floor;

//    @SerializedName("rack")
//    @Expose
//    public String rack;
//    @SerializedName("state")
//    @Expose
//    public String state;
//    @SerializedName("city")
//    @Expose
//    public String city;
//    @SerializedName("pin")
//    @Expose
//    public String pin;
//    @SerializedName("nb_of_asstes")
//    @Expose
//    public String nb_of_asstes;
//    @SerializedName("all_assets")
//    @Expose
//    public String all_assets;
//    @SerializedName("user_id")
//    @Expose
//    public String user_id;
//    @SerializedName("status")
//    @Expose
//    public String status;
//    @SerializedName("createdDate")
//    @Expose
//    public String createdDate;
//    @SerializedName("contact_person_sms_alert")
//    @Expose
//    public String contact_person_sms_alert;
//    @SerializedName("contact_person_email_alert")
//    @Expose
//    public String contact_person_email_alert;
//    @SerializedName("contact_person_web_alert")
//    @Expose
//    public String contact_person_web_alert;

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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
//
//    public String getFloor() {
//        return floor;
//    }
//
//    public void setFloor(String floor) {
//        this.floor = floor;
//    }


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getRack() {
//        return rack;
//    }
//
//    public void setRack(String rack) {
//        this.rack = rack;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getPin() {
//        return pin;
//    }
//
//    public void setPin(String pin) {
//        this.pin = pin;
//    }
//
//    public String getNb_of_asstes() {
//        return nb_of_asstes;
//    }
//
//    public void setNb_of_asstes(String nb_of_asstes) {
//        this.nb_of_asstes = nb_of_asstes;
//    }
//
//    public String getAll_assets() {
//        return all_assets;
//    }
//
//    public void setAll_assets(String all_assets) {
//        this.all_assets = all_assets;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }

    public String getContact_person_name() {
        return contact_person_name;
    }

    public void setContact_person_name(String contact_person_name) {
        this.contact_person_name = contact_person_name;
    }

    public String getContact_person_designation() {
        return contact_person_designation;
    }

    public void setContact_person_designation(String contact_person_designation) {
        this.contact_person_designation = contact_person_designation;
    }

    public String getContact_person_email() {
        return contact_person_email;
    }

    public void setContact_person_email(String contact_person_email) {
        this.contact_person_email = contact_person_email;
    }
//
//    public String getContact_person_sms_alert() {
//        return contact_person_sms_alert;
//    }
//
//    public void setContact_person_sms_alert(String contact_person_sms_alert) {
//        this.contact_person_sms_alert = contact_person_sms_alert;
//    }
//
//    public String getContact_person_email_alert() {
//        return contact_person_email_alert;
//    }
//
//    public void setContact_person_email_alert(String contact_person_email_alert) {
//        this.contact_person_email_alert = contact_person_email_alert;
//    }
//
//    public String getContact_person_web_alert() {
//        return contact_person_web_alert;
//    }
//
//    public void setContact_person_web_alert(String contact_person_web_alert) {
//        this.contact_person_web_alert = contact_person_web_alert;
//    }

    public String getContact_person_phone() {
        return contact_person_phone;
    }

    public void setContact_person_phone(String contact_person_phone) {
        this.contact_person_phone = contact_person_phone;
    }

    public String getGst_number() {
        return gst_number;
    }

    public void setGst_number(String gst_number) {
        this.gst_number = gst_number;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }
}
