package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehicleLiveLocation implements Serializable {

    @SerializedName("live_vehicle_lat")
    @Expose
    private String live_vehicle_lat;

    @SerializedName("live_vehicle_lng")
    @Expose
    private String live_vehicle_lng;


    public String getLive_vehicle_lat() {
        return live_vehicle_lat;
    }

    public void setLive_vehicle_lat(String live_vehicle_lat) {
        this.live_vehicle_lat = live_vehicle_lat;
    }

    public String getLive_vehicle_lng() {
        return live_vehicle_lng;
    }

    public void setLive_vehicle_lng(String live_vehicle_lng) {
        this.live_vehicle_lng = live_vehicle_lng;
    }
}
