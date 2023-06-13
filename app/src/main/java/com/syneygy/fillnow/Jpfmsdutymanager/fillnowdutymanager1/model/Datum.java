package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("login_id")
    @Expose
    private String login_id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("group_id")
    @Expose
    private String group_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("vehicle_id")
    @Expose
    private String vehicle_id;

    @SerializedName("duty_id")
    @Expose
    private String duty_id;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("random_code")
    @Expose
    private Integer randomCode;

    @SerializedName("timeslot")
    @Expose
    private List<LoginResponseDatumTimeslot> timeslot = null;

    @SerializedName("location_data")
    @Expose
    private List<LocationDatum> locationData = null;

    @SerializedName("vehicle_data")
    @Expose
    private List<LocationVehicleDatum> vehicleData = null;

    @SerializedName("assets")
    @Expose
    private List<Asset> assetData = null;

    @SerializedName("jsondata")
    @Expose
    public List<ATGData > atgDataList;

    @SerializedName("price")
    @Expose
    private Price price;

    @SerializedName("vehicle_live_location")
    @Expose
    private List<VehicleLiveLocation> VehicleLiveLocation;

    private final static long serialVersionUID = -6856099990010982654L;

    public List<LocationDatum> getLocationData() {
        return locationData;
    }

    public void setLocationData(List<LocationDatum> locationData) {
        this.locationData = locationData;
    }

    public List<LocationVehicleDatum> getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(List<LocationVehicleDatum> vehicleData) {
        this.vehicleData = vehicleData;
    }

//    @SerializedName("user_type")
//    private String user_type;
//
//    @SerializedName("entity_type")
//    private String entity_type;
//
//    @SerializedName("user_entity_type")
//    private String user_entity_type;
//
//    @SerializedName("single_login_id")
//    private String single_login_id;
//
//    @SerializedName("cust_ref_num")
//    private String cust_ref_num;
//
//    @SerializedName("reporting_to")
//    private String reporting_to;
//
//    @SerializedName("enterprise_group")
//    private String enterprise_group;
//
//    @SerializedName("both_e_sme_rights")
//    private String both_e_sme_rights;
//
//    @SerializedName("wipro_crm_id")
//    private String wipro_crm_id;
//
//    @SerializedName("old_id")
//    private String old_id;
//
//    @SerializedName("login_time")
//    private String login_time;
//
//    @SerializedName("logout_time")
//    private String logout_time;
//
//    @SerializedName("login_attempt")
//
//    private String login_attempt;

    public Datum() { }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(String duty_id) {
        this.duty_id = duty_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(Integer randomCode) {
        this.randomCode = randomCode;
    }

    public List<LoginResponseDatumTimeslot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(List<LoginResponseDatumTimeslot> timeslot) {
        this.timeslot = timeslot;
    }

    public List<Asset> getAssetData() {
        return assetData;
    }

    public void setAssetData(List<Asset> assetData) {
        this.assetData = assetData;
    }

    public List<ATGData> getAtgDataList() {
        return atgDataList;
    }

    public void setAtgDataList(List<ATGData> atgDataList) {
        this.atgDataList = atgDataList;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.VehicleLiveLocation> getVehicleLiveLocation() {
        return VehicleLiveLocation;
    }

    public void setVehicleLiveLocation(List<com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.VehicleLiveLocation> vehicleLiveLocation) {
        VehicleLiveLocation = vehicleLiveLocation;
    }
}
