package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCurrentAssetLatlong {


        @SerializedName("succ")
        @Expose
        private Boolean succ;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("asset_latitude")
        @Expose
        private String asset_latitude;
        @SerializedName("asset_longitude")
        @Expose
        private String asset_longitude;
        @SerializedName("vehicle_latitude")
        @Expose
        private String vehicle_latitude;
        @SerializedName("vehicle_longitude")
        @Expose
        private String vehicle_longitude;
        @SerializedName("public_msg")
        @Expose
        private String publicMsg;
        @SerializedName("_err_codes")
        @Expose
        private List<String> errCodes = null;

        public Boolean getSucc() {
            return succ;
        }

        public void setSucc(Boolean succ) {
            this.succ = succ;
        }

        public String getType() {
            return type;
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

        public List<String> getErrCodes() {
            return errCodes;
        }

        public void setErrCodes(List<String> errCodes) {
            this.errCodes = errCodes;
        }

    public String getAsset_latitude() {
        return asset_latitude;
    }

    public void setAsset_latitude(String asset_latitude) {
        this.asset_latitude = asset_latitude;
    }

    public String getAsset_longitude() {
        return asset_longitude;
    }

    public void setAsset_longitude(String asset_longitude) {
        this.asset_longitude = asset_longitude;
    }

    public String getVehicle_latitude() {
        return vehicle_latitude;
    }

    public void setVehicle_latitude(String vehicle_latitude) {
        this.vehicle_latitude = vehicle_latitude;
    }

    public String getVehicle_longitude() {
        return vehicle_longitude;
    }

    public void setVehicle_longitude(String vehicle_longitude) {
        this.vehicle_longitude = vehicle_longitude;
    }
}
