package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FranchiseDatum implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("login_mast_id")
    @Expose
    public String loginMastId;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("email_id")
    @Expose
    public String emailId;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("gst_no")
    @Expose
    public String gstNo;
    @SerializedName("contact_person")
    @Expose
    public String contactPerson;
    @SerializedName("contact_num")
    @Expose
    public String contactNum;
    @SerializedName("omc")
    @Expose
    public String omc;
    @SerializedName("franchisee_code_omc")
    @Expose
    public String franchiseeCodeOmc;
    @SerializedName("omc_id")
    @Expose
    public String omcId;
    @SerializedName("footer_message")
    @Expose
    public String footerMessage;
    @SerializedName("bank_name")
    @Expose
    public String bankName;
    @SerializedName("branch_name")
    @Expose
    public String branchName;
    @SerializedName("bank_add")
    @Expose
    public String bankAdd;
    @SerializedName("account_type")
    @Expose
    public String accountType;
    @SerializedName("account_number")
    @Expose
    public String accountNumber;
    @SerializedName("IFSC_code")
    @Expose
    public String iFSCCode;
    @SerializedName("Product_id")
    @Expose
    public String productId;
    @SerializedName("Sl_no")
    @Expose
    public String slNo;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("franchise_serial_no")
    @Expose
    public String franchiseSerialNo;
    @SerializedName("gst_userid")
    @Expose
    public String gstUserid;
    @SerializedName("gst_password")
    @Expose
    public String gstPassword;
    @SerializedName("ewaybill_userid")
    @Expose
    public String ewaybillUserid;
    @SerializedName("ewaybill_password")
    @Expose
    public String ewaybillPassword;
    @SerializedName("signature_image")
    @Expose
    public String signatureImage;

    @SerializedName("franchise_bypass")
    @Expose
    public String franchise_bypass="";

    protected FranchiseDatum(Parcel in) {
        id = in.readString();
        name = in.readString();
        loginMastId = in.readString();
        address = in.readString();
        emailId = in.readString();
        password = in.readString();
        state = in.readString();
        city = in.readString();
        pincode = in.readString();
        lat = in.readString();
        longitude = in.readString();
        gstNo = in.readString();
        contactPerson = in.readString();
        contactNum = in.readString();
        omc = in.readString();
        franchiseeCodeOmc = in.readString();
        omcId = in.readString();
        footerMessage = in.readString();
        bankName = in.readString();
        branchName = in.readString();
        bankAdd = in.readString();
        accountType = in.readString();
        accountNumber = in.readString();
        iFSCCode = in.readString();
        productId = in.readString();
        slNo = in.readString();
        status = in.readString();
        createdDate = in.readString();
        franchiseSerialNo = in.readString();
        gstUserid = in.readString();
        gstPassword = in.readString();
        ewaybillUserid = in.readString();
        ewaybillPassword = in.readString();
        signatureImage = in.readString();
        franchise_bypass=in.readString();
    }

    public static final Creator<FranchiseDatum> CREATOR = new Creator<FranchiseDatum>() {
        @Override
        public FranchiseDatum createFromParcel(Parcel in) {
            return new FranchiseDatum(in);
        }

        @Override
        public FranchiseDatum[] newArray(int size) {
            return new FranchiseDatum[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(loginMastId);
        dest.writeString(address);
        dest.writeString(emailId);
        dest.writeString(password);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(pincode);
        dest.writeString(lat);
        dest.writeString(longitude);
        dest.writeString(gstNo);
        dest.writeString(contactPerson);
        dest.writeString(contactNum);
        dest.writeString(omc);
        dest.writeString(franchiseeCodeOmc);
        dest.writeString(omcId);
        dest.writeString(footerMessage);
        dest.writeString(bankName);
        dest.writeString(branchName);
        dest.writeString(bankAdd);
        dest.writeString(accountType);
        dest.writeString(accountNumber);
        dest.writeString(iFSCCode);
        dest.writeString(productId);
        dest.writeString(slNo);
        dest.writeString(status);
        dest.writeString(createdDate);
        dest.writeString(franchiseSerialNo);
        dest.writeString(gstUserid);
        dest.writeString(gstPassword);
        dest.writeString(ewaybillUserid);
        dest.writeString(ewaybillPassword);
        dest.writeString(signatureImage);
        dest.writeString(franchise_bypass);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginMastId() {
        return loginMastId;
    }

    public void setLoginMastId(String loginMastId) {
        this.loginMastId = loginMastId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getOmc() {
        return omc;
    }

    public void setOmc(String omc) {
        this.omc = omc;
    }

    public String getFranchiseeCodeOmc() {
        return franchiseeCodeOmc;
    }

    public void setFranchiseeCodeOmc(String franchiseeCodeOmc) {
        this.franchiseeCodeOmc = franchiseeCodeOmc;
    }

    public String getOmcId() {
        return omcId;
    }

    public void setOmcId(String omcId) {
        this.omcId = omcId;
    }

    public String getFooterMessage() {
        return footerMessage;
    }

    public void setFooterMessage(String footerMessage) {
        this.footerMessage = footerMessage;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFranchiseSerialNo() {
        return franchiseSerialNo;
    }

    public void setFranchiseSerialNo(String franchiseSerialNo) {
        this.franchiseSerialNo = franchiseSerialNo;
    }

    public String getGstUserid() {
        return gstUserid;
    }

    public void setGstUserid(String gstUserid) {
        this.gstUserid = gstUserid;
    }

    public String getGstPassword() {
        return gstPassword;
    }

    public void setGstPassword(String gstPassword) {
        this.gstPassword = gstPassword;
    }

    public String getEwaybillUserid() {
        return ewaybillUserid;
    }

    public void setEwaybillUserid(String ewaybillUserid) {
        this.ewaybillUserid = ewaybillUserid;
    }

    public String getEwaybillPassword() {
        return ewaybillPassword;
    }

    public void setEwaybillPassword(String ewaybillPassword) { this.ewaybillPassword = ewaybillPassword; }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getFranchise_bypass() {
        return franchise_bypass;
    }

    public void setFranchise_bypass(String franchise_bypass) {
        this.franchise_bypass = franchise_bypass;
    }
}