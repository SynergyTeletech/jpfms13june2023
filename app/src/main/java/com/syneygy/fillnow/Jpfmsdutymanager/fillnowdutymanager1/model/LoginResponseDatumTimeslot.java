package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseDatumTimeslot {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("no_of_delivery")
    @Expose
    private String noOfDelivery;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("display_title")
    @Expose
    private String displayTitle;
    @SerializedName("is_happy_hours")
    @Expose
    private String isHappyHours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNoOfDelivery() {
        return noOfDelivery;
    }

    public void setNoOfDelivery(String noOfDelivery) {
        this.noOfDelivery = noOfDelivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getIsHappyHours() {
        return isHappyHours;
    }

    public void setIsHappyHours(String isHappyHours) {
        this.isHappyHours = isHappyHours;
    }

}
