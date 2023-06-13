package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class TransactionDbModel {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String transactionId;

    @ColumnInfo(name = "Date")
    public String Date;

    @ColumnInfo(name = "Fueling_Start_Time")
    public String Fueling_Start_Time;

    @ColumnInfo(name = "Fueling_Stop_Time")
    public String Fueling_Stop_Time;

    @ColumnInfo(name = "Transaction_Type")
    public String Transaction_Type;

    @ColumnInfo(name = "Duty_Manager_ID")
    public String Duty_Manager_ID;

    @ColumnInfo(name = "Duty_Manager_Name")
    public String Duty_Manager_Name;

    @ColumnInfo(name = "Vehicle_ID")
    public String Vehicle_ID;

    @ColumnInfo(name = "Vehicle_Reg_No")
    public String Vehicle_Reg_No;

    @ColumnInfo(name = "Franchisee_ID")
    public String Franchisee_ID;

    @ColumnInfo(name = "Franchisee_Name")
    public String Franchisee_Name;

    @ColumnInfo(name = "Customer_ID")
    public String Customer_ID;

    @ColumnInfo(name = "Customer_Name")
    public String Customer_Name;

    @ColumnInfo(name = "Location_ID")
    public String Location_ID;

    @ColumnInfo(name = "Location_Name")
    public String Location_Name;

    @ColumnInfo(name = "Latitude")
    public String Latitude;

    @ColumnInfo(name = "Longitude")
    public String Longitude;

    @ColumnInfo(name = "Terminal_Id")
    public String Terminal_Id;

    @ColumnInfo(name = "Batch_No")
    public String Batch_No;

    @ColumnInfo(name = "Transaction_No")
    public String Transaction_No;

    @ColumnInfo(name = "Product")
    public String Product;

    @ColumnInfo(name = "Asset_ID")
    public String Asset_ID;

    @ColumnInfo(name = "Asset_Name")
    public String Asset_Name;

    @ColumnInfo(name = "Dispense_Qty")
    public String Dispense_Qty;

    @ColumnInfo(name = "Unit_Price")
    public String Unit_Price;

    @ColumnInfo(name = "Amount")
    public String Amount;

    @ColumnInfo(name = "Meter_Reading")
    public String Meter_Reading;

    @ColumnInfo(name = "Asset_Other_Reading")
    public String Asset_Other_Reading;

    @ColumnInfo(name = "Payment_Ref_number")
    public String Payment_Ref_number;

    @ColumnInfo(name = "ATG_Tank_Start_Reading")
    public String ATG_Tank_Start_Reading;

    @ColumnInfo(name = "ATG_Tank_End_Reading")
    public String ATG_Tank_End_Reading;

    @ColumnInfo(name = "Volume_Totalizer")
    public String Volume_Totalizer;

    @ColumnInfo(name = "GPS_Status")
    public String GPS_Status;

    @ColumnInfo(name = "Manhole_Status")
    public String Manhole_Status;

    @ColumnInfo(name = "RFID_Status")
    public String RFID_Status;

    @ColumnInfo(name = "DCV_Status")
    public String DCV_Status;

    @ColumnInfo(name = "ATG_Status")
    public String ATG_Status;

    @ColumnInfo(name = "Location_Reading_1")
    public String Location_Reading_1;

    @ColumnInfo(name = "Location_Reading_2")
    public String Location_Reading_2;

    @ColumnInfo(name = "No_of_event_")
    public String No_of_event_;

    @ColumnInfo(name = "Dispensed_in_")
    public String Dispensed_in_;

    @ColumnInfo(name = "Order_ID")
    public String Order_ID;

    public TransactionDbModel(String transactionId, String date, String fueling_Start_Time, String fueling_Stop_Time, String transaction_Type, String duty_Manager_ID, String duty_Manager_Name, String vehicle_ID, String vehicle_Reg_No, String franchisee_ID, String franchisee_Name, String customer_ID, String customer_Name, String location_ID, String location_Name, String latitude, String longitude, String terminal_Id, String batch_No, String transaction_No, String product, String asset_ID, String asset_Name, String dispense_Qty, String unit_Price, String amount, String meter_Reading, String asset_Other_Reading, String payment_Ref_number, String ATG_Tank_Start_Reading, String ATG_Tank_End_Reading, String volume_Totalizer, String GPS_Status, String manhole_Status, String RFID_Status, String DCV_Status, String ATG_Status, String location_Reading_1, String location_Reading_2, String no_of_event_, String dispensed_in_, String order_ID) {
        this.transactionId = transactionId;
        Date = date;
        Fueling_Start_Time = fueling_Start_Time;
        Fueling_Stop_Time = fueling_Stop_Time;
        Transaction_Type = transaction_Type;
        Duty_Manager_ID = duty_Manager_ID;
        Duty_Manager_Name = duty_Manager_Name;
        Vehicle_ID = vehicle_ID;
        Vehicle_Reg_No = vehicle_Reg_No;
        Franchisee_ID = franchisee_ID;
        Franchisee_Name = franchisee_Name;
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Location_ID = location_ID;
        Location_Name = location_Name;
        Latitude = latitude;
        Longitude = longitude;
        Terminal_Id = terminal_Id;
        Batch_No = batch_No;
        Transaction_No = transaction_No;
        Product = product;
        Asset_ID = asset_ID;
        Asset_Name = asset_Name;
        Dispense_Qty = dispense_Qty;
        Unit_Price = unit_Price;
        Amount = amount;
        Meter_Reading = meter_Reading;
        Asset_Other_Reading = asset_Other_Reading;
        Payment_Ref_number = payment_Ref_number;
        this.ATG_Tank_Start_Reading = ATG_Tank_Start_Reading;
        this.ATG_Tank_End_Reading = ATG_Tank_End_Reading;
        Volume_Totalizer = volume_Totalizer;
        this.GPS_Status = GPS_Status;
        Manhole_Status = manhole_Status;
        this.RFID_Status = RFID_Status;
        this.DCV_Status = DCV_Status;
        this.ATG_Status = ATG_Status;
        Location_Reading_1 = location_Reading_1;
        Location_Reading_2 = location_Reading_2;
        No_of_event_ = no_of_event_;
        Dispensed_in_ = dispensed_in_;
        Order_ID = order_ID;
    }

    public TransactionDbModel(@NonNull String transactionId, String fueling_Start_Time) {
        this.Fueling_Start_Time = fueling_Start_Time;
        this.transactionId = transactionId;

    }

    public TransactionDbModel() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFueling_Start_Time() {
        return Fueling_Start_Time;
    }

    public void setFueling_Start_Time(String fueling_Start_Time) {
        Fueling_Start_Time = fueling_Start_Time;
    }

    public String getFueling_Stop_Time() {
        return Fueling_Stop_Time;
    }

    public void setFueling_Stop_Time(String fueling_Stop_Time) {
        Fueling_Stop_Time = fueling_Stop_Time;
    }

    public String getTransaction_Type() {
        return Transaction_Type;
    }

    public void setTransaction_Type(String transaction_Type) {
        Transaction_Type = transaction_Type;
    }

    public String getDuty_Manager_ID() {
        return Duty_Manager_ID;
    }

    public void setDuty_Manager_ID(String duty_Manager_ID) {
        Duty_Manager_ID = duty_Manager_ID;
    }

    public String getDuty_Manager_Name() {
        return Duty_Manager_Name;
    }

    public void setDuty_Manager_Name(String duty_Manager_Name) {
        Duty_Manager_Name = duty_Manager_Name;
    }

    public String getVehicle_ID() {
        return Vehicle_ID;
    }

    public void setVehicle_ID(String vehicle_ID) {
        Vehicle_ID = vehicle_ID;
    }

    public String getVehicle_Reg_No() {
        return Vehicle_Reg_No;
    }

    public void setVehicle_Reg_No(String vehicle_Reg_No) {
        Vehicle_Reg_No = vehicle_Reg_No;
    }

    public String getFranchisee_ID() {
        return Franchisee_ID;
    }

    public void setFranchisee_ID(String franchisee_ID) {
        Franchisee_ID = franchisee_ID;
    }

    public String getFranchisee_Name() {
        return Franchisee_Name;
    }

    public void setFranchisee_Name(String franchisee_Name) {
        Franchisee_Name = franchisee_Name;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getLocation_ID() {
        return Location_ID;
    }

    public void setLocation_ID(String location_ID) {
        Location_ID = location_ID;
    }

    public String getLocation_Name() {
        return Location_Name;
    }

    public void setLocation_Name(String location_Name) {
        Location_Name = location_Name;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTerminal_Id() {
        return Terminal_Id;
    }

    public void setTerminal_Id(String terminal_Id) {
        Terminal_Id = terminal_Id;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public String getTransaction_No() {
        return Transaction_No;
    }

    public void setTransaction_No(String transaction_No) {
        Transaction_No = transaction_No;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getAsset_ID() {
        return Asset_ID;
    }

    public void setAsset_ID(String asset_ID) {
        Asset_ID = asset_ID;
    }

    public String getAsset_Name() {
        return Asset_Name;
    }

    public void setAsset_Name(String asset_Name) {
        Asset_Name = asset_Name;
    }

    public String getDispense_Qty() {
        return Dispense_Qty;
    }

    public void setDispense_Qty(String dispense_Qty) {
        Dispense_Qty = dispense_Qty;
    }

    public String getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(String unit_Price) {
        Unit_Price = unit_Price;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMeter_Reading() {
        return Meter_Reading;
    }

    public void setMeter_Reading(String meter_Reading) {
        Meter_Reading = meter_Reading;
    }

    public String getAsset_Other_Reading() {
        return Asset_Other_Reading;
    }

    public void setAsset_Other_Reading(String asset_Other_Reading) {
        Asset_Other_Reading = asset_Other_Reading;
    }

    public String getPayment_Ref_number() {
        return Payment_Ref_number;
    }

    public void setPayment_Ref_number(String payment_Ref_number) {
        Payment_Ref_number = payment_Ref_number;
    }

    public String getATG_Tank_Start_Reading() {
        return ATG_Tank_Start_Reading;
    }

    public void setATG_Tank_Start_Reading(String ATG_Tank_Start_Reading) {
        this.ATG_Tank_Start_Reading = ATG_Tank_Start_Reading;
    }

    public String getATG_Tank_End_Reading() {
        return ATG_Tank_End_Reading;
    }

    public void setATG_Tank_End_Reading(String ATG_Tank_End_Reading) {
        this.ATG_Tank_End_Reading = ATG_Tank_End_Reading;
    }

    public String getVolume_Totalizer() {
        return Volume_Totalizer;
    }

    public void setVolume_Totalizer(String volume_Totalizer) {
        Volume_Totalizer = volume_Totalizer;
    }

    public String getGPS_Status() {
        return GPS_Status;
    }

    public void setGPS_Status(String GPS_Status) {
        this.GPS_Status = GPS_Status;
    }

    public String getManhole_Status() {
        return Manhole_Status;
    }

    public void setManhole_Status(String manhole_Status) {
        Manhole_Status = manhole_Status;
    }

    public String getRFID_Status() {
        return RFID_Status;
    }

    public void setRFID_Status(String RFID_Status) {
        this.RFID_Status = RFID_Status;
    }

    public String getDCV_Status() {
        return DCV_Status;
    }

    public void setDCV_Status(String DCV_Status) {
        this.DCV_Status = DCV_Status;
    }

    public String getATG_Status() {
        return ATG_Status;
    }

    public void setATG_Status(String ATG_Status) {
        this.ATG_Status = ATG_Status;
    }

    public String getLocation_Reading_1() {
        return Location_Reading_1;
    }

    public void setLocation_Reading_1(String location_Reading_1) {
        Location_Reading_1 = location_Reading_1;
    }

    public String getLocation_Reading_2() {
        return Location_Reading_2;
    }

    public void setLocation_Reading_2(String location_Reading_2) {
        Location_Reading_2 = location_Reading_2;
    }

    public String getNo_of_event_() {
        return No_of_event_;
    }

    public void setNo_of_event_(String no_of_event_) {
        No_of_event_ = no_of_event_;
    }

    public String getDispensed_in_() {
        return Dispensed_in_;
    }

    public void setDispensed_in_(String dispensed_in_) {
        Dispensed_in_ = dispensed_in_;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    @Override
    public String toString() {
        return "TransactionDbModel{" +
                "transactionId='" + transactionId + '\'' +
                ", Date='" + Date + '\'' +
                ", Fueling_Start_Time='" + Fueling_Start_Time + '\'' +
                ", Fueling_Stop_Time='" + Fueling_Stop_Time + '\'' +
                ", Transaction_Type='" + Transaction_Type + '\'' +
                ", Duty_Manager_ID='" + Duty_Manager_ID + '\'' +
                ", Duty_Manager_Name='" + Duty_Manager_Name + '\'' +
                ", Vehicle_ID='" + Vehicle_ID + '\'' +
                ", Vehicle_Reg_No='" + Vehicle_Reg_No + '\'' +
                ", Franchisee_ID='" + Franchisee_ID + '\'' +
                ", Franchisee_Name='" + Franchisee_Name + '\'' +
                ", Customer_ID='" + Customer_ID + '\'' +
                ", Customer_Name='" + Customer_Name + '\'' +
                ", Location_ID='" + Location_ID + '\'' +
                ", Location_Name='" + Location_Name + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Terminal_Id='" + Terminal_Id + '\'' +
                ", Batch_No='" + Batch_No + '\'' +
                ", Transaction_No='" + Transaction_No + '\'' +
                ", Product='" + Product + '\'' +
                ", Asset_ID='" + Asset_ID + '\'' +
                ", Asset_Name='" + Asset_Name + '\'' +
                ", Dispense_Qty='" + Dispense_Qty + '\'' +
                ", Unit_Price='" + Unit_Price + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Meter_Reading='" + Meter_Reading + '\'' +
                ", Asset_Other_Reading='" + Asset_Other_Reading + '\'' +
                ", Payment_Ref_number='" + Payment_Ref_number + '\'' +
                ", ATG_Tank_Start_Reading='" + ATG_Tank_Start_Reading + '\'' +
                ", ATG_Tank_End_Reading='" + ATG_Tank_End_Reading + '\'' +
                ", Volume_Totalizer='" + Volume_Totalizer + '\'' +
                ", GPS_Status='" + GPS_Status + '\'' +
                ", Manhole_Status='" + Manhole_Status + '\'' +
                ", RFID_Status='" + RFID_Status + '\'' +
                ", DCV_Status='" + DCV_Status + '\'' +
                ", ATG_Status='" + ATG_Status + '\'' +
                ", Location_Reading_1='" + Location_Reading_1 + '\'' +
                ", Location_Reading_2='" + Location_Reading_2 + '\'' +
                ", No_of_event_='" + No_of_event_ + '\'' +
                ", Dispensed_in_='" + Dispensed_in_ + '\'' +
                ", Order_ID='" + Order_ID + '\'' +
                '}';
    }
}
