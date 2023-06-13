package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseInfo{

    @SerializedName("public_msg")
    private String publicMsg;

    @SerializedName("succ")
    private boolean succ;

    @SerializedName("data")
    private Data data;

    @SerializedName("type")
    private String type;

    @SerializedName("_err_codes")
    private List<Object> errCodes;

    public void setPublicMsg(String publicMsg){
        this.publicMsg = publicMsg;
    }

    public String getPublicMsg(){
        return publicMsg;
    }

    public void setSucc(boolean succ){
        this.succ = succ;
    }

    public boolean isSucc(){
        return succ;
    }

    public void setData(Data data){
        this.data = data;
    }

    public Data getData(){
        return data;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setErrCodes(List<Object> errCodes){
        this.errCodes = errCodes;
    }

    public List<Object> getErrCodes(){
        return errCodes;
    }

    @Override
    public String toString(){
        return
                "FranchiseInfo{" +
                        "public_msg = '" + publicMsg + '\'' +
                        ",succ = '" + succ + '\'' +
                        ",data = '" + data + '\'' +
                        ",type = '" + type + '\'' +
                        ",_err_codes = '" + errCodes + '\'' +
                        "}";
    }

    public class Data{

        @SerializedName("franchise_detail")
        private List<FranchiseDetailItem> franchiseDetail;

        @SerializedName("price")
        private Price price;

        @SerializedName("franchise_vehicle")
        private List<FranchiseVehicleItem> franchiseVehicle;

        public void setFranchiseDetail(List<FranchiseDetailItem> franchiseDetail){
            this.franchiseDetail = franchiseDetail;
        }

        public List<FranchiseDetailItem> getFranchiseDetail(){
            return franchiseDetail;
        }

        public void setPrice(Price price){
            this.price = price;
        }

        public Price getPrice(){
            return price;
        }

        public void setFranchiseVehicle(List<FranchiseVehicleItem> franchiseVehicle){
            this.franchiseVehicle = franchiseVehicle;
        }

        public List<FranchiseInfo.FranchiseVehicleItem> getFranchiseVehicle(){
            return franchiseVehicle;
        }

        @Override
        public String toString(){
            return
                    "Data{" +
                            "franchise_detail = '" + franchiseDetail + '\'' +
                            ",price = '" + price + '\'' +
                            ",franchise_vehicle = '" + franchiseVehicle + '\'' +
                            "}";
        }
    }
    public class FranchiseVehicleItem{

        @SerializedName("reg_no")
        private String regNo;

        @SerializedName("year")
        private String year;

        @SerializedName("make_and_model")
        private String makeAndModel;

        @SerializedName("gps_mismatch_range")
        private String gpsMismatchRange;

        @SerializedName("no_of_tanks")
        private String noOfTanks;

        @SerializedName("engine_no")
        private String engineNo;

        @SerializedName("compartment1")
        private String compartment1;

        @SerializedName("slot_check")
        private String slotCheck;

        @SerializedName("product_id")
        private String productId;

        @SerializedName("chasis_no")
        private String chasisNo;

        @SerializedName("balance_fuel")
        private String balanceFuel;

        @SerializedName("logo")
        private String logo;

        @SerializedName("id")
        private String id;

        @SerializedName("state")
        private String state;

        @SerializedName("fitness_expriry")
        private String fitnessExpriry;

        @SerializedName("engine_nb")
        private String engineNb;

        @SerializedName("no_of_terminals")
        private String noOfTerminals;

        @SerializedName("longitude")
        private String longitude;

        @SerializedName("time_slot")
        private String timeSlot;

        @SerializedName("pincode")
        private String pincode;

        @SerializedName("total_no_of_deliveries")
        private String totalNoOfDeliveries;

        @SerializedName("validity_of_stamping")
        private String validityOfStamping;

        @SerializedName("printer")
        private String printer;

        @SerializedName("status_of_goods_tax")
        private String statusOfGoodsTax;

        @SerializedName("pollution_expiry")
        private String pollutionExpiry;

        @SerializedName("compartment2")
        private String compartment2;

        @SerializedName("compartment3")
        private String compartment3;

        @SerializedName("created_by")
        private String createdBy;

        @SerializedName("compartment4")
        private String compartment4;

        @SerializedName("random_code")
        private String randomCode;

        @SerializedName("status_of_fitness")
        private String statusOfFitness;

        @SerializedName("compartment5")
        private String compartment5;

        @SerializedName("franchisee_id")
        private String franchiseeId;

        @SerializedName("no_of_valves")
        private String noOfValves;

        @SerializedName("status_of_permit")
        private String statusOfPermit;

        @SerializedName("updated_by")
        private String updatedBy;

        @SerializedName("atg_serial_no")
        private String atgSerialNo;

        @SerializedName("status")
        private String status;

        @SerializedName("atg_dip_chart_file")
        private String atgDipChartFile;

        @SerializedName("other")
        private String other;

        @SerializedName("live_vehicle_lng")
        private String liveVehicleLng;

        @SerializedName("no_of_atg")
        private String noOfAtg;

        @SerializedName("make_of_dispenser")
        private String makeOfDispenser;

        @SerializedName("vehicle_serial_no")
        private String vehicleSerialNo;

        @SerializedName("live_vehicle_lat")
        private String liveVehicleLat;

        @SerializedName("city")
        private String city;

        @SerializedName("atg_dip_chart")
        private String atgDipChart;

        @SerializedName("latitude")
        private String latitude;

        @SerializedName("title")
        private String title;

        @SerializedName("omc_id")
        private String omcId;

        @SerializedName("wireless_status")
        private String wirelessStatus;

        @SerializedName("capacity")
        private String capacity;

        @SerializedName("make_of_atg")
        private String makeOfAtg;

        @SerializedName("status_of_puc")
        private String statusOfPuc;

        @SerializedName("make_of_relay_board")
        private String makeOfRelayBoard;

        @SerializedName("tank_shape")
        private String tankShape;

        @SerializedName("sl_no")
        private String slNo;

        @SerializedName("max_deliverable_distance")
        private String maxDeliverableDistance;

        @SerializedName("type_of_permit")
        private String typeOfPermit;

        @SerializedName("no_of_manhole_locks")
        private String noOfManholeLocks;

        @SerializedName("permit_expiry")
        private String permitExpiry;

        @SerializedName("insurance_expiry")
        private String insuranceExpiry;

        @SerializedName("status_of_insurance")
        private String statusOfInsurance;

        @SerializedName("created_date")
        private String createdDate;

        @SerializedName("updated_date")
        private String updatedDate;

        @SerializedName("msd")
        private String msd;

        public void setRegNo(String regNo){
            this.regNo = regNo;
        }

        public String getRegNo(){
            return regNo;
        }

        public void setYear(String year){
            this.year = year;
        }

        public String getYear(){
            return year;
        }

        public void setMakeAndModel(String makeAndModel){
            this.makeAndModel = makeAndModel;
        }

        public String getMakeAndModel(){
            return makeAndModel;
        }

        public void setGpsMismatchRange(String gpsMismatchRange){
            this.gpsMismatchRange = gpsMismatchRange;
        }

        public String getGpsMismatchRange(){
            return gpsMismatchRange;
        }

        public void setNoOfTanks(String noOfTanks){
            this.noOfTanks = noOfTanks;
        }

        public String getNoOfTanks(){
            return noOfTanks;
        }

        public void setEngineNo(String engineNo){
            this.engineNo = engineNo;
        }

        public String getEngineNo(){
            return engineNo;
        }

        public void setCompartment1(String compartment1){
            this.compartment1 = compartment1;
        }

        public String getCompartment1(){
            return compartment1;
        }

        public void setSlotCheck(String slotCheck){
            this.slotCheck = slotCheck;
        }

        public String getSlotCheck(){
            return slotCheck;
        }

        public void setProductId(String productId){
            this.productId = productId;
        }

        public String getProductId(){
            return productId;
        }

        public void setChasisNo(String chasisNo){
            this.chasisNo = chasisNo;
        }

        public String getChasisNo(){
            return chasisNo;
        }

        public void setBalanceFuel(String balanceFuel){
            this.balanceFuel = balanceFuel;
        }

        public String getBalanceFuel(){
            return balanceFuel;
        }

        public void setLogo(String logo){
            this.logo = logo;
        }

        public String getLogo(){
            return logo;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }

        public void setState(String state){
            this.state = state;
        }

        public String getState(){
            return state;
        }

        public void setFitnessExpriry(String fitnessExpriry){
            this.fitnessExpriry = fitnessExpriry;
        }

        public String getFitnessExpriry(){
            return fitnessExpriry;
        }

        public void setEngineNb(String engineNb){
            this.engineNb = engineNb;
        }

        public String getEngineNb(){
            return engineNb;
        }

        public void setNoOfTerminals(String noOfTerminals){
            this.noOfTerminals = noOfTerminals;
        }

        public String getNoOfTerminals(){
            return noOfTerminals;
        }

        public void setLongitude(String longitude){
            this.longitude = longitude;
        }

        public String getLongitude(){
            return longitude;
        }

        public void setTimeSlot(String timeSlot){
            this.timeSlot = timeSlot;
        }

        public String getTimeSlot(){
            return timeSlot;
        }

        public void setPincode(String pincode){
            this.pincode = pincode;
        }

        public String getPincode(){
            return pincode;
        }

        public void setTotalNoOfDeliveries(String totalNoOfDeliveries){
            this.totalNoOfDeliveries = totalNoOfDeliveries;
        }

        public String getTotalNoOfDeliveries(){
            return totalNoOfDeliveries;
        }

        public void setValidityOfStamping(String validityOfStamping){
            this.validityOfStamping = validityOfStamping;
        }

        public String getValidityOfStamping(){
            return validityOfStamping;
        }

        public void setPrinter(String printer){
            this.printer = printer;
        }

        public String getPrinter(){
            return printer;
        }

        public void setStatusOfGoodsTax(String statusOfGoodsTax){
            this.statusOfGoodsTax = statusOfGoodsTax;
        }

        public String getStatusOfGoodsTax(){
            return statusOfGoodsTax;
        }

        public void setPollutionExpiry(String pollutionExpiry){
            this.pollutionExpiry = pollutionExpiry;
        }

        public String getPollutionExpiry(){
            return pollutionExpiry;
        }

        public void setCompartment2(String compartment2){
            this.compartment2 = compartment2;
        }

        public String getCompartment2(){
            return compartment2;
        }

        public void setCompartment3(String compartment3){
            this.compartment3 = compartment3;
        }

        public String getCompartment3(){
            return compartment3;
        }

        public void setCreatedBy(String createdBy){
            this.createdBy = createdBy;
        }

        public String getCreatedBy(){
            return createdBy;
        }

        public void setCompartment4(String compartment4){
            this.compartment4 = compartment4;
        }

        public String getCompartment4(){
            return compartment4;
        }

        public void setRandomCode(String randomCode){
            this.randomCode = randomCode;
        }

        public String getRandomCode(){
            return randomCode;
        }

        public void setStatusOfFitness(String statusOfFitness){
            this.statusOfFitness = statusOfFitness;
        }

        public String getStatusOfFitness(){
            return statusOfFitness;
        }

        public void setCompartment5(String compartment5){
            this.compartment5 = compartment5;
        }

        public String getCompartment5(){
            return compartment5;
        }

        public void setFranchiseeId(String franchiseeId){
            this.franchiseeId = franchiseeId;
        }

        public String getFranchiseeId(){
            return franchiseeId;
        }

        public void setNoOfValves(String noOfValves){
            this.noOfValves = noOfValves;
        }

        public String getNoOfValves(){
            return noOfValves;
        }

        public void setStatusOfPermit(String statusOfPermit){
            this.statusOfPermit = statusOfPermit;
        }

        public String getStatusOfPermit(){
            return statusOfPermit;
        }

        public void setUpdatedBy(String updatedBy){
            this.updatedBy = updatedBy;
        }

        public String getUpdatedBy(){
            return updatedBy;
        }

        public void setAtgSerialNo(String atgSerialNo){
            this.atgSerialNo = atgSerialNo;
        }

        public String getAtgSerialNo(){
            return atgSerialNo;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }

        public void setAtgDipChartFile(String atgDipChartFile){
            this.atgDipChartFile = atgDipChartFile;
        }

        public String getAtgDipChartFile(){
            return atgDipChartFile;
        }

        public void setOther(String other){
            this.other = other;
        }

        public String getOther(){
            return other;
        }

        public void setLiveVehicleLng(String liveVehicleLng){
            this.liveVehicleLng = liveVehicleLng;
        }

        public String getLiveVehicleLng(){
            return liveVehicleLng;
        }

        public void setNoOfAtg(String noOfAtg){
            this.noOfAtg = noOfAtg;
        }

        public String getNoOfAtg(){
            return noOfAtg;
        }

        public void setMakeOfDispenser(String makeOfDispenser){
            this.makeOfDispenser = makeOfDispenser;
        }

        public String getMakeOfDispenser(){
            return makeOfDispenser;
        }

        public void setVehicleSerialNo(String vehicleSerialNo){
            this.vehicleSerialNo = vehicleSerialNo;
        }

        public String getVehicleSerialNo(){
            return vehicleSerialNo;
        }

        public void setLiveVehicleLat(String liveVehicleLat){
            this.liveVehicleLat = liveVehicleLat;
        }

        public String getLiveVehicleLat(){
            return liveVehicleLat;
        }

        public void setCity(String city){
            this.city = city;
        }

        public String getCity(){
            return city;
        }

        public void setAtgDipChart(String atgDipChart){
            this.atgDipChart = atgDipChart;
        }

        public String getAtgDipChart(){
            return atgDipChart;
        }

        public void setLatitude(String latitude){
            this.latitude = latitude;
        }

        public String getLatitude(){
            return latitude;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }

        public void setOmcId(String omcId){
            this.omcId = omcId;
        }

        public String getOmcId(){
            return omcId;
        }

        public void setWirelessStatus(String wirelessStatus){
            this.wirelessStatus = wirelessStatus;
        }

        public String getWirelessStatus(){
            return wirelessStatus;
        }

        public void setCapacity(String capacity){
            this.capacity = capacity;
        }

        public String getCapacity(){
            return capacity;
        }

        public void setMakeOfAtg(String makeOfAtg){
            this.makeOfAtg = makeOfAtg;
        }

        public String getMakeOfAtg(){
            return makeOfAtg;
        }

        public void setStatusOfPuc(String statusOfPuc){
            this.statusOfPuc = statusOfPuc;
        }

        public String getStatusOfPuc(){
            return statusOfPuc;
        }

        public void setMakeOfRelayBoard(String makeOfRelayBoard){
            this.makeOfRelayBoard = makeOfRelayBoard;
        }

        public String getMakeOfRelayBoard(){
            return makeOfRelayBoard;
        }

        public void setTankShape(String tankShape){
            this.tankShape = tankShape;
        }

        public String getTankShape(){
            return tankShape;
        }

        public void setSlNo(String slNo){
            this.slNo = slNo;
        }

        public String getSlNo(){
            return slNo;
        }

        public void setMaxDeliverableDistance(String maxDeliverableDistance){
            this.maxDeliverableDistance = maxDeliverableDistance;
        }

        public String getMaxDeliverableDistance(){
            return maxDeliverableDistance;
        }

        public void setTypeOfPermit(String typeOfPermit){
            this.typeOfPermit = typeOfPermit;
        }

        public String getTypeOfPermit(){
            return typeOfPermit;
        }

        public void setNoOfManholeLocks(String noOfManholeLocks){
            this.noOfManholeLocks = noOfManholeLocks;
        }

        public String getNoOfManholeLocks(){
            return noOfManholeLocks;
        }

        public void setPermitExpiry(String permitExpiry){
            this.permitExpiry = permitExpiry;
        }

        public String getPermitExpiry(){
            return permitExpiry;
        }

        public void setInsuranceExpiry(String insuranceExpiry){
            this.insuranceExpiry = insuranceExpiry;
        }

        public String getInsuranceExpiry(){
            return insuranceExpiry;
        }

        public void setStatusOfInsurance(String statusOfInsurance){
            this.statusOfInsurance = statusOfInsurance;
        }

        public String getStatusOfInsurance(){
            return statusOfInsurance;
        }

        public void setCreatedDate(String createdDate){
            this.createdDate = createdDate;
        }

        public String getCreatedDate(){
            return createdDate;
        }

        public void setUpdatedDate(String updatedDate){
            this.updatedDate = updatedDate;
        }

        public String getUpdatedDate(){
            return updatedDate;
        }

        public void setMsd(String msd){
            this.msd = msd;
        }

        public String getMsd(){
            return msd;
        }

        @Override
        public String toString(){
            return
                    "FranchiseVehicleItem{" +
                            "reg_no = '" + regNo + '\'' +
                            ",year = '" + year + '\'' +
                            ",make_and_model = '" + makeAndModel + '\'' +
                            ",gps_mismatch_range = '" + gpsMismatchRange + '\'' +
                            ",no_of_tanks = '" + noOfTanks + '\'' +
                            ",engine_no = '" + engineNo + '\'' +
                            ",compartment1 = '" + compartment1 + '\'' +
                            ",slot_check = '" + slotCheck + '\'' +
                            ",product_id = '" + productId + '\'' +
                            ",chasis_no = '" + chasisNo + '\'' +
                            ",balance_fuel = '" + balanceFuel + '\'' +
                            ",logo = '" + logo + '\'' +
                            ",id = '" + id + '\'' +
                            ",state = '" + state + '\'' +
                            ",fitness_expriry = '" + fitnessExpriry + '\'' +
                            ",engine_nb = '" + engineNb + '\'' +
                            ",no_of_terminals = '" + noOfTerminals + '\'' +
                            ",longitude = '" + longitude + '\'' +
                            ",time_slot = '" + timeSlot + '\'' +
                            ",pincode = '" + pincode + '\'' +
                            ",total_no_of_deliveries = '" + totalNoOfDeliveries + '\'' +
                            ",validity_of_stamping = '" + validityOfStamping + '\'' +
                            ",printer = '" + printer + '\'' +
                            ",status_of_goods_tax = '" + statusOfGoodsTax + '\'' +
                            ",pollution_expiry = '" + pollutionExpiry + '\'' +
                            ",compartment2 = '" + compartment2 + '\'' +
                            ",compartment3 = '" + compartment3 + '\'' +
                            ",created_by = '" + createdBy + '\'' +
                            ",compartment4 = '" + compartment4 + '\'' +
                            ",random_code = '" + randomCode + '\'' +
                            ",status_of_fitness = '" + statusOfFitness + '\'' +
                            ",compartment5 = '" + compartment5 + '\'' +
                            ",franchisee_id = '" + franchiseeId + '\'' +
                            ",no_of_valves = '" + noOfValves + '\'' +
                            ",status_of_permit = '" + statusOfPermit + '\'' +
                            ",updated_by = '" + updatedBy + '\'' +
                            ",atg_serial_no = '" + atgSerialNo + '\'' +
                            ",status = '" + status + '\'' +
                            ",atg_dip_chart_file = '" + atgDipChartFile + '\'' +
                            ",other = '" + other + '\'' +
                            ",live_vehicle_lng = '" + liveVehicleLng + '\'' +
                            ",no_of_atg = '" + noOfAtg + '\'' +
                            ",make_of_dispenser = '" + makeOfDispenser + '\'' +
                            ",vehicle_serial_no = '" + vehicleSerialNo + '\'' +
                            ",live_vehicle_lat = '" + liveVehicleLat + '\'' +
                            ",city = '" + city + '\'' +
                            ",atg_dip_chart = '" + atgDipChart + '\'' +
                            ",latitude = '" + latitude + '\'' +
                            ",title = '" + title + '\'' +
                            ",omc_id = '" + omcId + '\'' +
                            ",wireless_status = '" + wirelessStatus + '\'' +
                            ",capacity = '" + capacity + '\'' +
                            ",make_of_atg = '" + makeOfAtg + '\'' +
                            ",status_of_puc = '" + statusOfPuc + '\'' +
                            ",make_of_relay_board = '" + makeOfRelayBoard + '\'' +
                            ",tank_shape = '" + tankShape + '\'' +
                            ",sl_no = '" + slNo + '\'' +
                            ",max_deliverable_distance = '" + maxDeliverableDistance + '\'' +
                            ",type_of_permit = '" + typeOfPermit + '\'' +
                            ",no_of_manhole_locks = '" + noOfManholeLocks + '\'' +
                            ",permit_expiry = '" + permitExpiry + '\'' +
                            ",insurance_expiry = '" + insuranceExpiry + '\'' +
                            ",status_of_insurance = '" + statusOfInsurance + '\'' +
                            ",created_date = '" + createdDate + '\'' +
                            ",updated_date = '" + updatedDate + '\'' +
                            ",msd = '" + msd + '\'' +
                            "}";
        }
    }


    public class FranchiseDetailItem{

        @SerializedName("email_id")
        private String emailId;

        @SerializedName("account_number")
        private String accountNumber;

        @SerializedName("account_type")
        private String accountType;

        @SerializedName("Sl_no")
        private String slNo;

        @SerializedName("omc")
        private String omc;

        @SerializedName("city")
        private String city;

        @SerializedName("Product_id")
        private String productId;

        @SerializedName("IFSC_code")
        private String iFSCCode;

        @SerializedName("omc_id")
        private String omcId;

        @SerializedName("login_mast_id")
        private String loginMastId;

        @SerializedName("gst_userid")
        private String gstUserid;

        @SerializedName("password")
        private String password;

        @SerializedName("contact_num")
        private String contactNum;

        @SerializedName("gst_no")
        private String gstNo;

        @SerializedName("branch_name")
        private String branchName;

        @SerializedName("bank_name")
        private String bankName;

        @SerializedName("id")
        private String id;

        @SerializedName("state")
        private String state;

        @SerializedName("ewaybill_password")
        private String ewaybillPassword;

        @SerializedName("lat")
        private String lat;

        @SerializedName("gst_password")
        private String gstPassword;

        @SerializedName("longitude")
        private String longitude;

        @SerializedName("ewaybill_userid")
        private String ewaybillUserid;

        @SerializedName("footer_message")
        private String footerMessage;

        @SerializedName("pincode")
        private String pincode;

        @SerializedName("address")
        private String address;

        @SerializedName("contact_person")
        private String contactPerson;

        @SerializedName("franchise_serial_no")
        private String franchiseSerialNo;

        @SerializedName("signature_image")
        private String signatureImage;

        @SerializedName("bank_add")
        private String bankAdd;

        @SerializedName("franchisee_code_omc")
        private String franchiseeCodeOmc;

        @SerializedName("createdDate")
        private String createdDate;

        @SerializedName("name")
        private String name;

        @SerializedName("status")
        private String status;

        public void setEmailId(String emailId){
            this.emailId = emailId;
        }

        public String getEmailId(){
            return emailId;
        }

        public void setAccountNumber(String accountNumber){
            this.accountNumber = accountNumber;
        }

        public String getAccountNumber(){
            return accountNumber;
        }

        public void setAccountType(String accountType){
            this.accountType = accountType;
        }

        public String getAccountType(){
            return accountType;
        }

        public void setSlNo(String slNo){
            this.slNo = slNo;
        }

        public String getSlNo(){
            return slNo;
        }

        public void setOmc(String omc){
            this.omc = omc;
        }

        public String getOmc(){
            return omc;
        }

        public void setCity(String city){
            this.city = city;
        }

        public String getCity(){
            return city;
        }

        public void setProductId(String productId){
            this.productId = productId;
        }

        public String getProductId(){
            return productId;
        }

        public void setIFSCCode(String iFSCCode){
            this.iFSCCode = iFSCCode;
        }

        public String getIFSCCode(){
            return iFSCCode;
        }

        public void setOmcId(String omcId){
            this.omcId = omcId;
        }

        public String getOmcId(){
            return omcId;
        }

        public void setLoginMastId(String loginMastId){
            this.loginMastId = loginMastId;
        }

        public String getLoginMastId(){
            return loginMastId;
        }

        public void setGstUserid(String gstUserid){
            this.gstUserid = gstUserid;
        }

        public String getGstUserid(){
            return gstUserid;
        }

        public void setPassword(String password){
            this.password = password;
        }

        public String getPassword(){
            return password;
        }

        public void setContactNum(String contactNum){
            this.contactNum = contactNum;
        }

        public String getContactNum(){
            return contactNum;
        }

        public void setGstNo(String gstNo){
            this.gstNo = gstNo;
        }

        public String getGstNo(){
            return gstNo;
        }

        public void setBranchName(String branchName){
            this.branchName = branchName;
        }

        public String getBranchName(){
            return branchName;
        }

        public void setBankName(String bankName){
            this.bankName = bankName;
        }

        public String getBankName(){
            return bankName;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }

        public void setState(String state){
            this.state = state;
        }

        public String getState(){
            return state;
        }

        public void setEwaybillPassword(String ewaybillPassword){
            this.ewaybillPassword = ewaybillPassword;
        }

        public String getEwaybillPassword(){
            return ewaybillPassword;
        }

        public void setLat(String lat){
            this.lat = lat;
        }

        public String getLat(){
            return lat;
        }

        public void setGstPassword(String gstPassword){
            this.gstPassword = gstPassword;
        }

        public String getGstPassword(){
            return gstPassword;
        }

        public void setLongitude(String longitude){
            this.longitude = longitude;
        }

        public String getLongitude(){
            return longitude;
        }

        public void setEwaybillUserid(String ewaybillUserid){
            this.ewaybillUserid = ewaybillUserid;
        }

        public String getEwaybillUserid(){
            return ewaybillUserid;
        }

        public void setFooterMessage(String footerMessage){
            this.footerMessage = footerMessage;
        }

        public String getFooterMessage(){
            return footerMessage;
        }

        public void setPincode(String pincode){
            this.pincode = pincode;
        }

        public String getPincode(){
            return pincode;
        }

        public void setAddress(String address){
            this.address = address;
        }

        public String getAddress(){
            return address;
        }

        public void setContactPerson(String contactPerson){
            this.contactPerson = contactPerson;
        }

        public String getContactPerson(){
            return contactPerson;
        }

        public void setFranchiseSerialNo(String franchiseSerialNo){
            this.franchiseSerialNo = franchiseSerialNo;
        }

        public String getFranchiseSerialNo(){
            return franchiseSerialNo;
        }

        public void setSignatureImage(String signatureImage){
            this.signatureImage = signatureImage;
        }

        public String getSignatureImage(){
            return signatureImage;
        }

        public void setBankAdd(String bankAdd){
            this.bankAdd = bankAdd;
        }

        public String getBankAdd(){
            return bankAdd;
        }

        public void setFranchiseeCodeOmc(String franchiseeCodeOmc){
            this.franchiseeCodeOmc = franchiseeCodeOmc;
        }

        public String getFranchiseeCodeOmc(){
            return franchiseeCodeOmc;
        }

        public void setCreatedDate(String createdDate){
            this.createdDate = createdDate;
        }

        public String getCreatedDate(){
            return createdDate;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }

        @Override
        public String toString(){
            return
                    "FranchiseDetailItem{" +
                            "email_id = '" + emailId + '\'' +
                            ",account_number = '" + accountNumber + '\'' +
                            ",account_type = '" + accountType + '\'' +
                            ",sl_no = '" + slNo + '\'' +
                            ",omc = '" + omc + '\'' +
                            ",city = '" + city + '\'' +
                            ",product_id = '" + productId + '\'' +
                            ",iFSC_code = '" + iFSCCode + '\'' +
                            ",omc_id = '" + omcId + '\'' +
                            ",login_mast_id = '" + loginMastId + '\'' +
                            ",gst_userid = '" + gstUserid + '\'' +
                            ",password = '" + password + '\'' +
                            ",contact_num = '" + contactNum + '\'' +
                            ",gst_no = '" + gstNo + '\'' +
                            ",branch_name = '" + branchName + '\'' +
                            ",bank_name = '" + bankName + '\'' +
                            ",id = '" + id + '\'' +
                            ",state = '" + state + '\'' +
                            ",ewaybill_password = '" + ewaybillPassword + '\'' +
                            ",lat = '" + lat + '\'' +
                            ",gst_password = '" + gstPassword + '\'' +
                            ",longitude = '" + longitude + '\'' +
                            ",ewaybill_userid = '" + ewaybillUserid + '\'' +
                            ",footer_message = '" + footerMessage + '\'' +
                            ",pincode = '" + pincode + '\'' +
                            ",address = '" + address + '\'' +
                            ",contact_person = '" + contactPerson + '\'' +
                            ",franchise_serial_no = '" + franchiseSerialNo + '\'' +
                            ",signature_image = '" + signatureImage + '\'' +
                            ",bank_add = '" + bankAdd + '\'' +
                            ",franchisee_code_omc = '" + franchiseeCodeOmc + '\'' +
                            ",createdDate = '" + createdDate + '\'' +
                            ",name = '" + name + '\'' +
                            ",status = '" + status + '\'' +
                            "}";
        }
    }

    public class Price{

        @SerializedName("auto_id")
        private String autoId;

        @SerializedName("price")
        private String price;

        @SerializedName("name")
        private String name;

        public void setAutoId(String autoId){
            this.autoId = autoId;
        }

        public String getAutoId(){
            return autoId;
        }

        public void setPrice(String price){
            this.price = price;
        }

        public String getPrice(){
            return price;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        @Override
        public String toString(){
            return
                    "Price{" +
                            "auto_id = '" + autoId + '\'' +
                            ",price = '" + price + '\'' +
                            ",name = '" + name + '\'' +
                            "}";
        }
    }
}