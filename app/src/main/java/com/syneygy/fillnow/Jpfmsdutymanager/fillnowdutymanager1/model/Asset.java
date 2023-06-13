package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Asset implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("asset_name")
    @Expose
    private String assetName;
    @SerializedName("registration_no")
    @Expose
    private String registration_no;
    @SerializedName("asset_type")
    @Expose
    private String assetType;
    @SerializedName("asset_make")
    @Expose
    private String assetMake;
    @SerializedName("asset_capacity")
    @Expose
    private String assetCapacity;
    @SerializedName("linked_asset")
    @Expose
    private String linked_asset;//linked asset equals
    @SerializedName("assets_fuel_tank_capacity")
    @Expose
    private String assetsFuelTankCapacity;
    @SerializedName("asset_tank_shape")
    @Expose
    private String assetTankShape;
    @SerializedName("assets_length")
    @Expose
    private String assetsLength;
    @SerializedName("assets_height")
    @Expose
    private String assetsHeight;
    @SerializedName("assets_breadth")
    @Expose
    private String assetsBreadth;
    @SerializedName("assets_dia")
    @Expose
    private String assetsDia;

    @SerializedName("assets_rfid")
    @Expose
    private String assetsRfid;

    @SerializedName("assets_tagid")
    @Expose
    private String assetsTagid;

    @SerializedName("assets_bypassrfid")
    @Expose
    private String assetsBypassRfid;

    @SerializedName("assets_elock")
    @Expose
    private String assets_elock;

    @SerializedName("data_volume")
    @Expose
    private String data_volume;

    @SerializedName("asset_order_qty")
    @Expose
    private String assetOrderQty;
    @SerializedName("asset_dispense_qty")
    @Expose
    private String assetDispenseQty;

    @SerializedName("e_lock_id")
    private String elock_serial;


    public String getData_volume() {
        return data_volume;
    }

    public void setData_volume(String data_volume) {
        this.data_volume = data_volume;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public String getAssetCapacity() {
        return assetCapacity;
    }

    public void setAssetCapacity(String assetCapacity) {
        this.assetCapacity = assetCapacity;
    }

    public String getAssetsFuelTankCapacity() {
        return assetsFuelTankCapacity;
    }

    public void setAssetsFuelTankCapacity(String assetsFuelTankCapacity) {
        this.assetsFuelTankCapacity = assetsFuelTankCapacity;
    }

    public String getAssetTankShape() {
        return assetTankShape;
    }

    public void setAssetTankShape(String assetTankShape) {
        this.assetTankShape = assetTankShape;
    }

    public String getAssetsLength() {
        return assetsLength;
    }

    public void setAssetsLength(String assetsLength) {
        this.assetsLength = assetsLength;
    }

    public String getAssetsHeight() {
        return assetsHeight;
    }

    public void setAssetsHeight(String assetsHeight) {
        this.assetsHeight = assetsHeight;
    }

    public String getAssetsBreadth() {
        return assetsBreadth;
    }

    public void setAssetsBreadth(String assetsBreadth) {
        this.assetsBreadth = assetsBreadth;
    }

    public String getAssetsDia() {
        return assetsDia;
    }

    public void setAssetsDia(String assetsDia) {
        this.assetsDia = assetsDia;
    }

    public String getAssetsRfid() {
        return assetsRfid;
    }

    public void setAssetsRfid(String assetsRfid) {
        this.assetsRfid = assetsRfid;
    }

    public String getAssetsTagid() {
        return assetsTagid;
    }

    public void setAssetsTagid(String assetsTagid) {
        this.assetsTagid = assetsTagid;
    }

    public String getLinked_asset() {
        return linked_asset;
    }

    public void setLinked_asset(String linked_asset) {
        this.linked_asset = linked_asset;
    }

    public String getAssetsBypassRfid() {
        return assetsBypassRfid;
    }

    public void setAssetsBypassRfid(String assetsBypassRfid) {
        this.assetsBypassRfid = assetsBypassRfid;
    }

    public String getAssets_elock() {
        return assets_elock;
    }

    public void setAssets_elock(String assets_elock) {
        this.assets_elock = assets_elock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getElock_serial() {
        return elock_serial;
    }

    public void setElock_serial(String elock_serial) {
        this.elock_serial = elock_serial;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.locationId);
        dest.writeString(this.creationDate);
        dest.writeString(this.assetName);
        dest.writeString(this.assetType);
        dest.writeString(this.assetMake);
        dest.writeString(this.assetCapacity);
        dest.writeString(this.assetsFuelTankCapacity);
        dest.writeString(this.assetTankShape);
        dest.writeString(this.assetsLength);
        dest.writeString(this.assetsHeight);
        dest.writeString(this.assetsBreadth);
        dest.writeString(this.assetsDia);
        dest.writeString(this.assetsRfid);
        dest.writeString(this.assetsTagid);
        dest.writeString(this.assetsBypassRfid);
        dest.writeString(this.assets_elock);
        dest.writeString(this.assetDispenseQty);
        dest.writeString(this.assetOrderQty);
        dest.writeString(this.elock_serial);
    }

    public String getAssetOrderQty() {
        return assetOrderQty;
    }

    public void setAssetOrderQty(String assetOrderQty) {
        this.assetOrderQty = assetOrderQty;
    }

    public String getAssetDispenseQty() {
        return assetDispenseQty;
    }

    public void setAssetDispenseQty(String assetDispenseQty) {
        this.assetDispenseQty = assetDispenseQty;
    }

    public Asset() {
    }

    protected Asset(Parcel in) {
        this.id = in.readString();
        this.locationId = in.readString();
        this.creationDate = in.readString();
        this.assetName = in.readString();
        this.assetType = in.readString();
        this.assetMake = in.readString();
        this.assetCapacity = in.readString();
        this.assetsFuelTankCapacity = in.readString();
        this.assetTankShape = in.readString();
        this.assetsLength = in.readString();
        this.assetsHeight = in.readString();
        this.assetsBreadth = in.readString();
        this.assetsDia = in.readString();
        this.assetsRfid = in.readString();
        this.assetsTagid = in.readString();
        this.assetsBypassRfid = in.readString();
        this.assets_elock = in.readString();
        this.assetDispenseQty=in.readString();
        this.assetOrderQty=in.readString();
        this.elock_serial=in.readString();
    }

    public static final Parcelable.Creator<Asset> CREATOR = new Parcelable.Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel source) {
            return new Asset(source);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };

    @Override
    public String toString() {
        return "Asset{" +
                "id='" + id + '\'' +
                ", locationId='" + locationId + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", assetName='" + assetName + '\'' +
                ", registration_no='" + registration_no + '\'' +
                ", assetType='" + assetType + '\'' +
                ", assetMake='" + assetMake + '\'' +
                ", assetCapacity='" + assetCapacity + '\'' +
                ", linked_asset='" + linked_asset + '\'' +
                ", assetsFuelTankCapacity='" + assetsFuelTankCapacity + '\'' +
                ", assetTankShape='" + assetTankShape + '\'' +
                ", assetsLength='" + assetsLength + '\'' +
                ", assetsHeight='" + assetsHeight + '\'' +
                ", assetsBreadth='" + assetsBreadth + '\'' +
                ", assetsDia='" + assetsDia + '\'' +
                ", assetsRfid='" + assetsRfid + '\'' +
                ", assetsTagid='" + assetsTagid + '\'' +
                ", assetsBypassRfid='" + assetsBypassRfid + '\'' +
                ", assets_elock='" + assets_elock + '\'' +
                ", data_volume='" + data_volume + '\'' +
                ", assetOrderQty='" + assetOrderQty + '\'' +
                ", assetDispenseQty='" + assetDispenseQty + '\'' +
                ", elock_serial='" + elock_serial + '\'' +
                '}';
    }
}
