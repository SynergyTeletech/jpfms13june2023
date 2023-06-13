package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order implements Parcelable {

    private boolean isSelected = false;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("lead_number")
    @Expose
    private String lead_number;

    @SerializedName("location_id")
    @Expose
    private String location_id;

    @SerializedName("asset_wise")
    @Expose
    private String asset_wise;

    @SerializedName("staus")
    @Expose
    private String staus;

    @SerializedName("slot_name")
    @Expose
    private String slotname;

    @SerializedName("order_date")
    @Expose
    private String orderdate;

    @SerializedName("qty")
    @Expose
    private String quantity;
    @SerializedName("registration_no")
    @Expose
    private String registration_no;

    @SerializedName("login_id")
    @Expose
    private String login_id;

    @SerializedName("asset_flag")
    @Expose
    private boolean asset_flag;

    @SerializedName("asset_name")
    @Expose
    private String asset_name;

    @SerializedName("created_datatime")
    @Expose
    private String created_datatime;

    @SerializedName("location_name")
    @Expose
    private String location_name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("logitude")
    @Expose
    private String longitude;

    @SerializedName("contact_person_name")
    @Expose
    private String order_contact_person_name;

    @SerializedName("contact_person_phone")
    @Expose
    private String contact_person_phone;

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;
    @SerializedName("offline_otp")
    @Expose
    private String offline_otp;

    @SerializedName("performa_invoice_no")
    @Expose
    private String performaId;

    @SerializedName("branch_id")
    @Expose
    private String branchID;

    @SerializedName("order_type")
    @Expose
    private String orderType;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    @SerializedName("slot_id")
    @Expose
    private String slotId;

    @SerializedName("delivered_data")
    @Expose
    private String delivered_data;

    @SerializedName("ticket_id")
    @Expose
    private String ticket_id;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("total_dispense_qty")
    @Expose
    private String total_dispense_qty;

    @SerializedName("unit_price")
    @Expose
    private String unit_price;

    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    @SerializedName("dispense_latitude")
    @Expose
    private String dispense_latitude;

    @SerializedName("dispense_longitude")
    @Expose
    private String dispense_longitude;


    @SerializedName("gst_percentage")
    @Expose
    private String gst_percentage;

    @SerializedName("asset_other_reading_2")
    @Expose
    private String asset_other_reading_2;



    @SerializedName("location_reading_1")
    @Expose
    private String location_reading_1;


    public Order() { }

    public boolean isSelected() {
        return isSelected;
    }

    public String getGst_percentage() {
        return gst_percentage;
    }

    public void setGst_percentage(String gst_percentage) {
        this.gst_percentage = gst_percentage;
    }

    public String getAsset_other_reading_2() {
        return asset_other_reading_2;
    }

    public void setAsset_other_reading_2(String asset_other_reading_2) {
        this.asset_other_reading_2 = asset_other_reading_2;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }


    public String getLocation_reading_1() {
        return location_reading_1;
    }

    public void setLocation_reading_1(String location_reading_1) {
        this.location_reading_1 = location_reading_1;
    }

    public Order(boolean isSelected, String order_id, String asset_wise,String lead_number, String location_id, String staus, String slotname, String orderdate, String quantity, String login_id, boolean asset_flag, String asset_name, String created_datatime, String location_name, String address, String latitude, String longitude, String order_contact_person_name, String contact_person_phone, String fname, String transaction_id, String offline_otp, String performaId, String branchID, String orderType, String fuel, String slotId, String delivered_data, String ticket_id, String customer_name, String total_dispense_qty, String unit_price, String total_amount, String dispense_latitude, String dispense_longitude, String gst_percentage, String asset_other_reading_2, String location_reading_1) {
        this.isSelected = isSelected;
        this.order_id = order_id;
        this.lead_number = lead_number;
        this.location_id = location_id;
        this.staus = staus;
        this.slotname = slotname;
        this.orderdate = orderdate;
        this.quantity = quantity;
        this.login_id = login_id;
        this.asset_flag = asset_flag;
        this.asset_name = asset_name;
        this.created_datatime = created_datatime;
        this.location_name = location_name;
        this.asset_wise = asset_wise;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.order_contact_person_name = order_contact_person_name;
        this.contact_person_phone = contact_person_phone;
        this.fname = fname;
        this.transaction_id = transaction_id;
        this.offline_otp = offline_otp;
        this.performaId = performaId;
        this.branchID = branchID;
        this.orderType = orderType;
        this.fuel = fuel;
        this.slotId = slotId;
        this.delivered_data = delivered_data;
        this.ticket_id = ticket_id;
        this.customer_name = customer_name;
        this.total_dispense_qty = total_dispense_qty;
        this.unit_price = unit_price;
        this.total_amount = total_amount;
        this.dispense_latitude = dispense_latitude;
        this.dispense_longitude = dispense_longitude;
        this.gst_percentage = gst_percentage;
        this.asset_other_reading_2 = asset_other_reading_2;
        this.location_reading_1 = location_reading_1;
    }

    public String getTotal_dispense_qty() {
        return total_dispense_qty;
    }

    public void setTotal_dispense_qty(String total_dispense_qty) {
        this.total_dispense_qty = total_dispense_qty;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDispense_latitude() {
        return dispense_latitude;
    }

    public void setDispense_latitude(String dispense_latitude) {
        this.dispense_latitude = dispense_latitude;
    }

    public String getDispense_longitude() {
        return dispense_longitude;
    }

    public void setDispense_longitude(String dispense_longitude) {
        this.dispense_longitude = dispense_longitude;
    }

    public Order(boolean isSelected, String order_id, String asset_wise,String lead_number, String location_id, String staus, String slotname, String orderdate, String quantity, String login_id, boolean asset_flag, String asset_name, String created_datatime, String location_name, String address, String latitude, String longitude, String order_contact_person_name, String contact_person_phone, String fname, String transaction_id, String offline_otp, String performaId, String branchID, String orderType, String fuel, String slotId, String delivered_data, String ticket_id, String customer_name, String total_dispense_qty, String unit_price, String total_amount, String dispense_latitude, String dispense_longitude) {
        this.isSelected = isSelected;
        this.order_id = order_id;
        this.lead_number = lead_number;
        this.location_id = location_id;
        this.staus = staus;
        this.slotname = slotname;
        this.orderdate = orderdate;
        this.quantity = quantity;
        this.login_id = login_id;
        this.asset_flag = asset_flag;
        this.asset_name = asset_name;
        this.created_datatime = created_datatime;
        this.location_name = location_name;
        this.asset_wise = asset_wise;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.order_contact_person_name = order_contact_person_name;
        this.contact_person_phone = contact_person_phone;
        this.fname = fname;
        this.transaction_id = transaction_id;
        this.offline_otp = offline_otp;
        this.performaId = performaId;
        this.branchID = branchID;
        this.orderType = orderType;
        this.fuel = fuel;
        this.slotId = slotId;
        this.delivered_data = delivered_data;
        this.ticket_id = ticket_id;
        this.customer_name = customer_name;
        this.total_dispense_qty = total_dispense_qty;
        this.unit_price = unit_price;
        this.total_amount = total_amount;
        this.dispense_latitude = dispense_latitude;
        this.dispense_longitude = dispense_longitude;
    }

    public String getOffline_otp() {
        return offline_otp;
    }

    public Order(boolean isSelected, String order_id, String lead_number,String asset_wise, String location_id, String staus, String slotname, String orderdate, String quantity, String login_id, boolean asset_flag, String asset_name, String created_datatime, String location_name, String address, String latitude, String longitude, String order_contact_person_name, String contact_person_phone, String fname, String transaction_id, String offline_otp, String performaId, String branchID, String orderType, String fuel, String slotId, String delivered_data, String ticket_id, String customer_name) {
        this.isSelected = isSelected;
        this.order_id = order_id;
        this.lead_number = lead_number;
        this.location_id = location_id;
        this.staus = staus;
        this.slotname = slotname;
        this.orderdate = orderdate;
        this.quantity = quantity;
        this.login_id = login_id;
        this.asset_flag = asset_flag;
        this.asset_name = asset_name;
        this.created_datatime = created_datatime;
        this.location_name = location_name;
        this.asset_wise = asset_wise;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.order_contact_person_name = order_contact_person_name;
        this.contact_person_phone = contact_person_phone;
        this.fname = fname;
        this.transaction_id = transaction_id;
        this.offline_otp = offline_otp;
        this.performaId = performaId;
        this.branchID = branchID;
        this.orderType = orderType;
        this.fuel = fuel;
        this.slotId = slotId;
        this.delivered_data = delivered_data;
        this.ticket_id = ticket_id;
        this.customer_name = customer_name;
    }

    public void setOffline_otp(String offline_otp) {
        this.offline_otp = offline_otp;
    }

    public Order(String transaction_id, String order_id, String location_name, String asset_wise,String address, String order_contact_person_name,
                 String contact_person_phone, String quantity, String created_datatime, String slotname, String orderdate,
                 String staus, String latitude, String longitude, String slotId, String delivereddata, String lead_number, String ticket_id, String customer_name) {
        this.order_id = order_id;
        this.quantity = quantity;
        this.created_datatime = created_datatime;
        this.location_name = location_name;
        this.asset_wise = asset_wise;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.order_contact_person_name = order_contact_person_name;
        this.contact_person_phone = contact_person_phone;
        this.staus = staus;
        this.transaction_id = transaction_id;
        this.slotId = slotId;
        this.slotname = slotname;
        this.orderdate = orderdate;
        this.delivered_data = delivereddata;
        this.lead_number=lead_number;
        this.ticket_id=ticket_id;
        this.customer_name=customer_name;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getSlotname() {
        return slotname;
    }

    public void setSlotname(String slotname) {
        this.slotname = slotname;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getDelivered_data() {
        return delivered_data;
    }

    public boolean isAsset_flag() {
        return asset_flag;
    }

    public void setAsset_flag(boolean asset_flag) {
        this.asset_flag = asset_flag;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public void setDelivered_data(String delivered_data) {
        this.delivered_data = delivered_data;
    }
    public String getOrder_id() {
        return order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getCreated_datatime() {
        return created_datatime;
    }

    public void setCreated_datatime(String created_datatime) {
        this.created_datatime = created_datatime;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getAsset_wise() {
        return asset_wise;
    }

    public void setAsset_wise(String asset_wise) {
        this.asset_wise = asset_wise;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOrder_contact_person_name() {
        return order_contact_person_name;
    }

    public void setOrder_contact_person_name(String order_contact_person_name) {
        this.order_contact_person_name = order_contact_person_name;
    }

    public String getContact_person_phone() {
        return contact_person_phone;
    }

    public void setContact_person_phone(String contact_person_phone) {
        this.contact_person_phone = contact_person_phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public boolean getselected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPerformaId() {
        return performaId;
    }

    public void setPerformaId(String performaId) {
        this.performaId = performaId;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getLead_number() {
        return lead_number;
    }

    public void setLead_number(String lead_number) {
        this.lead_number = lead_number;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.order_id);
        dest.writeString(this.lead_number);
        dest.writeString(this.location_id);
        dest.writeString(this.staus);
        dest.writeString(this.quantity);
        dest.writeString(this.login_id);
        dest.writeString(this.created_datatime);
        dest.writeString(this.location_name);
        dest.writeString(this.asset_wise);
        dest.writeString(this.address);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.order_contact_person_name);
        dest.writeString(this.contact_person_phone);
        dest.writeString(this.fname);
        dest.writeString(this.transaction_id);
        dest.writeString(this.performaId);
        dest.writeString(this.branchID);
        dest.writeString(this.slotId);
        dest.writeString(this.slotname);
        dest.writeString(this.orderdate);
        dest.writeString(this.delivered_data);
        dest.writeString(this.ticket_id);
        dest.writeString(this.customer_name);
            }

    protected Order(Parcel in) {
        this.isSelected = in.readByte() != 0;
        this.order_id = in.readString();
        this.lead_number = in.readString();
        this.location_id = in.readString();
        this.staus = in.readString();
        this.quantity = in.readString();
        this.login_id = in.readString();
        this.created_datatime = in.readString();
        this.location_name = in.readString();
        this.asset_wise = in.readString();
        this.address = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.order_contact_person_name = in.readString();
        this.contact_person_phone = in.readString();
        this.fname = in.readString();
        this.transaction_id = in.readString();
        this.performaId = in.readString();
        this.branchID = in.readString();
        this.slotId = in.readString();
        this.slotname = in.readString();
        this.orderdate = in.readString();
        this.delivered_data = in.readString();
        this.ticket_id = in.readString();
        this.customer_name = in.readString();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }

    };

    @Override
    public String toString() {
        return "Order{" +
                "isSelected=" + isSelected +
                ", order_id='" + order_id + '\'' +
                ", lead_number='" + lead_number + '\'' +
                ", location_id='" + location_id + '\'' +
                ", asset_wise='" + asset_wise + '\'' +
                ", staus='" + staus + '\'' +
                ", slotname='" + slotname + '\'' +
                ", orderdate='" + orderdate + '\'' +
                ", quantity='" + quantity + '\'' +
                ", registration_no='" + registration_no + '\'' +
                ", login_id='" + login_id + '\'' +
                ", asset_flag=" + asset_flag +
                ", asset_name='" + asset_name + '\'' +
                ", created_datatime='" + created_datatime + '\'' +
                ", location_name='" + location_name + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", order_contact_person_name='" + order_contact_person_name + '\'' +
                ", contact_person_phone='" + contact_person_phone + '\'' +
                ", fname='" + fname + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", offline_otp='" + offline_otp + '\'' +
                ", performaId='" + performaId + '\'' +
                ", branchID='" + branchID + '\'' +
                ", orderType='" + orderType + '\'' +
                ", fuel='" + fuel + '\'' +
                ", slotId='" + slotId + '\'' +
                ", delivered_data='" + delivered_data + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", total_dispense_qty='" + total_dispense_qty + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", dispense_latitude='" + dispense_latitude + '\'' +
                ", dispense_longitude='" + dispense_longitude + '\'' +
                ", gst_percentage='" + gst_percentage + '\'' +
                ", asset_other_reading_2='" + asset_other_reading_2 + '\'' +
                ", location_reading_1='" + location_reading_1 + '\'' +
                '}';
    }
}
