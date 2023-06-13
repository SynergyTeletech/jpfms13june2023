package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ATGData {

    @SerializedName("data_auto_id")
    @Expose
    private String data_auto_id;

    @SerializedName("data_vehicle_id")
    @Expose
    private String data_vehicle_id;

    @SerializedName("data_atg_serial_no")
    @Expose
    private String data_atg_serial_no;

    @SerializedName("data_volume")
    @Expose
    private String data_volume;


    public String getData_auto_id() {
        return data_auto_id;
    }

    public void setData_auto_id(String data_auto_id) {
        this.data_auto_id = data_auto_id;
    }

    public String getData_vehicle_id() {
        return data_vehicle_id;
    }

    public void setData_vehicle_id(String data_vehicle_id) {
        this.data_vehicle_id = data_vehicle_id;
    }

    public String getData_atg_serial_no() {
        return data_atg_serial_no;
    }

    public void setData_atg_serial_no(String data_atg_serial_no) {
        this.data_atg_serial_no = data_atg_serial_no;
    }

    public String getData_volume() {
        return data_volume;
    }

    public void setData_volume(String data_volume) {
        this.data_volume = data_volume;
    }
}
