package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk;

import java.io.Serializable;

public class OrderDispenseLocalData implements Serializable {
    private int orderLocalID;
    private String transaction_id;
    private String vehicle_id;
    private String flag;
    private String assets_id;
    private String dispense_qty;
    private String transaction_no;
    private String unit_price;
    private String transaction_type;
    private String fueling_start_time;
    private String fueling_stop_time;
    private String meter_reading;
    private String asset_reading2;
    private String asset_remark;
    private String asset_other_reading;
    private String asset_other_reading_2;
    private String atg_tank_start_reading;
    private String atg_tank_end_reading;
    private String terminal_id;
    private String batch_no;
    private String latitude;
    private String longitude;
    private String location_reading_1;
    private String location_reading_2;
    private String gps_status;
    private String dcv_status;
    private String rfid_status;
    private String dispensed_in;
    private String no_of_event_start_stop;
    private String volume_totalizer;
    private String total_qty;
    private String order_id;
    private String duty_id;
    private String location_id;
    private String customer_name;
    private String footer_message;
    private String gst_percentage;
    private String total_amount;
    private String deliveredBy;
    private String status;
    private String initial_volume_totalizer;
    private String elock_start_reading;
    private  String elock_end_reading;
    public String getAsset_reading2() {
        return asset_reading2;
    }

    public void setAsset_reading2(String asset_reading2) {
        this.asset_reading2 = asset_reading2;
    }

    public String getAsset_remark() {
        return asset_remark;
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

    public void setAsset_remark(String asset_remark) {
        this.asset_remark = asset_remark;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitial_volume_totalizer() {
        return initial_volume_totalizer;
    }

    public void setInitial_volume_totalizer(String initial_volume_totalizer) {
        this.initial_volume_totalizer = initial_volume_totalizer;
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

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(String duty_id) {
        this.duty_id = duty_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAsset_other_reading_2() {
        return asset_other_reading_2;
    }

    public void setAsset_other_reading_2(String asset_other_reading_2) {
        this.asset_other_reading_2 = asset_other_reading_2;
    }

    public String getFooter_message() {
        return footer_message;
    }

    public void setFooter_message(String footer_message) {
        this.footer_message = footer_message;
    }

    public String getGst_percentage() {
        return gst_percentage;
    }

    public void setGst_percentage(String gst_percentage) {
        this.gst_percentage = gst_percentage;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    @Override
    public String toString() {
        return "OrderDispenseLocalData{" +
                "orderLocalID=" + orderLocalID +
                ", transaction_id='" + transaction_id + '\'' +
                ", vehicle_id='" + vehicle_id + '\'' +
                ", flag='" + flag + '\'' +
                ", assets_id='" + assets_id + '\'' +
                ", dispense_qty='" + dispense_qty + '\'' +
                ", transaction_no='" + transaction_no + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", fueling_start_time='" + fueling_start_time + '\'' +
                ", fueling_stop_time='" + fueling_stop_time + '\'' +
                ", meter_reading='" + meter_reading + '\'' +
                ", asset_reading2='" + asset_reading2 + '\'' +
                ", asset_remark='" + asset_remark + '\'' +
                ", asset_other_reading='" + asset_other_reading + '\'' +
                ", asset_other_reading_2='" + asset_other_reading_2 + '\'' +
                ", atg_tank_start_reading='" + atg_tank_start_reading + '\'' +
                ", atg_tank_end_reading='" + atg_tank_end_reading + '\'' +
                ", terminal_id='" + terminal_id + '\'' +
                ", batch_no='" + batch_no + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", location_reading_1='" + location_reading_1 + '\'' +
                ", location_reading_2='" + location_reading_2 + '\'' +
                ", gps_status='" + gps_status + '\'' +
                ", dcv_status='" + dcv_status + '\'' +
                ", rfid_status='" + rfid_status + '\'' +
                ", dispensed_in='" + dispensed_in + '\'' +
                ", no_of_event_start_stop='" + no_of_event_start_stop + '\'' +
                ", volume_totalizer='" + volume_totalizer + '\'' +
                ", total_qty='" + total_qty + '\'' +
                ", order_id='" + order_id + '\'' +
                ", duty_id='" + duty_id + '\'' +
                ", location_id='" + location_id + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", footer_message='" + footer_message + '\'' +
                ", gst_percentage='" + gst_percentage + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", deliveredBy='" + deliveredBy + '\'' +
                ", status='" + status + '\'' +
                ", initial_volume_totalizer='" + initial_volume_totalizer + '\'' +
                ", elock_start_reading='" + elock_start_reading + '\'' +
                ", elock_end_reading='" + elock_end_reading + '\'' +
                '}';
    }
}
