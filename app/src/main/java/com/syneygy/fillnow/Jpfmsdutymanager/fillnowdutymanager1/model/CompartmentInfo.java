package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompartmentInfo implements Parcelable {

    @SerializedName("atg_auto_id")
    @Expose
    private String atgAutoId;
    @SerializedName("atg_vehicle_id")
    @Expose
    private String atgVehicleId;
    @SerializedName("atg_serial_no")
    @Expose
    private String atgSerialNo;
    @SerializedName("atg_dip_chart")
    @Expose
    private String atgDipChart;
    @SerializedName("atg_file")
    @Expose
    private String atgFile;

    public String getAtgAutoId() {
        return atgAutoId;
    }

    public void setAtgAutoId(String atgAutoId) {
        this.atgAutoId = atgAutoId;
    }

    public String getAtgVehicleId() {
        return atgVehicleId;
    }

    public void setAtgVehicleId(String atgVehicleId) {
        this.atgVehicleId = atgVehicleId;
    }

    public String getAtgDipChart() {
        return atgDipChart;
    }

    public void setAtgDipChart(String atgDipChart) {
        this.atgDipChart = atgDipChart;
    }

    public String getAtgSerialNo() {
        return atgSerialNo;
    }

    public void setAtgSerialNo(String atgSerialNo) {
        this.atgSerialNo = atgSerialNo;
    }

    public String getAtgFile() {
        return atgFile;
    }

    public void setAtgFile(String atgFile) {
        this.atgFile = atgFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.atgAutoId);
        dest.writeString(this.atgVehicleId);
        dest.writeString(this.atgSerialNo);
        dest.writeString(this.atgDipChart);
        dest.writeString(this.atgFile);
    }

    public CompartmentInfo() {
    }

    protected CompartmentInfo(Parcel in) {
        this.atgAutoId = in.readString();
        this.atgVehicleId = in.readString();
        this.atgSerialNo = in.readString();
        this.atgDipChart = in.readString();
        this.atgFile = in.readString();
    }

    public static final Parcelable.Creator<CompartmentInfo> CREATOR = new Parcelable.Creator<CompartmentInfo>() {
        @Override
        public CompartmentInfo createFromParcel(Parcel source) {
            return new CompartmentInfo(source);
        }

        @Override
        public CompartmentInfo[] newArray(int size) {
            return new CompartmentInfo[size];
        }
    };
}