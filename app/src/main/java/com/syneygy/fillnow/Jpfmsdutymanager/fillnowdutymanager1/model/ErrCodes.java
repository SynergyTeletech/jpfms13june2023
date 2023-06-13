package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ErrCodes implements Serializable
{

    @SerializedName("location_data")
    @Expose
    private List<LocationDatum> locationData = null;

    @SerializedName("vehicle_data")
    @Expose
    private List<LocationVehicleDatum> vehicleData = null;

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

}