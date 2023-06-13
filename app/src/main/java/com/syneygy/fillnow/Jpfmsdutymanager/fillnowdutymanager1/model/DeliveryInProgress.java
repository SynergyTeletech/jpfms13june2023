package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DeliveryInProgress implements Parcelable {

    @SerializedName("succ")
    @Expose
    private Boolean succ;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("public_msg")
    @Expose
    private String publicMsg;
    @SerializedName("_err_codes")
    @Expose
    private List<Object> errCodes = null;
    @SerializedName("Progress")
    @Expose
    private Progress progress;
    @SerializedName("assets")
    @Expose
    private ArrayList<Asset> assets = null;
    @SerializedName("order")
    @Expose
    private List<OrderDetail> order = null;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DeliveryInProgress{" +
                "succ=" + succ +
                ", type='" + type + '\'' +
                ", publicMsg='" + publicMsg + '\'' +
                ", errCodes=" + errCodes +
                ", progress=" + progress +
                ", assets=" + assets +
                ", order=" + order +
                '}';
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublicMsg() {
        return publicMsg;
    }

    public void setPublicMsg(String publicMsg) {
        this.publicMsg = publicMsg;
    }

    public List<Object> getErrCodes() {
        return errCodes;
    }

    public void setErrCodes(List<Object> errCodes) {
        this.errCodes = errCodes;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<Asset> assets) {
        this.assets = assets;
    }

    public List<OrderDetail> getOrder() {
        return order;
    }

    public void setOrder(List<OrderDetail> order) {
        this.order = order;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.succ);
        dest.writeString(this.type);
        dest.writeString(this.publicMsg);
        dest.writeList(this.errCodes);
        dest.writeParcelable(this.progress, flags);
        dest.writeTypedList(this.assets);
        dest.writeList(this.order);
    }

    public DeliveryInProgress() {
    }

    public DeliveryInProgress(Boolean succ, String type, String publicMsg, List<Object> errCodes, Progress progress, ArrayList<Asset> assets, List<OrderDetail> order) {
        this.succ = succ;
        this.type = type;
        this.publicMsg = publicMsg;
        this.errCodes = errCodes;
        this.progress = progress;
        this.assets = assets;
        this.order = order;
    }

    protected DeliveryInProgress(Parcel in) {
        this.succ = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.type = in.readString();
        this.publicMsg = in.readString();
        this.errCodes = new ArrayList<Object>();
        in.readList(this.errCodes, Object.class.getClassLoader());
        this.progress = in.readParcelable(Progress.class.getClassLoader());
        this.assets = in.createTypedArrayList(Asset.CREATOR);
        this.order = new ArrayList<OrderDetail>();
        in.readList(this.order, OrderDetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<DeliveryInProgress> CREATOR = new Parcelable.Creator<DeliveryInProgress>() {
        @Override
        public DeliveryInProgress createFromParcel(Parcel source) {
            return new DeliveryInProgress(source);
        }

        @Override
        public DeliveryInProgress[] newArray(int size) {
            return new DeliveryInProgress[size];
        }
    };

}


