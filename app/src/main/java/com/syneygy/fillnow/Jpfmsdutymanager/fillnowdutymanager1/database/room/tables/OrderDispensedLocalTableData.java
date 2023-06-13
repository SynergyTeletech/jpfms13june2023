package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "new_offline_order_table")
public class OrderDispensedLocalTableData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int orderLocalID;

    @ColumnInfo(name = "transaction_id")
    private String transaction_id;

    @ColumnInfo(name = "vehicle_id")
    private String vehicle_id;

    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "assets_id")
    private String assets_id;

    @ColumnInfo(name = "dispense_qty")
    private String dispense_qty;

    @ColumnInfo(name = "transaction_no")
    private String transaction_no;

    @ColumnInfo(name = "unit_price")
    private String unit_price;

    @ColumnInfo(name = "transaction_type")
    private String transaction_type;

    @ColumnInfo(name = "fueling_start_time")
    private String fueling_start_time;

    @ColumnInfo(name = "fueling_stop_time")
    private String fueling_stop_time;

    @ColumnInfo(name = "meter_reading")
    private String meter_reading;

    @ColumnInfo(name = "asset_other_reading")
    private String asset_other_reading;

    @ColumnInfo(name = "atg_tank_start_reading")
    private String atg_tank_start_reading;

    @ColumnInfo(name = "atg_tank_end_reading")
    private String atg_tank_end_reading;

    @ColumnInfo(name = "terminal_id")
    private String terminal_id;

    @ColumnInfo(name = "batch_no")
    private String batch_no;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "location_reading_1")
    private String location_reading_1;

    @ColumnInfo(name = "location_reading_2")
    private String location_reading_2;

    @ColumnInfo(name = "gps_status")
    private String gps_status;

    @ColumnInfo(name = "dcv_status")
    private String dcv_status;

    @ColumnInfo(name = "rfid_status")
    private String rfid_status;

    @ColumnInfo(name = "dispensed_in")
    private String dispensed_in;

    @ColumnInfo(name = "no_of_event_start_stop")
    private String no_of_event_start_stop;

    @ColumnInfo(name = "volume_totalizer")
    private String volume_totalizer;


    public int getOrderLocalID() {
        return orderLocalID;
    }

    public void setOrderLocalID(int orderLocalID) {
        this.orderLocalID = orderLocalID;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAssets_id() {
        return assets_id;
    }

    public void setAssets_id(String assets_id) {
        this.assets_id = assets_id;
    }

    public String getDispense_qty() {
        return dispense_qty;
    }

    public void setDispense_qty(String dispense_qty) {
        this.dispense_qty = dispense_qty;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getFueling_start_time() {
        return fueling_start_time;
    }

    public void setFueling_start_time(String fueling_start_time) {
        this.fueling_start_time = fueling_start_time;
    }

    public String getFueling_stop_time() {
        return fueling_stop_time;
    }

    public void setFueling_stop_time(String fueling_stop_time) {
        this.fueling_stop_time = fueling_stop_time;
    }

    public String getMeter_reading() {
        return meter_reading;
    }

    public void setMeter_reading(String meter_reading) {
        this.meter_reading = meter_reading;
    }

    public String getAsset_other_reading() {
        return asset_other_reading;
    }

    public void setAsset_other_reading(String asset_other_reading) {
        this.asset_other_reading = asset_other_reading;
    }

    public String getAtg_tank_start_reading() {
        return atg_tank_start_reading;
    }

    public void setAtg_tank_start_reading(String atg_tank_start_reading) {
        this.atg_tank_start_reading = atg_tank_start_reading;
    }

    public String getAtg_tank_end_reading() {
        return atg_tank_end_reading;
    }

    public void setAtg_tank_end_reading(String atg_tank_end_reading) {
        this.atg_tank_end_reading = atg_tank_end_reading;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation_reading_1() {
        return location_reading_1;
    }

    public void setLocation_reading_1(String location_reading_1) {
        this.location_reading_1 = location_reading_1;
    }

    public String getLocation_reading_2() {
        return location_reading_2;
    }

    public void setLocation_reading_2(String location_reading_2) {
        this.location_reading_2 = location_reading_2;
    }

    public String getGps_status() {
        return gps_status;
    }

    public void setGps_status(String gps_status) {
        this.gps_status = gps_status;
    }

    public String getDcv_status() {
        return dcv_status;
    }

    public void setDcv_status(String dcv_status) {
        this.dcv_status = dcv_status;
    }

    public String getRfid_status() {
        return rfid_status;
    }

    public void setRfid_status(String rfid_status) {
        this.rfid_status = rfid_status;
    }

    public String getDispensed_in() {
        return dispensed_in;
    }

    public void setDispensed_in(String dispensed_in) {
        this.dispensed_in = dispensed_in;
    }

    public String getNo_of_event_start_stop() {
        return no_of_event_start_stop;
    }

    public void setNo_of_event_start_stop(String no_of_event_start_stop) {
        this.no_of_event_start_stop = no_of_event_start_stop;
    }

    public String getVolume_totalizer() {
        return volume_totalizer;
    }

    public void setVolume_totalizer(String volume_totalizer) {
        this.volume_totalizer = volume_totalizer;
    }
}
