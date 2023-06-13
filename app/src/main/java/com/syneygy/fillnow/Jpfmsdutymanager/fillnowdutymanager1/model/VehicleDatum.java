package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VehicleDatum implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("random_code")
    @Expose
    private String randomCode;
    @SerializedName("tank_shape")
    @Expose
    private String tankShape;
    @SerializedName("no_of_nozzle")
    @Expose
    private String no_of_nozzle;
    @SerializedName("fitness_expriry")
    @Expose
    private String fitnessExpriry;
    @SerializedName("permit_expiry")
    @Expose
    private String permitExpiry;
    @SerializedName("insurance_expiry")
    @Expose
    private String insuranceExpiry;
    @SerializedName("pollution_expiry")
    @Expose
    private String pollutionExpiry;
    @SerializedName("geofencing")
    @Expose
    private String geofencing;
    @SerializedName("franchisee_id")
    @Expose
    private String franchiseeId;
    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("no_of_tanks")
    @Expose
    private Object noOfTanks;
    @SerializedName("no_of_manhole_locks")
    @Expose
    private String noOfManholeLocks;
    @SerializedName("no_of_valves")
    @Expose
    private String noOfValves;
    @SerializedName("engine_nb")
    @Expose
    private String engineNb;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("make_and_model")
    @Expose
    private String makeAndModel;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("reg_no")
    @Expose
    private String regNo;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("engine_no")
    @Expose
    private String engineNo;
    @SerializedName("chasis_no")
    @Expose
    private String chasisNo;
    @SerializedName("status_of_permit")
    @Expose
    private String statusOfPermit;
    @SerializedName("max_deliverable_distance")
    @Expose
    private String maxDeliverableDistance;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("total_no_of_deliveries")
    @Expose
    private String totalNoOfDeliveries;
    @SerializedName("status_of_goods_tax")
    @Expose
    private String statusOfGoodsTax;
    @SerializedName("status_of_puc")
    @Expose
    private String statusOfPuc;
    @SerializedName("status_of_fitness")
    @Expose
    private String statusOfFitness;
    @SerializedName("status_of_insurance")
    @Expose
    private String statusOfInsurance;
    @SerializedName("validity_of_stamping")
    @Expose
    private String validityOfStamping;
    @SerializedName("type_of_permit")
    @Expose
    private String typeOfPermit;
    @SerializedName("no_of_atg")
    @Expose
    private String noOfAtg;
    @SerializedName("make_of_atg")
    @Expose
    private String makeOfAtg;
    @SerializedName("make_by_fcc")
    @Expose
    private String makeOfFcc;
    @SerializedName("printer")
    @Expose
    private String printer;
    @SerializedName("make_of_dispenser")
    @Expose
    private String makeOfDispenser;
    @SerializedName("gps_mismatch_range")
    @Expose
    private String gpsMismatchRange;
    @SerializedName("make_of_relay_board")
    @Expose
    private String makeOfRelayBoard;
    @SerializedName("wireless_status")
    @Expose
    private String wirelessStatus;
    @SerializedName("no_of_terminals")
    @Expose
    private String noOfTerminals;
    @SerializedName("msd")
    @Expose
    private String msd;
    @SerializedName("omc_id")
    @Expose
    private String omcId;
    @SerializedName("sl_no")
    @Expose
    private String slNo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("balance_fuel")
    @Expose
    private String balanceFuel;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("slot_check")
    @Expose
    private String slotCheck;
    @SerializedName("compartment1")
    @Expose
    private String compartment1;
    @SerializedName("compartment2")
    @Expose
    private String compartment2;
    @SerializedName("compartment3")
    @Expose
    private String compartment3;
    @SerializedName("compartment4")
    @Expose
    private String compartment4;
    @SerializedName("compartment5")
    @Expose
    private String compartment5;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("atg_serial_no")
    @Expose
    private String atgSerialNo;
    @SerializedName("atg_dip_chart")
    @Expose
    private String atgDipChart;
    @SerializedName("atg_dip_chart_file")
    @Expose
    private String atgDipChartFile;
    @SerializedName("vehicle_serial_no")
    @Expose
    private String vehicleSerialNo;

    @SerializedName("valve_bypass")
    @Expose
    private Boolean valve_bypass;

    @SerializedName("lock_bypass")
    @Expose
    private Boolean lock_bypass;

    @SerializedName("atg_bypass")
    @Expose
    private Boolean atg_bypass;

    @SerializedName("franchise_bypass")
    @Expose
    private Boolean franchise_bypass;

    @SerializedName("compartment_info")
    @Expose
    private ArrayList<CompartmentInfo> compartmentInfo = null;

    public ArrayList<CompartmentInfo> getCompartmentInfo() {
        return compartmentInfo;
    }

    public void setCompartmentInfo(ArrayList<CompartmentInfo> compartmentInfo) {
        this.compartmentInfo = compartmentInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getTankShape() {
        return tankShape;
    }

    public void setTankShape(String tankShape) {
        this.tankShape = tankShape;
    }

    public String getNo_of_nozzle() {
        return no_of_nozzle;
    }

    public void setNo_of_nozzle(String no_of_nozzle) {
        this.no_of_nozzle = no_of_nozzle;
    }

    public String getFitnessExpriry() {
        return fitnessExpriry;
    }

    public void setFitnessExpriry(String fitnessExpriry) {
        this.fitnessExpriry = fitnessExpriry;
    }

    public String getPermitExpiry() {
        return permitExpiry;
    }

    public void setPermitExpiry(String permitExpiry) {
        this.permitExpiry = permitExpiry;
    }

    public String getInsuranceExpiry() {
        return insuranceExpiry;
    }

    public void setInsuranceExpiry(String insuranceExpiry) {
        this.insuranceExpiry = insuranceExpiry;
    }

    public String getMakeOfFcc() {
        return makeOfFcc;
    }

    public void setMakeOfFcc(String makeOfFcc) {
        this.makeOfFcc = makeOfFcc;
    }

    public String getPollutionExpiry() {
        return pollutionExpiry;
    }

    public void setPollutionExpiry(String pollutionExpiry) {
        this.pollutionExpiry = pollutionExpiry;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getNoOfTanks() {
        return String.valueOf(noOfTanks);
    }

    public void setNoOfTanks(String noOfTanks) {
        this.noOfTanks = noOfTanks;
    }

    public String getNoOfManholeLocks() {
        return noOfManholeLocks;
    }

    public void setNoOfManholeLocks(String noOfManholeLocks) {
        this.noOfManholeLocks = noOfManholeLocks;
    }

    public String getNoOfValves() {
        return noOfValves;
    }

    public void setNoOfValves(String noOfValves) {
        this.noOfValves = noOfValves;
    }

    public String getEngineNb() {
        return engineNb;
    }

    public void setEngineNb(String engineNb) {
        this.engineNb = engineNb;
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

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public void setMakeAndModel(String makeAndModel) {
        this.makeAndModel = makeAndModel;
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

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChasisNo() {
        return chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getStatusOfPermit() {
        return statusOfPermit;
    }

    public void setStatusOfPermit(String statusOfPermit) {
        this.statusOfPermit = statusOfPermit;
    }

    public String getMaxDeliverableDistance() {
        return maxDeliverableDistance;
    }

    public void setMaxDeliverableDistance(String maxDeliverableDistance) {
        this.maxDeliverableDistance = maxDeliverableDistance;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTotalNoOfDeliveries() {
        return totalNoOfDeliveries;
    }

    public void setTotalNoOfDeliveries(String totalNoOfDeliveries) {
        this.totalNoOfDeliveries = totalNoOfDeliveries;
    }

    public String getStatusOfGoodsTax() {
        return statusOfGoodsTax;
    }

    public void setStatusOfGoodsTax(String statusOfGoodsTax) {
        this.statusOfGoodsTax = statusOfGoodsTax;
    }

    public String getStatusOfPuc() {
        return statusOfPuc;
    }

    public void setStatusOfPuc(String statusOfPuc) {
        this.statusOfPuc = statusOfPuc;
    }

    public String getStatusOfFitness() {
        return statusOfFitness;
    }

    public void setStatusOfFitness(String statusOfFitness) {
        this.statusOfFitness = statusOfFitness;
    }

    public String getStatusOfInsurance() {
        return statusOfInsurance;
    }

    public void setStatusOfInsurance(String statusOfInsurance) {
        this.statusOfInsurance = statusOfInsurance;
    }

    public String getValidityOfStamping() {
        return validityOfStamping;
    }

    public void setValidityOfStamping(String validityOfStamping) {
        this.validityOfStamping = validityOfStamping;
    }

    public String getTypeOfPermit() {
        return typeOfPermit;
    }

    public void setTypeOfPermit(String typeOfPermit) {
        this.typeOfPermit = typeOfPermit;
    }

    public String getNoOfAtg() {
        return noOfAtg;
    }

    public void setNoOfAtg(String noOfAtg) {
        this.noOfAtg = noOfAtg;
    }

    public String getMakeOfAtg() {
        return makeOfAtg;
    }

    public void setMakeOfAtg(String makeOfAtg) {
        this.makeOfAtg = makeOfAtg;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getMakeOfDispenser() {
        return makeOfDispenser;
    }

    public void setMakeOfDispenser(String makeOfDispenser) {
        this.makeOfDispenser = makeOfDispenser;
    }

    public String getGpsMismatchRange() {
        return gpsMismatchRange;
    }

    public void setGpsMismatchRange(String gpsMismatchRange) {
        this.gpsMismatchRange = gpsMismatchRange;
    }

    public String getMakeOfRelayBoard() {
        return makeOfRelayBoard;
    }

    public void setMakeOfRelayBoard(String makeOfRelayBoard) {
        this.makeOfRelayBoard = makeOfRelayBoard;
    }

    public String getWirelessStatus() {
        return wirelessStatus;
    }

    public void setWirelessStatus(String wirelessStatus) {
        this.wirelessStatus = wirelessStatus;
    }

    public String getNoOfTerminals() {
        return noOfTerminals;
    }

    public void setNoOfTerminals(String noOfTerminals) {
        this.noOfTerminals = noOfTerminals;
    }

    public String getMsd() {
        return msd;
    }

    public void setMsd(String msd) {
        this.msd = msd;
    }

    public String getOmcId() {
        return omcId;
    }

    public void setOmcId(String omcId) {
        this.omcId = omcId;
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

    public Object getBalanceFuel() {
        return balanceFuel;
    }

    public void setBalanceFuel(String balanceFuel) {
        this.balanceFuel = balanceFuel;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getSlotCheck() {
        return slotCheck;
    }

    public void setSlotCheck(String slotCheck) {
        this.slotCheck = slotCheck;
    }

    public String getCompartment1() {
        return compartment1;
    }

    public void setCompartment1(String compartment1) {
        this.compartment1 = compartment1;
    }

    public String getCompartment2() {
        return compartment2;
    }

    public void setCompartment2(String compartment2) {
        this.compartment2 = compartment2;
    }

    public String getCompartment3() {
        return compartment3;
    }

    public void setCompartment3(String compartment3) {
        this.compartment3 = compartment3;
    }

    public String getCompartment4() {
        return compartment4;
    }

    public void setCompartment4(String compartment4) {
        this.compartment4 = compartment4;
    }

    public String getCompartment5() {
        return compartment5;
    }

    public void setCompartment5(String compartment5) {
        this.compartment5 = compartment5;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getAtgSerialNo() {
        return atgSerialNo;
    }

    public void setAtgSerialNo(String atgSerialNo) {
        this.atgSerialNo = atgSerialNo;
    }

    public String getAtgDipChart() {
        return atgDipChart;
    }

    public void setAtgDipChart(String atgDipChart) {
        this.atgDipChart = atgDipChart;
    }

    public String getAtgDipChartFile() {
        return atgDipChartFile;
    }

    public void setAtgDipChartFile(String atgDipChartFile) {
        this.atgDipChartFile = atgDipChartFile;
    }

    public String getVehicleSerialNo() {
        return vehicleSerialNo;
    }

    public void setVehicleSerialNo(String vehicleSerialNo) {
        this.vehicleSerialNo = vehicleSerialNo;
    }

    public VehicleDatum() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.randomCode);
        dest.writeString(this.tankShape);
        dest.writeString(this.fitnessExpriry);
        dest.writeString(this.permitExpiry);
        dest.writeString(this.insuranceExpiry);
        dest.writeString(this.pollutionExpiry);
        dest.writeString(this.franchiseeId);
        dest.writeString(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.other);
        dest.writeString(this.capacity);
        dest.writeString(String.valueOf(this.noOfTanks));
        dest.writeString(this.noOfManholeLocks);
        dest.writeString(this.noOfValves);
        dest.writeString(this.engineNb);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.makeAndModel);
        dest.writeString(this.state);
        dest.writeString(this.city);
        dest.writeString(this.regNo);
        dest.writeString(this.year);
        dest.writeString(this.engineNo);
        dest.writeString(this.chasisNo);
        dest.writeString(this.statusOfPermit);
        dest.writeString(this.maxDeliverableDistance);
        dest.writeString(this.pincode);
        dest.writeString(this.totalNoOfDeliveries);
        dest.writeString(this.statusOfGoodsTax);
        dest.writeString(this.statusOfPuc);
        dest.writeString(this.statusOfFitness);
        dest.writeString(this.statusOfInsurance);
        dest.writeString(this.validityOfStamping);
        dest.writeString(this.typeOfPermit);
        dest.writeString(this.noOfAtg);
        dest.writeString(this.makeOfAtg);
        dest.writeString(this.printer);
        dest.writeString(this.makeOfDispenser);
        dest.writeString(this.gpsMismatchRange);
        dest.writeString(this.makeOfRelayBoard);
        dest.writeString(this.wirelessStatus);
        dest.writeString(this.noOfTerminals);
        dest.writeString(this.msd);
        dest.writeString(this.omcId);
        dest.writeString(this.slNo);
        dest.writeString(this.status);
        dest.writeString(this.balanceFuel);
        dest.writeString(this.timeSlot);
        dest.writeString(this.slotCheck);
        dest.writeString(this.compartment1);
        dest.writeString(this.compartment2);
        dest.writeString(this.compartment3);
        dest.writeString(this.compartment4);
        dest.writeString(this.compartment5);
        dest.writeString(this.createdDate);
        dest.writeString(this.createdBy);
        dest.writeString(this.updatedDate);
        dest.writeString(this.updatedBy);
        dest.writeString(this.atgSerialNo);
        dest.writeString(this.atgDipChart);
        dest.writeString(this.atgDipChartFile);
        dest.writeString(this.vehicleSerialNo);
        dest.writeTypedList(this.compartmentInfo);
    }

    protected VehicleDatum(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.randomCode = in.readString();
        this.tankShape = in.readString();
        this.fitnessExpriry = in.readString();
        this.permitExpiry = in.readString();
        this.insuranceExpiry = in.readString();
        this.pollutionExpiry = in.readString();
        this.franchiseeId = in.readString();
        this.productId = in.readString();
        this.productName = in.readString();
        this.other = in.readString();
        this.capacity = in.readString();
        this.noOfTanks = in.readString();
        this.noOfManholeLocks = in.readString();
        this.noOfValves = in.readString();
        this.engineNb = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.makeAndModel = in.readString();
        this.state = in.readString();
        this.city = in.readString();
        this.regNo = in.readString();
        this.year = in.readString();
        this.engineNo = in.readString();
        this.chasisNo = in.readString();
        this.statusOfPermit = in.readString();
        this.maxDeliverableDistance = in.readString();
        this.pincode = in.readString();
        this.totalNoOfDeliveries = in.readString();
        this.statusOfGoodsTax = in.readString();
        this.statusOfPuc = in.readString();
        this.statusOfFitness = in.readString();
        this.statusOfInsurance = in.readString();
        this.validityOfStamping = in.readString();
        this.typeOfPermit = in.readString();
        this.noOfAtg = in.readString();
        this.makeOfAtg = in.readString();
        this.printer = in.readString();
        this.makeOfDispenser = in.readString();
        this.gpsMismatchRange = in.readString();
        this.makeOfRelayBoard = in.readString();
        this.wirelessStatus = in.readString();
        this.noOfTerminals = in.readString();
        this.msd = in.readString();
        this.omcId = in.readString();
        this.slNo = in.readString();
        this.status = in.readString();
        this.balanceFuel = in.readString();
        this.timeSlot = in.readString();
        this.slotCheck = in.readString();
        this.compartment1 = in.readString();
        this.compartment2 = in.readString();
        this.compartment3 = in.readString();
        this.compartment4 = in.readString();
        this.compartment5 = in.readString();
        this.createdDate = in.readString();
        this.createdBy = in.readString();
        this.updatedDate = in.readString();
        this.updatedBy = in.readString();
        this.atgSerialNo = in.readString();
        this.atgDipChart = in.readString();
        this.atgDipChartFile = in.readString();
        this.vehicleSerialNo = in.readString();
        this.compartmentInfo = in.createTypedArrayList(CompartmentInfo.CREATOR);
    }

    public static final Creator<VehicleDatum> CREATOR = new Creator<VehicleDatum>() {
        @Override
        public VehicleDatum createFromParcel(Parcel source) {
            return new VehicleDatum(source);
        }

        @Override
        public VehicleDatum[] newArray(int size) {
            return new VehicleDatum[size];
        }
    };

    public Boolean getValve_bypass() {
        return valve_bypass;
    }

    public void setValve_bypass(Boolean valve_bypass) {
        this.valve_bypass = valve_bypass;
    }

    public Boolean getLock_bypass() {
        return lock_bypass;
    }

    public void setLock_bypass(Boolean lock_bypass) {
        this.lock_bypass = lock_bypass;
    }

    public Boolean getAtg_bypass() {
        return atg_bypass;
    }

    public void setAtg_bypass(Boolean atg_bypass) {
        this.atg_bypass = atg_bypass;
    }

    public Boolean getFranchise_bypass() {
        return franchise_bypass;
    }

    public void setFranchise_bypass(Boolean franchise_bypass) {
        this.franchise_bypass = franchise_bypass;
    }

    public String getGeofencing() {
        return geofencing;
    }

    public void setGeofencing(String geofencing) {
        this.geofencing = geofencing;
    }

    public void setNoOfTanks(Object noOfTanks) {
        this.noOfTanks = noOfTanks;
    }
}