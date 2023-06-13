package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

public class orderSpinnermodel {
    private String order_id, loc, address, contact_name, contact_person_phone, quantity, time, status, transaction_id, latitude, logitude, login_id, performa_id, branchId;


    public void setPerforma_id(String performa_id) {
        this.performa_id = performa_id;
    }

    public String getPerforma_id() {
        return performa_id;
    }

    public orderSpinnermodel() {

    }

    public orderSpinnermodel(String order_id, String loc, String address, String contact_name, String contact_person_phone, String quantity, String time, String status, String transaction_id) {
        this.order_id = order_id;
        this.loc = loc;
        this.address = address;
        this.contact_name = contact_name;
        this.contact_person_phone = contact_person_phone;
        this.quantity = quantity;
        this.time = time;
        this.status = status;
        this.transaction_id = transaction_id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getAddress() {
        return address;
    }

    public String getTransaction_id() {
        return transaction_id;
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

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getContact_person_phone() {
        return contact_person_phone;
    }

    public void setContact_person_phone(String contact_person_phone) {
        this.contact_person_phone = contact_person_phone;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
