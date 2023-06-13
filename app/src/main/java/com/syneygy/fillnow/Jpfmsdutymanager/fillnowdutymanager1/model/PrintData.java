package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrintData implements Serializable {

    @SerializedName("order_data_time")
    @Expose
    private String order_data_time;

    @SerializedName("fueling_start_time")
    @Expose
    private String fueling_start_time;

    @SerializedName("fueling_stop_time")
    @Expose
    private String fueling_stop_time;

    @SerializedName("assets_id")
    @Expose
    private String assets_id;

    @SerializedName("assets_name")
    @Expose
    private String assets_name;

    @SerializedName("dispense_qty")
    @Expose
    private String dispense_qty;

    @SerializedName("meter_reading")
    @Expose
    private String meter_reading;

    @SerializedName("asset_other_reading")
    @Expose
    private String asset_other_reading;
 @SerializedName("asset_remark")
 @Expose
 private  String asset_remark;
    @SerializedName("asset_other_reading2")
    @Expose
    private String asset_other_reading2;

    @SerializedName("atg_tank_start_reading")
    @Expose
    private String atg_tank_start_reading;

    @SerializedName("atg_tank_end_reading")
    @Expose
    private String atg_tank_end_reading;

    @SerializedName("volume_totalizer")
    @Expose
    private String volume_totalizer;

    @SerializedName("no_of_event_start_stop")
    @Expose
    private String no_of_event_start_stop;

    @SerializedName("dispensed_in")
    @Expose
    private String dispensed_in;

    @SerializedName("rfid_status")
    @Expose
    private String rfid_status;

    @SerializedName("transaction_type")
    @Expose
    private String transaction_type;

    @SerializedName("terminal_id")
    @Expose
    private String terminal_id;

    @SerializedName("batch_no")
    @Expose
    private String batch_no;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("location_reading_1")
    @Expose
    private String location_reading_1;

    @SerializedName("location_reading_2")
    @Expose
    private String location_reading_2;

    @SerializedName("gps_status")
    @Expose
    private String gps_status;

    @SerializedName("dcv_status")
    @Expose
    private String dcv_status;

    @SerializedName("flag")
    @Expose
    private String flag;

    @SerializedName("transaction_no")
    @Expose
    private String transaction_no;

    @SerializedName("unit_price")
    @Expose
    private String unit_price;

    @SerializedName("vehicle_id")
    @Expose
    private String vehicle_id;

    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("location_name")
    @Expose
    private String locationName;

    @SerializedName("total_qty")
    @Expose
    private String total_qty;

    private String GST;
    private String total_amount;
    private String duty_id;
    private String location_id;



    private String footer_message;

    private String deliveredBy;
    private String elock_start_reading;
    private  String elock_end_reading;



    public PrintData() { }


    public PrintData(String order_data_time, String fueling_start_time, String fueling_stop_time, String assets_id, String dispense_qty, String meter_reading, String asset_other_reading, String atg_tank_start_reading, String atg_tank_end_reading, String volume_totalizer, String no_of_event_start_stop, String dispensed_in, String rfid_status, String transaction_type, String terminal_id, String batch_no, String latitude, String longitude, String location_reading_1, String location_reading_2, String gps_status, String dcv_status, String flag, String transaction_no, String unit_price, String vehicle_id, String transaction_id, String order_id, String amount, String asset_other_reading2, String asset_remark) {
        this.order_data_time = order_data_time;
        this.fueling_start_time = fueling_start_time;
        this.fueling_stop_time = fueling_stop_time;
        this.assets_id = assets_id;
        this.dispense_qty = dispense_qty;
        this.meter_reading = meter_reading;
        this.asset_other_reading = asset_other_reading;
        this.asset_other_reading2 = asset_other_reading2;
        this.asset_remark = asset_remark;

        this.atg_tank_start_reading = atg_tank_start_reading;
        this.atg_tank_end_reading = atg_tank_end_reading;
        this.volume_totalizer = volume_totalizer;
        this.no_of_event_start_stop = no_of_event_start_stop;
        this.dispensed_in = dispensed_in;
        this.rfid_status = rfid_status;
        this.transaction_type = transaction_type;
        this.terminal_id = terminal_id;
        this.batch_no = batch_no;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location_reading_1 = location_reading_1;
        this.location_reading_2 = location_reading_2;
        this.gps_status = gps_status;
        this.dcv_status = dcv_status;
        this.flag = flag;
        this.transaction_no = transaction_no;
        this.unit_price = unit_price;
        this.vehicle_id = vehicle_id;
        this.transaction_id = transaction_id;
        this.order_id = order_id;
        this.amount = amount;
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

    public String getElock_start_reading() {
        return elock_start_reading;
    }

    public void setElock_start_reading(String elock_start_reading) {
        this.elock_start_reading = elock_start_reading;
    }

    public String getElock_end_reading() {
        return elock_end_reading;
    }

    public void setElock_end_reading(String elock_end_reading) {
        this.elock_end_reading = elock_end_reading;
    }

    public void setFueling_stop_time(String fueling_stop_time) {
        this.fueling_stop_time = fueling_stop_time;
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

    public String getMeter_reading() {
        return meter_reading;
    }

    public void setMeter_reading(String meter_reading) {
        this.meter_reading = meter_reading;
    }

    public String getAsset_other_reading() {
        return asset_other_reading;
    }

    public String getAsset_other_reading2() {
        return asset_other_reading2;
    }

    public void setAsset_other_reading2(String asset_other_reading2) {//remark from add reading dialog
        this.asset_other_reading2 = asset_other_reading2;
    }

    public void setAsset_other_reading(String asset_other_reading) {
        this.asset_other_reading = asset_other_reading;
    }
    public void setAsset_remark(String asset_remark) {
        this.asset_remark = asset_remark;
    }
    public  String getAsset_remark() {
        return asset_remark;

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

    public String getVolume_totalizer() {
        return volume_totalizer;
    }

    public void setVolume_totalizer(String volume_totalizer) {
        this.volume_totalizer = volume_totalizer;
    }

    public String getNo_of_event_start_stop() {
        return no_of_event_start_stop;
    }

    public void setNo_of_event_start_stop(String no_of_event_start_stop) {
        this.no_of_event_start_stop = no_of_event_start_stop;
    }

    public String getDispensed_in() {
        return dispensed_in;
    }

    public void setDispensed_in(String dispensed_in) {
        this.dispensed_in = dispensed_in;
    }

    public String getRfid_status() {
        return rfid_status;
    }

    public void setRfid_status(String rfid_status) {
        this.rfid_status = rfid_status;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_data_time() {
        return order_data_time;
    }

    public void setOrder_data_time(String order_data_time) {
        this.order_data_time = order_data_time;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAssets_name() {
        return assets_name;
    }

    public void setAssets_name(String assets_name) {
        this.assets_name = assets_name;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(String duty_id) {
        this.duty_id = duty_id;
    }

    public String getFooter_message() {
        return footer_message;
    }

    public void setFooter_message(String footer_message) {
        this.footer_message = footer_message;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }


}
