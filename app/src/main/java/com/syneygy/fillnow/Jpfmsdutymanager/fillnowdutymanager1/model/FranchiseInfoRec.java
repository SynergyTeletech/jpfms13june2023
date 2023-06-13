package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseInfoRec {


    public class Data {

        @SerializedName("franchise_detail")
        @Expose
        private List<FranchiseDetail> franchiseDetail = null;
        @SerializedName("franchise_bypass")
        @Expose
        private Boolean franchiseBypass;
        @SerializedName("franchise_vehicle")
        @Expose
        private List<FranchiseVehicle> franchiseVehicle = null;
        @SerializedName("price")
        @Expose
        private List<Object> price = null;

        public List<FranchiseDetail> getFranchiseDetail() {
            return franchiseDetail;
        }

        public void setFranchiseDetail(List<FranchiseDetail> franchiseDetail) {
            this.franchiseDetail = franchiseDetail;
        }

        public Boolean getFranchiseBypass() {
            return franchiseBypass;
        }

        public void setFranchiseBypass(Boolean franchiseBypass) {
            this.franchiseBypass = franchiseBypass;
        }

        public List<FranchiseVehicle> getFranchiseVehicle() {
            return franchiseVehicle;
        }

        public void setFranchiseVehicle(List<FranchiseVehicle> franchiseVehicle) {
            this.franchiseVehicle = franchiseVehicle;
        }

        public List<Object> getPrice() {
            return price;
        }

        public void setPrice(List<Object> price) {
            this.price = price;
        }

    }
    public class FranchiseDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("login_mast_id")
        @Expose
        private String loginMastId;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("email_id")
        @Expose
        private String emailId;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("gst_no")
        @Expose
        private String gstNo;
        @SerializedName("contact_person")
        @Expose
        private String contactPerson;
        @SerializedName("contact_num")
        @Expose
        private String contactNum;
        @SerializedName("omc")
        @Expose
        private String omc;
        @SerializedName("franchisee_code_omc")
        @Expose
        private String franchiseeCodeOmc;
        @SerializedName("omc_id")
        @Expose
        private String omcId;
        @SerializedName("footer_message")
        @Expose
        private String footerMessage;
        @SerializedName("bank_name")
        @Expose
        private String bankName;
        @SerializedName("branch_name")
        @Expose
        private String branchName;
        @SerializedName("bank_add")
        @Expose
        private String bankAdd;
        @SerializedName("account_type")
        @Expose
        private String accountType;
        @SerializedName("account_number")
        @Expose
        private String accountNumber;
        @SerializedName("IFSC_code")
        @Expose
        private String iFSCCode;
        @SerializedName("Product_id")
        @Expose
        private String productId;
        @SerializedName("Sl_no")
        @Expose
        private String slNo;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("franchise_serial_no")
        @Expose
        private String franchiseSerialNo;
        @SerializedName("gst_userid")
        @Expose
        private String gstUserid;
        @SerializedName("gst_password")
        @Expose
        private String gstPassword;
        @SerializedName("ewaybill_userid")
        @Expose
        private String ewaybillUserid;
        @SerializedName("ewaybill_password")
        @Expose
        private String ewaybillPassword;
        @SerializedName("signature_image")
        @Expose
        private String signatureImage;

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

        public String getIFSCCode() {
            return iFSCCode;
        }

        public void setIFSCCode(String iFSCCode) {
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

        public void setEwaybillPassword(String ewaybillPassword) {
            this.ewaybillPassword = ewaybillPassword;
        }

        public String getSignatureImage() {
            return signatureImage;
        }

        public void setSignatureImage(String signatureImage) {
            this.signatureImage = signatureImage;
        }

    }
    public class FranchiseVehicle {

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
        @SerializedName("franchisee_id")
        @Expose
        private String franchiseeId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("other")
        @Expose
        private String other;
        @SerializedName("capacity")
        @Expose
        private String capacity;
        @SerializedName("no_of_tanks")
        @Expose
        private String noOfTanks;
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
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("no_of_nozzle")
        @Expose
        private String noOfNozzle;
        @SerializedName("make_by_fcc")
        @Expose
        private String makeByFcc;
        @SerializedName("master_rfid_type")
        @Expose
        private String masterRfidType;
        @SerializedName("master_rfid_id")
        @Expose
        private String masterRfidId;
        @SerializedName("geofencing")
        @Expose
        private String geofencing;
        @SerializedName("live_vehicle_lat")
        @Expose
        private String liveVehicleLat;
        @SerializedName("live_vehicle_lng")
        @Expose
        private String liveVehicleLng;

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
            return noOfTanks;
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

        public String getBalanceFuel() {
            return balanceFuel;
        }

        public void setBalanceFuel(String balanceFuel) {
            this.balanceFuel = balanceFuel;
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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getNoOfNozzle() {
            return noOfNozzle;
        }

        public void setNoOfNozzle(String noOfNozzle) {
            this.noOfNozzle = noOfNozzle;
        }

        public String getMakeByFcc() {
            return makeByFcc;
        }

        public void setMakeByFcc(String makeByFcc) {
            this.makeByFcc = makeByFcc;
        }

        public String getMasterRfidType() {
            return masterRfidType;
        }

        public void setMasterRfidType(String masterRfidType) {
            this.masterRfidType = masterRfidType;
        }

        public String getMasterRfidId() {
            return masterRfidId;
        }

        public void setMasterRfidId(String masterRfidId) {
            this.masterRfidId = masterRfidId;
        }

        public String getGeofencing() {
            return geofencing;
        }

        public void setGeofencing(String geofencing) {
            this.geofencing = geofencing;
        }

        public String getLiveVehicleLat() {
            return liveVehicleLat;
        }

        public void setLiveVehicleLat(String liveVehicleLat) {
            this.liveVehicleLat = liveVehicleLat;
        }

        public String getLiveVehicleLng() {
            return liveVehicleLng;
        }

        public void setLiveVehicleLng(String liveVehicleLng) {
            this.liveVehicleLng = liveVehicleLng;
        }

    }

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
        @SerializedName("data")
        @Expose
        private Data data;

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

        public List<Object> getErrCodes() {
            return errCodes;
        }

        public void setErrCodes(List<Object> errCodes) {
            this.errCodes = errCodes;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }



}
