package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;

import java.util.ArrayList;
import java.util.List;

public class DispenseLocalDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DUTY_MANAGER_LOCAL_DATABASE";

    public static final String TABLE_ORDER_DISPENSE_LOCAL_DATA = "TABLE_ORDER_DISPENSE_LOCAL_DATA";
    public static final String TABLE_FRESH_DISPENSE_LOCAL_DATA = "TABLE_FRESH_DISPENSE_LOCAL_DATA";
    public static final String TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA = "TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA";

    public static final String COLUMN_ORDER_LOCAL_KEY_ID = "order_local_key_id";
    public static final String COLUMN_TRANSACTION_ID = "transaction_id";
    public static final String COLUMN_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_FLAG = "flag";
    public static final String COLUMN_ASSET_ID = "asset_id";
    public static final String COLUMN_DISPENSE_QTY = "dispense_qty";
    public static final String COLUMN_UNIT_PRICE = "unit_price";
    public static final String COLUMN_TRANSACTION_TYPE = "transaction_type";
    public static final String COLUMN_TRANSACTION_NO = "transaction_no";
    public static final String COLUMN_FUELING_START_TIME = "fueling_start_time";
    public static final String COLUMN_FUELING_STOP_TIME = "fueling_stop_time";
    public static final String COLUMN_METER_READING = "meter_reading";
    public static final String COLUMN_ASSET_OTHER_READING = "asset_other_reading";
    public static final String COLUMN_ASSET_OTHER_READING_2 = "asset_other_reading_2";
    public static final String COLUMN_ATG_TANK_START_READING = "atg_tank_start_reading";
    public static final String COLUMN_ATG_TANK_END_READING = "atg_tank_end_reading";
    public static final String COLUMN_TERMINAL_ID = "terminal_id";
    public static final String COLUMN_BATCH_NO= "batch_no";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LOCATION_READING_1 = "location_reading_1";
    public static final String COLUMN_LOCATION_READING_2 = "location_reading_2";
    public static final String COLUMN_GPS_STATUS = "gps_status";
    public static final String COLUMN_DCV_STATUS = "dcv_status";
    public static final String COLUMN_RFID_STATUS = "rfid_status";
    public static final String COLUMN_DISPENSE_IN = "dispense_in";
    public static final String COLUMN_NO_OF_EVENT_START_STOP = "no_of_event_start_stop";
    public static final String COLUMN_VOLUME_TOTALIZER = "volume_totalizer";
    public static final String COLUMN_TOTAL_QTY = "total_qty";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_DUTY_ID = "duty_id";
    public static final String COLUMN_LOCATION_ID = "location_id";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_FOOTER_MESSAGE = "footer_message";
    public static final String COLUMN_GST_PERCENTAGE = "gst_percentage";
    public static final String STATUSS = "status";
    public static  final String INTIAL_TOT="initial_volume_totalizer";

    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";

     //table name//
    public static final String ACCEPTED_ORDER="ACCEPTED_ORDER";
    public static final String PROGRESS_LIST="PROGRESS_ORDER";
    // table column
    public static final String ORDER_ID="order_id";
    public static final String LEAD_NO="lead_number";
    public static final String LOCATION_ID="location_id";
    public static final String ASSET_WISE="asset_wise";
    public static final String STATUS="staus";
    public static final String SLOT_NAME="slot_name";
    public static final String ORDER_DATE="order_date";
    public static final String QTY="qty";
    public static final String REGISTRATION_NO="registration_no";
    public static final String LOGIN_ID="login_id";
    public static final String ASSETFLAG="asset_flag";
    public static final String CREATEDDATETIME="created_datatime";
    public static final String LOCATIONNAME="location_name";
    public static final String ADDRESS="address";
    public static final String LATTITUDE="latitude";
    public static final String LONGITUDE="logitude";
    public static final String CONTACTPERSONNAME="contact_person_name";
    public static final String CONTACTPERSIONPHONE="contact_person_phone";
    public static final String FNAME="fname";
    public static final String TRANSACTION_ID="transaction_id";
    public static final String OFFLINE_OTP="offline_otp";
    public static final String  PERFORMA_INVOICE_NO="performa_invoice_no";
    public static final String BRANCH_ID="branch_id";
    public static final String ORDER_TYPE="order_type";
    public static final String FUEL="fuel";
    public static final String SLOT_ID="slot_id";
    public static final String DELIVERED_DATA="delivered_data";
    public static final String TICKET_ID="ticket_id";
    public static final String CUSTOMER_NAME="customer_name";
    public static final String TOTAL_DISPENCE_QTY="total_dispense_qty";
    public static final String UNIT_PRICE="unit_price";
    public static final String TOTAL_AMOUNT="total_amount";
    public static final String DISPENCE_LATITUDE="dispense_latitude";
    public static final String DISPENCE_LONGITUDE="dispense_longitude";
    public static final String GST_PERCENTAGE="gst_percentage";
    public static final String ASSET_OTHER_READING="asset_other_reading_2";
    public static final String LOCATION_READING="location_reading_1";
    public static final String ASSET_NAME="asset_name";
    public static final String ASSET_READING_START="atg_start_reading";
    public static final String ASSET_READING_END="atg_end_reading";


















    public DispenseLocalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ORDER_DISPENSE_LOCAL_DATA_TABLE = "CREATE TABLE " + TABLE_ORDER_DISPENSE_LOCAL_DATA + "("
                + COLUMN_ORDER_LOCAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TRANSACTION_ID + " TEXT,"
                + COLUMN_VEHICLE_ID + " TEXT,"
                + COLUMN_FLAG + " TEXT,"
                + COLUMN_ASSET_ID + " TEXT,"
                + COLUMN_DISPENSE_QTY + " TEXT,"
                + COLUMN_UNIT_PRICE + " TEXT,"
                + COLUMN_TRANSACTION_TYPE + " TEXT,"
                + COLUMN_TRANSACTION_NO + " TEXT,"
                + COLUMN_FUELING_START_TIME + " TEXT,"
                + COLUMN_FUELING_STOP_TIME + " TEXT,"
                + COLUMN_METER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING_2 + " TEXT,"
                + COLUMN_ATG_TANK_START_READING + " TEXT,"
                + COLUMN_ATG_TANK_END_READING + " TEXT,"
                + COLUMN_TERMINAL_ID + " TEXT,"
                + COLUMN_BATCH_NO + " TEXT,"
                + COLUMN_LATITUDE + " TEXT,"
                + COLUMN_LONGITUDE + " TEXT,"
                + COLUMN_LOCATION_READING_1 + " TEXT,"
                + COLUMN_LOCATION_READING_2 + " TEXT,"
                + COLUMN_GPS_STATUS + " TEXT,"
                + COLUMN_DCV_STATUS + " TEXT,"
                + COLUMN_RFID_STATUS + " TEXT,"
                + COLUMN_DISPENSE_IN + " TEXT,"
                + COLUMN_NO_OF_EVENT_START_STOP + " TEXT,"
                + COLUMN_VOLUME_TOTALIZER + " TEXT,"
                + COLUMN_TOTAL_QTY + " TEXT,"
                + COLUMN_ORDER_ID + " TEXT,"
                + COLUMN_DUTY_ID + " TEXT,"
                + COLUMN_LOCATION_ID + " TEXT,"
                + COLUMN_FOOTER_MESSAGE + " TEXT,"
                + COLUMN_GST_PERCENTAGE + " TEXT,"
                + INTIAL_TOT + " TEXT,"
                + STATUSS + " TEXT,"
                + COLUMN_TOTAL_AMOUNT + " TEXT,"
                + ASSET_READING_START + " TEXT,"
                + ASSET_READING_END + " TEXT,"
                + STATUSS + "TEXT" +
                ")";


        String CREATE_FRESH_DISPENSE_LOCAL_DATA_TABLE = "CREATE TABLE " + TABLE_FRESH_DISPENSE_LOCAL_DATA + "("
                + COLUMN_ORDER_LOCAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TRANSACTION_ID + " TEXT,"
                + COLUMN_VEHICLE_ID + " TEXT,"
                + COLUMN_FLAG + " TEXT,"
                + COLUMN_ASSET_ID + " TEXT,"
                + COLUMN_DISPENSE_QTY + " TEXT,"
                + COLUMN_UNIT_PRICE + " TEXT,"
                + COLUMN_TRANSACTION_TYPE + " TEXT,"
                + COLUMN_TRANSACTION_NO + " TEXT,"
                + COLUMN_FUELING_START_TIME + " TEXT,"
                + COLUMN_FUELING_STOP_TIME + " TEXT,"
                + COLUMN_METER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING_2 + " TEXT,"
                + COLUMN_ATG_TANK_START_READING + " TEXT,"
                + COLUMN_ATG_TANK_END_READING + " TEXT,"
                + COLUMN_TERMINAL_ID + " TEXT,"
                + COLUMN_BATCH_NO + " TEXT,"
                + COLUMN_LATITUDE + " TEXT,"
                + COLUMN_LONGITUDE + " TEXT,"
                + COLUMN_LOCATION_READING_1 + " TEXT,"
                + COLUMN_LOCATION_READING_2 + " TEXT,"
                + COLUMN_GPS_STATUS + " TEXT,"
                + COLUMN_DCV_STATUS + " TEXT,"
                + COLUMN_RFID_STATUS + " TEXT,"
                + COLUMN_DISPENSE_IN + " TEXT,"
                + COLUMN_NO_OF_EVENT_START_STOP + " TEXT,"
                + COLUMN_VOLUME_TOTALIZER + " TEXT,"
                + COLUMN_TOTAL_QTY + " TEXT,"
                + COLUMN_ORDER_ID + " TEXT,"
                + COLUMN_DUTY_ID + " TEXT,"
                + COLUMN_LOCATION_ID + " TEXT,"
                + COLUMN_FOOTER_MESSAGE + " TEXT,"
                + COLUMN_GST_PERCENTAGE + " TEXT,"
                + COLUMN_TOTAL_AMOUNT + " TEXT" + ")";

        String CREATE_GO_LOCAL_DISPENSE_LOCAL_DATA_TABLE = "CREATE TABLE " + TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA + "("
                + COLUMN_ORDER_LOCAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TRANSACTION_ID + " TEXT,"
                + COLUMN_VEHICLE_ID + " TEXT,"
                + COLUMN_FLAG + " TEXT,"
                + COLUMN_ASSET_ID + " TEXT,"
                + COLUMN_DISPENSE_QTY + " TEXT,"
                + COLUMN_UNIT_PRICE + " TEXT,"
                + COLUMN_TRANSACTION_TYPE + " TEXT,"
                + COLUMN_TRANSACTION_NO + " TEXT,"
                + COLUMN_FUELING_START_TIME + " TEXT,"
                + COLUMN_FUELING_STOP_TIME + " TEXT,"
                + COLUMN_METER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING + " TEXT,"
                + COLUMN_ASSET_OTHER_READING_2 + " TEXT,"
                + COLUMN_ATG_TANK_START_READING + " TEXT,"
                + COLUMN_ATG_TANK_END_READING + " TEXT,"
                + COLUMN_TERMINAL_ID + " TEXT,"
                + COLUMN_BATCH_NO + " TEXT,"
                + COLUMN_LATITUDE + " TEXT,"
                + COLUMN_LONGITUDE + " TEXT,"
                + COLUMN_LOCATION_READING_1 + " TEXT,"
                + COLUMN_LOCATION_READING_2 + " TEXT,"
                + COLUMN_GPS_STATUS + " TEXT,"
                + COLUMN_DCV_STATUS + " TEXT,"
                + COLUMN_RFID_STATUS + " TEXT,"
                + COLUMN_DISPENSE_IN + " TEXT,"
                + COLUMN_NO_OF_EVENT_START_STOP + " TEXT,"
                + COLUMN_VOLUME_TOTALIZER + " TEXT,"
                + COLUMN_TOTAL_QTY + " TEXT,"
                + COLUMN_ORDER_ID + " TEXT,"
                + COLUMN_DUTY_ID + " TEXT,"
                + COLUMN_LOCATION_ID + " TEXT,"
                + COLUMN_FOOTER_MESSAGE + " TEXT,"
                + COLUMN_GST_PERCENTAGE + " TEXT,"
                + COLUMN_TOTAL_AMOUNT + " TEXT" + ")";

        String CREATE_ACCEPTED_ORDER = "CREATE TABLE " + ACCEPTED_ORDER + "("
                + COLUMN_ORDER_LOCAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ORDER_ID + " TEXT,"
                + LEAD_NO + " TEXT,"
                + LOCATION_ID + " TEXT,"
                + ASSET_WISE + " TEXT,"
                + STATUS + " TEXT,"
                + SLOT_NAME + " TEXT,"
                + ORDER_DATE + " TEXT,"
                + QTY + " TEXT,"
                + REGISTRATION_NO + " TEXT,"
                + LOGIN_ID + " TEXT,"
                + ASSETFLAG + " TEXT,"
                + CREATEDDATETIME + " TEXT,"
                + LOCATIONNAME + " TEXT,"
                + ADDRESS + " TEXT,"
                + LATTITUDE + " TEXT,"
                + LONGITUDE + " TEXT,"
                + CONTACTPERSONNAME + " TEXT,"
                + CONTACTPERSIONPHONE + " TEXT,"
                + FNAME + " TEXT,"
                + TRANSACTION_ID + " TEXT,"
                + OFFLINE_OTP + " TEXT,"
                + PERFORMA_INVOICE_NO + " TEXT,"
                + BRANCH_ID + " TEXT,"
                + ORDER_TYPE + " TEXT,"
                + FUEL + " TEXT,"
                + SLOT_ID + " TEXT,"
                + DELIVERED_DATA + " TEXT,"
                + TICKET_ID + " TEXT,"
                + CUSTOMER_NAME + " TEXT,"
                + TOTAL_DISPENCE_QTY + " TEXT,"
                + UNIT_PRICE + " TEXT,"
                + TOTAL_AMOUNT + " TEXT,"
                + DISPENCE_LATITUDE + " TEXT,"
                + DISPENCE_LONGITUDE + " TEXT,"
                + GST_PERCENTAGE + " TEXT,"
                + ASSET_OTHER_READING + " TEXT,"
                + LOCATION_READING + " TEXT,"
                + ASSET_NAME + " TEXT" + ")";

        String CREATE_PROGRESS_ORDER = "CREATE TABLE " + PROGRESS_LIST + "("
                + COLUMN_ORDER_LOCAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ORDER_ID + " TEXT,"
                + LEAD_NO + " TEXT,"
                + LOCATION_ID + " TEXT,"
                + ASSET_WISE + " TEXT,"
                + STATUS + " TEXT,"
                + SLOT_NAME + " TEXT,"
                + ORDER_DATE + " TEXT,"
                + QTY + " TEXT,"
                + REGISTRATION_NO + " TEXT,"
                + LOGIN_ID + " TEXT,"
                + ASSETFLAG + " TEXT,"
                + CREATEDDATETIME + " TEXT,"
                + LOCATIONNAME + " TEXT,"
                + ADDRESS + " TEXT,"
                + LATTITUDE + " TEXT,"
                + LONGITUDE + " TEXT,"
                + CONTACTPERSONNAME + " TEXT,"
                + CONTACTPERSIONPHONE + " TEXT,"
                + FNAME + " TEXT,"
                + TRANSACTION_ID + " TEXT,"
                + OFFLINE_OTP + " TEXT,"
                + PERFORMA_INVOICE_NO + " TEXT,"
                + BRANCH_ID + " TEXT,"
                + ORDER_TYPE + " TEXT,"
                + FUEL + " TEXT,"
                + SLOT_ID + " TEXT,"
                + DELIVERED_DATA + " TEXT,"
                + TICKET_ID + " TEXT,"
                + CUSTOMER_NAME + " TEXT,"
                + TOTAL_DISPENCE_QTY + " TEXT,"
                + UNIT_PRICE + " TEXT,"
                + TOTAL_AMOUNT + " TEXT,"
                + DISPENCE_LATITUDE + " TEXT,"
                + DISPENCE_LONGITUDE + " TEXT,"
                + GST_PERCENTAGE + " TEXT,"
                + ASSET_OTHER_READING + " TEXT,"
                + LOCATION_READING + " TEXT,"
                + ASSET_NAME + " TEXT" + ")";



        db.execSQL(CREATE_ORDER_DISPENSE_LOCAL_DATA_TABLE);
        db.execSQL(CREATE_FRESH_DISPENSE_LOCAL_DATA_TABLE);
        db.execSQL(CREATE_GO_LOCAL_DISPENSE_LOCAL_DATA_TABLE);
        db.execSQL(CREATE_ACCEPTED_ORDER);
        db.execSQL(CREATE_PROGRESS_ORDER);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DISPENSE_LOCAL_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRESH_DISPENSE_LOCAL_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + PROGRESS_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + ACCEPTED_ORDER);
        // Create tables again
        onCreate(db);
    }





    public void addOrderDispenseData(OrderDispenseLocalData orderDispenseLocalData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_ID, orderDispenseLocalData.getTransaction_id());
        values.put(COLUMN_VEHICLE_ID, orderDispenseLocalData.getVehicle_id());
        values.put(COLUMN_FLAG, orderDispenseLocalData.getFlag());
        values.put(COLUMN_ASSET_ID, orderDispenseLocalData.getAssets_id());
        values.put(COLUMN_DISPENSE_QTY, orderDispenseLocalData.getDispense_qty());
        values.put(COLUMN_UNIT_PRICE, orderDispenseLocalData.getUnit_price());
        values.put(COLUMN_TRANSACTION_TYPE, orderDispenseLocalData.getTransaction_type());
        values.put(COLUMN_TRANSACTION_NO, orderDispenseLocalData.getTransaction_no());
        values.put(COLUMN_FUELING_START_TIME, orderDispenseLocalData.getFueling_start_time());
        values.put(COLUMN_FUELING_STOP_TIME, orderDispenseLocalData.getFueling_stop_time());
        values.put(COLUMN_METER_READING, orderDispenseLocalData.getMeter_reading());
        values.put(COLUMN_ASSET_OTHER_READING, orderDispenseLocalData.getAsset_other_reading());
        values.put(COLUMN_ASSET_OTHER_READING_2, orderDispenseLocalData.getAsset_other_reading_2());
        values.put(COLUMN_ATG_TANK_START_READING, orderDispenseLocalData.getAtg_tank_start_reading());
        values.put(COLUMN_ATG_TANK_END_READING, orderDispenseLocalData.getAtg_tank_end_reading());
        values.put(COLUMN_TERMINAL_ID, orderDispenseLocalData.getTerminal_id());
        values.put(COLUMN_BATCH_NO, orderDispenseLocalData.getBatch_no());
        values.put(COLUMN_LATITUDE, orderDispenseLocalData.getLatitude());
        values.put(COLUMN_LONGITUDE, orderDispenseLocalData.getLongitude());
        values.put(COLUMN_LOCATION_READING_1, orderDispenseLocalData.getLocation_reading_1());
        values.put(COLUMN_LOCATION_READING_2, orderDispenseLocalData.getLocation_reading_2());
        values.put(COLUMN_GPS_STATUS, orderDispenseLocalData.getGps_status());
        values.put(COLUMN_DCV_STATUS, orderDispenseLocalData.getDcv_status());
        values.put(COLUMN_RFID_STATUS, orderDispenseLocalData.getRfid_status());
        values.put(COLUMN_DISPENSE_IN, orderDispenseLocalData.getDispensed_in());
        values.put(COLUMN_NO_OF_EVENT_START_STOP, orderDispenseLocalData.getNo_of_event_start_stop());
        values.put(COLUMN_VOLUME_TOTALIZER, orderDispenseLocalData.getVolume_totalizer());
        values.put(COLUMN_TOTAL_QTY, orderDispenseLocalData.getTotal_qty());
        values.put(COLUMN_ORDER_ID, orderDispenseLocalData.getOrder_id());
        values.put(COLUMN_DUTY_ID, orderDispenseLocalData.getDuty_id());
        values.put(COLUMN_LOCATION_ID, orderDispenseLocalData.getLocation_id());
        values.put(COLUMN_FOOTER_MESSAGE,orderDispenseLocalData.getFooter_message());
        values.put(COLUMN_GST_PERCENTAGE,orderDispenseLocalData.getGst_percentage());
        values.put(INTIAL_TOT,orderDispenseLocalData.getInitial_volume_totalizer());
        values.put(STATUSS,orderDispenseLocalData.getStatus());
        values.put(COLUMN_TOTAL_AMOUNT,orderDispenseLocalData.getTotal_amount());
        values.put(ASSET_READING_START,orderDispenseLocalData.getElock_start_reading());
        values.put(ASSET_READING_END,orderDispenseLocalData.getElock_end_reading());
        values.put(STATUSS,orderDispenseLocalData.getStatus());
        try {
            long dataInsertQeryResponse = db.insert(TABLE_ORDER_DISPENSE_LOCAL_DATA, null, values);
            Log.d("PravinDb" ,orderDispenseLocalData.toString()+","+dataInsertQeryResponse);
        }
        catch (Exception e){
            Log.d("PravinDb" ,orderDispenseLocalData+","+e.getLocalizedMessage());
        }

        db.close();
    }
    public void addAcceptedOrder(List<Order> order) {

        SQLiteDatabase db = this.getWritableDatabase();
        for(Order order1:order) {

            ContentValues values = new ContentValues();
            values.put(ORDER_ID, order1.getOrder_id());
            values.put(LEAD_NO, order1.getLead_number());
            values.put(LOCATION_ID, order1.getLocation_id());
            values.put(ASSET_WISE, order1.getAsset_wise());
            values.put(STATUS, order1.getStaus());
            values.put(SLOT_NAME, order1.getSlotname());
            values.put(ORDER_DATE, order1.getOrderdate());
            values.put(QTY, order1.getQuantity());
            values.put(REGISTRATION_NO, order1.getRegistration_no());
            values.put(LOGIN_ID, order1.getLogin_id());
            values.put(ASSETFLAG, order1.isAsset_flag()?"1":"0");
            values.put(CREATEDDATETIME, order1.getCreated_datatime());
            values.put(LOCATIONNAME, order1.getLocation_name());
            values.put(ADDRESS, order1.getAddress());
            values.put(LATTITUDE, order1.getLatitude());
            values.put(LONGITUDE, order1.getLongitude());
            values.put(CONTACTPERSONNAME, order1.getOrder_contact_person_name());
            values.put(CONTACTPERSIONPHONE, order1.getContact_person_phone());
            values.put(FNAME, order1.getFname());
            values.put(TRANSACTION_ID, order1.getTransaction_id());
            values.put(OFFLINE_OTP, order1.getOffline_otp());
            values.put(PERFORMA_INVOICE_NO, order1.getPerformaId());
            values.put(BRANCH_ID, order1.getBranchID());
            values.put(ORDER_TYPE, order1.getOrderType());
            values.put(FUEL, order1.getFuel());
            values.put(SLOT_ID, order1.getSlotId());
            values.put(DELIVERED_DATA, order1.getDelivered_data());
            values.put(TICKET_ID, order1.getTicket_id());
            values.put(CUSTOMER_NAME, order1.getCustomer_name());
            values.put(TOTAL_DISPENCE_QTY, order1.getTotal_dispense_qty());
            values.put(UNIT_PRICE, order1.getUnit_price());
            values.put(TOTAL_AMOUNT, order1.getTotal_amount());
            values.put(DISPENCE_LATITUDE, order1.getLatitude());
            values.put(DISPENCE_LONGITUDE, order1.getLongitude());
            values.put(GST_PERCENTAGE, order1.getGst_percentage());
            values.put(ASSET_OTHER_READING, order1.getAsset_other_reading_2());
            values.put(LOCATION_READING, order1.getLocation_reading_1());
            values.put(ASSET_NAME, order1.getAsset_name());
            long dataInsertQeryResponse = db.insert(ACCEPTED_ORDER, null, values);
        }

        db.close();
    }
    public void updateOfflineData(String transactionid,String delivered){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try{
            ContentValues cv = new ContentValues();
            cv.put(DELIVERED_DATA,delivered);
            id= db.update(PROGRESS_LIST, cv, TRANSACTION_ID + "= ?", new String[]{transactionid});
        }
        catch (Exception e){
            db.close();
            e.printStackTrace();
        }
        db.close();


    }
    public void updateAcceptedOrder(String transactionid,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try{
            ContentValues cv = new ContentValues();
            cv.put(STATUS,status);
            id= db.update(ACCEPTED_ORDER, cv, TRANSACTION_ID + "= ?", new String[]{transactionid});


        }
        catch (Exception e){
            db.close();
            e.printStackTrace();

        }
        db.close();


    }
    public String getQuantity(String transactionid){
        String qty="";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM PROGRESS_ORDER WHERE transaction_id=" + transactionid;

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
                   int column_index=cursor.getColumnIndex(DELIVERED_DATA);
                    if(column_index>=0) {
                        qty = cursor.getString(column_index);
                    }



        }
        db.close();
        return qty;

    }

    public void addProgressOrder(List<Order> order) {

        SQLiteDatabase db = this.getWritableDatabase();
        for(Order order1:order) {

            ContentValues values = new ContentValues();
            values.put(ORDER_ID, order1.getOrder_id());
            values.put(LEAD_NO, order1.getLead_number());
            values.put(LOCATION_ID, order1.getLocation_id());
            values.put(ASSET_WISE, order1.getAsset_wise());
            values.put(STATUS, order1.getStaus());
            values.put(SLOT_NAME, order1.getSlotname());
            values.put(ORDER_DATE, order1.getOrderdate());
            values.put(QTY, order1.getQuantity());
            values.put(REGISTRATION_NO, order1.getRegistration_no());
            values.put(LOGIN_ID, order1.getLogin_id());
            values.put(ASSETFLAG, order1.isAsset_flag()?"1":"0");
            values.put(CREATEDDATETIME, order1.getCreated_datatime());
            values.put(LOCATIONNAME, order1.getLocation_name());
            values.put(ADDRESS, order1.getAddress());
            values.put(LATTITUDE, order1.getLatitude());
            values.put(LONGITUDE, order1.getLongitude());
            values.put(CONTACTPERSONNAME, order1.getOrder_contact_person_name());
            values.put(CONTACTPERSIONPHONE, order1.getContact_person_phone());
            values.put(FNAME, order1.getFname());
            values.put(TRANSACTION_ID, order1.getTransaction_id());
            values.put(OFFLINE_OTP, order1.getOffline_otp());
            values.put(PERFORMA_INVOICE_NO, order1.getPerformaId());
            values.put(BRANCH_ID, order1.getBranchID());
            values.put(ORDER_TYPE, order1.getOrderType());
            values.put(FUEL, order1.getFuel());
            values.put(SLOT_ID, order1.getSlotId());
            values.put(DELIVERED_DATA, order1.getDelivered_data());
            values.put(TICKET_ID, order1.getTicket_id());
            values.put(CUSTOMER_NAME, order1.getCustomer_name());
            values.put(TOTAL_DISPENCE_QTY, order1.getTotal_dispense_qty());
            values.put(UNIT_PRICE, order1.getUnit_price());
            values.put(TOTAL_AMOUNT, order1.getTotal_amount());
            values.put(DISPENCE_LATITUDE, order1.getLatitude());
            values.put(DISPENCE_LONGITUDE, order1.getLongitude());
            values.put(GST_PERCENTAGE, order1.getGst_percentage());
            values.put(ASSET_OTHER_READING, order1.getAsset_other_reading_2());
            values.put(LOCATION_READING, order1.getLocation_reading_1());
            values.put(ASSET_NAME, order1.getAsset_name());
            long dataInsertQeryResponse = db.insert(PROGRESS_LIST, null, values);
        }

        db.close();
    }

    public void deleteAcceptedOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCEPTED_ORDER, null, null);
       db.close();
    }
    public void deleteProgressOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROGRESS_LIST, null, null);
        db.close();
    }

   public ArrayList<Order> getAcceptedOrder(){
       ArrayList<Order> orderList = new ArrayList<Order>();
       String selectQuery = "SELECT  * FROM " + ACCEPTED_ORDER ;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
       if(cursor!=null && cursor.getCount()>0) {
           if (cursor.moveToFirst()) {
               do {
                   Order order = new Order();
                   order.setOrder_id(cursor.getString(1));
                   order.setLead_number(cursor.getString(2));
                   order.setLocation_id(cursor.getString(3));
                   order.setAsset_wise(cursor.getString(4));
                   order.setStaus(cursor.getString(5));

                   order.setSlotname(cursor.getString(6));
                   order.setOrderdate(cursor.getString(7));
                   order.setQuantity(cursor.getString(8));
                   order.setRegistration_no(cursor.getString(9));
                   order.setLogin_id(cursor.getString(10));

                   order.setAsset_flag(cursor.getString(11) == "1" ? true : false);
                   order.setCreated_datatime(cursor.getString(12));
                   order.setLocation_name(cursor.getString(13));
                   order.setAddress(cursor.getString(14));
                   order.setLatitude(cursor.getString(15));
                   order.setLongitude(cursor.getString(16));
                   order.setOrder_contact_person_name(cursor.getString(17));
                   order.setContact_person_phone(cursor.getString(18));
                   order.setFname(cursor.getString(19));
                   order.setTransaction_id(cursor.getString(20));
                   order.setOffline_otp(cursor.getString(21));
                   order.setPerformaId(cursor.getString(22));
                   order.setBranchID(cursor.getString(23));
                   order.setOrderType(cursor.getString(24));
                   order.setFuel(cursor.getString(25));
                   order.setSlotId(cursor.getString(26));
                   order.setDelivered_data(cursor.getString(27));
                   order.setTicket_id(cursor.getString(28));
                   order.setCustomer_name(cursor.getString(29));
                   order.setTotal_dispense_qty(cursor.getString(30));
                   order.setUnit_price(cursor.getString(31));
                   order.setTotal_amount(cursor.getString(32));
                   order.setDispense_latitude(cursor.getString(33));
                   order.setDispense_longitude(cursor.getString(34));
                   order.setGst_percentage(cursor.getString(35));
                   order.setAsset_other_reading_2(cursor.getString(36));
                   order.setLocation_reading_1(cursor.getString(37));
                   order.setAsset_name(cursor.getString(38));

                   orderList.add(order);
               }
               while (cursor.moveToNext());
               cursor.close();
           }
       }

       return orderList;




   }
    public ArrayList<Order> getOfflineOrder(){
        ArrayList<Order> orderList = new ArrayList<Order>();
        String selectQuery = "SELECT  * FROM " + PROGRESS_LIST ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        Order order = new Order();
                        order.setOrder_id(cursor.getString(1));
                        order.setLead_number(cursor.getString(2));
                        order.setLocation_id(cursor.getString(3));
                        order.setAsset_wise(cursor.getString(4));
                        order.setStaus(cursor.getString(5));

                        order.setSlotname(cursor.getString(6));
                        order.setOrderdate(cursor.getString(7));
                        order.setQuantity(cursor.getString(8));
                        order.setRegistration_no(cursor.getString(9));
                        order.setLogin_id(cursor.getString(10));

                        order.setAsset_flag(cursor.getString(11) == "1" ? true : false);
                        order.setCreated_datatime(cursor.getString(12));
                        order.setLocation_name(cursor.getString(13));
                        order.setAddress(cursor.getString(14));
                        order.setLatitude(cursor.getString(15));
                        order.setLongitude(cursor.getString(16));
                        order.setOrder_contact_person_name(cursor.getString(17));
                        order.setContact_person_phone(cursor.getString(18));
                        order.setFname(cursor.getString(19));
                        order.setTransaction_id(cursor.getString(20));
                        order.setOffline_otp(cursor.getString(21));
                        order.setPerformaId(cursor.getString(22));
                        order.setBranchID(cursor.getString(23));
                        order.setOrderType(cursor.getString(24));
                        order.setFuel(cursor.getString(25));
                        order.setSlotId(cursor.getString(26));
                        order.setDelivered_data(cursor.getString(27));
                        order.setTicket_id(cursor.getString(28));
                        order.setCustomer_name(cursor.getString(29));
                        order.setTotal_dispense_qty(cursor.getString(30));
                        order.setUnit_price(cursor.getString(31));
                        order.setTotal_amount(cursor.getString(32));
                        order.setDispense_latitude(cursor.getString(33));
                        order.setDispense_longitude(cursor.getString(34));
                        order.setGst_percentage(cursor.getString(35));
                        order.setAsset_other_reading_2(cursor.getString(36));
                        order.setLocation_reading_1(cursor.getString(37));
                        order.setAsset_name(cursor.getString(38));
//                        order.setGst_percentage(cursor.getString(38));
//                        order.setAsset_other_reading_2(cursor.getString(39));
//                        order.setLocation_reading_1(cursor.getString(40));
                        orderList.add(order);
                    }
                    catch (Exception e){

                    }
                }
                while (cursor.moveToNext());
                cursor.close();
            }
        }
        else {

        }

        return orderList;




    }

    public ArrayList<OrderDispenseLocalData> getAllOrderDispenseDataList() {
        ArrayList<OrderDispenseLocalData> contactList = new ArrayList<OrderDispenseLocalData>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER_DISPENSE_LOCAL_DATA ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderDispenseLocalData contact = new OrderDispenseLocalData();
                contact.setOrderLocalID(cursor.getInt(0));
                contact.setTransaction_id(cursor.getString(1));
                contact.setVehicle_id(cursor.getString(2));
                contact.setFlag(cursor.getString(3));
                contact.setAssets_id(cursor.getString(4));
                contact.setDispense_qty(cursor.getString(5));
                contact.setUnit_price(cursor.getString(6));
                contact.setTransaction_type(cursor.getString(7));
                contact.setTransaction_no(cursor.getString(8));
                contact.setFueling_start_time(cursor.getString(9));
                contact.setFueling_stop_time(cursor.getString(10));
                contact.setMeter_reading(cursor.getString(11));
                contact.setAsset_other_reading(cursor.getString(12));
                contact.setAsset_other_reading_2(cursor.getString(13));
                contact.setAtg_tank_start_reading(cursor.getString(14));
                contact.setAtg_tank_end_reading(cursor.getString(15));
                contact.setTerminal_id(cursor.getString(16));
                contact.setBatch_no(cursor.getString(17));
                contact.setLatitude(cursor.getString(18));
                contact.setLongitude(cursor.getString(19));
                contact.setLocation_reading_1(cursor.getString(20));
                contact.setLocation_reading_2(cursor.getString(21));
                contact.setGps_status(cursor.getString(22));
                contact.setDcv_status(cursor.getString(23));
                contact.setRfid_status(cursor.getString(24));
                contact.setDispensed_in(cursor.getString(25));
                contact.setNo_of_event_start_stop(cursor.getString(26));
                contact.setVolume_totalizer(cursor.getString(27));
                contact.setTotal_qty(cursor.getString(28));
                contact.setOrder_id(cursor.getString(29));
                contact.setDuty_id(cursor.getString(30));
                contact.setLocation_id(cursor.getString(31));
                contact.setFooter_message(cursor.getString(32));
                contact.setGst_percentage(cursor.getString(33));
                contact.setInitial_volume_totalizer(cursor.getString(34));
                contact.setStatus(cursor.getString(35));

                contact.setTotal_amount(cursor.getString(36));
                contact.setElock_start_reading(cursor.getString(37));
                contact.setElock_end_reading(cursor.getString(38));
                Log.e("kamalget","1");
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
//        db.close();
        return contactList;
    }
    public void deleteAllOrderDispenseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_DISPENSE_LOCAL_DATA, null, null);
//        db.close();
    }
    public int deleteOrderDispenseDataEntry(int keyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteDataResult=db.delete(TABLE_ORDER_DISPENSE_LOCAL_DATA, COLUMN_ORDER_LOCAL_KEY_ID + "=" + keyId, null);
        db.close();
        return deleteDataResult;
    }



    //addFreshDispenseData
    public void addFreshDispenseData(OrderDispenseLocalData orderDispenseLocalData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ORDER_LOCAL_KEY_ID, orderDispenseLocalData.getOrderLocalID());
        values.put(COLUMN_TRANSACTION_ID, orderDispenseLocalData.getTransaction_id());
        values.put(COLUMN_VEHICLE_ID, orderDispenseLocalData.getVehicle_id());
        values.put(COLUMN_FLAG, orderDispenseLocalData.getFlag());
        values.put(COLUMN_ASSET_ID, orderDispenseLocalData.getAssets_id());
        values.put(COLUMN_DISPENSE_QTY, orderDispenseLocalData.getDispense_qty());
        values.put(COLUMN_UNIT_PRICE, orderDispenseLocalData.getUnit_price());
        values.put(COLUMN_TRANSACTION_TYPE, orderDispenseLocalData.getTransaction_type());
        values.put(COLUMN_TRANSACTION_NO, orderDispenseLocalData.getTransaction_no());
        values.put(COLUMN_FUELING_START_TIME, orderDispenseLocalData.getFueling_start_time());
        values.put(COLUMN_FUELING_STOP_TIME, orderDispenseLocalData.getFueling_stop_time());
        values.put(COLUMN_METER_READING, orderDispenseLocalData.getMeter_reading());
        values.put(COLUMN_ASSET_OTHER_READING, orderDispenseLocalData.getAsset_other_reading());
        values.put(COLUMN_ASSET_OTHER_READING_2, orderDispenseLocalData.getAsset_other_reading_2());
        values.put(COLUMN_ATG_TANK_START_READING, orderDispenseLocalData.getAtg_tank_start_reading());
        values.put(COLUMN_ATG_TANK_END_READING, orderDispenseLocalData.getAtg_tank_end_reading());
        values.put(COLUMN_TERMINAL_ID, orderDispenseLocalData.getTerminal_id());
        values.put(COLUMN_BATCH_NO, orderDispenseLocalData.getBatch_no());
        values.put(COLUMN_LATITUDE, orderDispenseLocalData.getLatitude());
        values.put(COLUMN_LONGITUDE, orderDispenseLocalData.getLongitude());
        values.put(COLUMN_LOCATION_READING_1, orderDispenseLocalData.getLocation_reading_1());
        values.put(COLUMN_LOCATION_READING_2, orderDispenseLocalData.getLocation_reading_2());
        values.put(COLUMN_GPS_STATUS, orderDispenseLocalData.getGps_status());
        values.put(COLUMN_DCV_STATUS, orderDispenseLocalData.getDcv_status());
        values.put(COLUMN_RFID_STATUS, orderDispenseLocalData.getRfid_status());
        values.put(COLUMN_DISPENSE_IN, orderDispenseLocalData.getDispensed_in());
        values.put(COLUMN_NO_OF_EVENT_START_STOP, orderDispenseLocalData.getNo_of_event_start_stop());
        values.put(COLUMN_VOLUME_TOTALIZER, orderDispenseLocalData.getVolume_totalizer());
        values.put(COLUMN_TOTAL_QTY, orderDispenseLocalData.getTotal_qty());
        values.put(COLUMN_ORDER_ID, orderDispenseLocalData.getOrder_id());
        values.put(COLUMN_DUTY_ID, orderDispenseLocalData.getDuty_id());
        values.put(COLUMN_LOCATION_ID, orderDispenseLocalData.getLocation_id());
        values.put(COLUMN_FOOTER_MESSAGE,orderDispenseLocalData.getFooter_message());
        values.put(COLUMN_GST_PERCENTAGE,orderDispenseLocalData.getGst_percentage());
        values.put(COLUMN_TOTAL_AMOUNT,orderDispenseLocalData.getTotal_amount());
        db.insert(TABLE_FRESH_DISPENSE_LOCAL_DATA, null, values);
        db.close();
    }
    public List<OrderDispenseLocalData> getAllFreshDispenseDataList() {
        List<OrderDispenseLocalData> contactList = new ArrayList<OrderDispenseLocalData>();
        String selectQuery = "SELECT  * FROM " + TABLE_FRESH_DISPENSE_LOCAL_DATA ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderDispenseLocalData contact = new OrderDispenseLocalData();
                contact.setOrderLocalID(cursor.getInt(0));
                contact.setTransaction_id(cursor.getString(1));
                contact.setVehicle_id(cursor.getString(2));
                contact.setFlag(cursor.getString(3));
                contact.setAssets_id(cursor.getString(4));
                contact.setDispense_qty(cursor.getString(5));
                contact.setUnit_price(cursor.getString(6));
                contact.setTransaction_type(cursor.getString(7));
                contact.setTransaction_no(cursor.getString(8));
                contact.setFueling_start_time(cursor.getString(9));
                contact.setFueling_stop_time(cursor.getString(10));
                contact.setMeter_reading(cursor.getString(11));
                contact.setAsset_other_reading(cursor.getString(12));
                contact.setAsset_other_reading_2(cursor.getString(13));
                contact.setAtg_tank_start_reading(cursor.getString(14));
                contact.setAtg_tank_end_reading(cursor.getString(15));
                contact.setTerminal_id(cursor.getString(16));
                contact.setBatch_no(cursor.getString(17));
                contact.setLatitude(cursor.getString(18));
                contact.setLongitude(cursor.getString(19));
                contact.setLocation_reading_1(cursor.getString(20));
                contact.setLocation_reading_2(cursor.getString(21));
                contact.setGps_status(cursor.getString(22));
                contact.setDcv_status(cursor.getString(23));
                contact.setRfid_status(cursor.getString(24));
                contact.setDispensed_in(cursor.getString(25));
                contact.setNo_of_event_start_stop(cursor.getString(26));
                contact.setVolume_totalizer(cursor.getString(27));
                contact.setTotal_qty(cursor.getString(28));
                contact.setOrder_id(cursor.getString(29));
                contact.setDuty_id(cursor.getString(30));
                contact.setLocation_id(cursor.getString(31));
                contact.setFooter_message(cursor.getString(32));
                contact.setGst_percentage(cursor.getString(33));
                contact.setTotal_amount(cursor.getString(34));
                contactList.add(contact);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contactList;
    }
    public void deleteAllFreshDispenseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRESH_DISPENSE_LOCAL_DATA, null, null);
    }
    public int deleteFreshDispenseDataEntry(int keyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FRESH_DISPENSE_LOCAL_DATA, COLUMN_ORDER_LOCAL_KEY_ID + "=" + keyId, null);
    }



    //addGoLocalDispenseData
    public void addGoLocalDispenseData(OrderDispenseLocalData orderDispenseLocalData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ORDER_LOCAL_KEY_ID, orderDispenseLocalData.getOrderLocalID());
        values.put(COLUMN_TRANSACTION_ID, orderDispenseLocalData.getTransaction_id());
        values.put(COLUMN_VEHICLE_ID, orderDispenseLocalData.getVehicle_id());
        values.put(COLUMN_FLAG, orderDispenseLocalData.getFlag());
        values.put(COLUMN_ASSET_ID, orderDispenseLocalData.getAssets_id());
        values.put(COLUMN_DISPENSE_QTY, orderDispenseLocalData.getDispense_qty());
        values.put(COLUMN_UNIT_PRICE, orderDispenseLocalData.getUnit_price());
        values.put(COLUMN_TRANSACTION_TYPE, orderDispenseLocalData.getTransaction_type());
        values.put(COLUMN_TRANSACTION_NO, orderDispenseLocalData.getTransaction_no());
        values.put(COLUMN_FUELING_START_TIME, orderDispenseLocalData.getFueling_start_time());
        values.put(COLUMN_FUELING_STOP_TIME, orderDispenseLocalData.getFueling_stop_time());
        values.put(COLUMN_METER_READING, orderDispenseLocalData.getMeter_reading());
        values.put(COLUMN_ASSET_OTHER_READING, orderDispenseLocalData.getAsset_other_reading());
        values.put(COLUMN_ASSET_OTHER_READING_2, orderDispenseLocalData.getAsset_other_reading_2());
        values.put(COLUMN_ATG_TANK_START_READING, orderDispenseLocalData.getAtg_tank_start_reading());
        values.put(COLUMN_ATG_TANK_END_READING, orderDispenseLocalData.getAtg_tank_end_reading());
        values.put(COLUMN_TERMINAL_ID, orderDispenseLocalData.getTerminal_id());
        values.put(COLUMN_BATCH_NO, orderDispenseLocalData.getBatch_no());
        values.put(COLUMN_LATITUDE, orderDispenseLocalData.getLatitude());
        values.put(COLUMN_LONGITUDE, orderDispenseLocalData.getLongitude());
        values.put(COLUMN_LOCATION_READING_1, orderDispenseLocalData.getLocation_reading_1());
        values.put(COLUMN_LOCATION_READING_2, orderDispenseLocalData.getLocation_reading_2());
        values.put(COLUMN_GPS_STATUS, orderDispenseLocalData.getGps_status());
        values.put(COLUMN_DCV_STATUS, orderDispenseLocalData.getDcv_status());
        values.put(COLUMN_RFID_STATUS, orderDispenseLocalData.getRfid_status());
        values.put(COLUMN_DISPENSE_IN, orderDispenseLocalData.getDispensed_in());
        values.put(COLUMN_NO_OF_EVENT_START_STOP, orderDispenseLocalData.getNo_of_event_start_stop());
        values.put(COLUMN_VOLUME_TOTALIZER, orderDispenseLocalData.getVolume_totalizer());
        values.put(COLUMN_TOTAL_QTY, orderDispenseLocalData.getTotal_qty());
        values.put(COLUMN_ORDER_ID, orderDispenseLocalData.getOrder_id());
        values.put(COLUMN_DUTY_ID, orderDispenseLocalData.getDuty_id());
        values.put(COLUMN_LOCATION_ID, orderDispenseLocalData.getLocation_id());
        values.put(COLUMN_FOOTER_MESSAGE,orderDispenseLocalData.getFooter_message());
        values.put(COLUMN_GST_PERCENTAGE,orderDispenseLocalData.getGst_percentage());
        values.put(COLUMN_TOTAL_AMOUNT,orderDispenseLocalData.getTotal_amount());
        db.insert(TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA, null, values);
       db.close();
    }
    public List<OrderDispenseLocalData> getAllGoLocalDispenseDataList() {
        List<OrderDispenseLocalData> contactList = new ArrayList<OrderDispenseLocalData>();
        String selectQuery = "SELECT  * FROM " + TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderDispenseLocalData contact = new OrderDispenseLocalData();
                contact.setOrderLocalID(cursor.getInt(0));
                contact.setTransaction_id(cursor.getString(1));
                contact.setVehicle_id(cursor.getString(2));
                contact.setFlag(cursor.getString(3));
                contact.setAssets_id(cursor.getString(4));
                contact.setDispense_qty(cursor.getString(5));
                contact.setUnit_price(cursor.getString(6));
                contact.setTransaction_type(cursor.getString(7));
                contact.setTransaction_no(cursor.getString(8));
                contact.setFueling_start_time(cursor.getString(9));
                contact.setFueling_stop_time(cursor.getString(10));
                contact.setMeter_reading(cursor.getString(11));
                contact.setAsset_other_reading(cursor.getString(12));
                contact.setAsset_other_reading_2(cursor.getString(13));
                contact.setAtg_tank_start_reading(cursor.getString(14));
                contact.setAtg_tank_end_reading(cursor.getString(15));
                contact.setTerminal_id(cursor.getString(16));
                contact.setBatch_no(cursor.getString(17));
                contact.setLatitude(cursor.getString(18));
                contact.setLongitude(cursor.getString(19));
                contact.setLocation_reading_1(cursor.getString(20));
                contact.setLocation_reading_2(cursor.getString(21));
                contact.setGps_status(cursor.getString(22));
                contact.setDcv_status(cursor.getString(23));
                contact.setRfid_status(cursor.getString(24));
                contact.setDispensed_in(cursor.getString(25));
                contact.setNo_of_event_start_stop(cursor.getString(26));
                contact.setVolume_totalizer(cursor.getString(27));
                contact.setTotal_qty(cursor.getString(28));
                contact.setOrder_id(cursor.getString(29));
                contact.setDuty_id(cursor.getString(30));
                contact.setLocation_id(cursor.getString(31));
                contact.setFooter_message(cursor.getString(32));
                contact.setGst_percentage(cursor.getString(33));
                contact.setTotal_amount(cursor.getString(34));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return contactList;
    }
    public void deleteAllGoLocalDispenseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA, null, null);
       db.close();
    }
    public int deleteGoLocalDispenseDataEntry(int keyId) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_GO_LOCAL_DISPENSE_LOCAL_DATA, COLUMN_ORDER_LOCAL_KEY_ID + "=" + keyId, null);
    }


    public void updateStatus(String transaction_no) {
        String[] args = new String[]{transaction_no};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STATUSS,"1");
        db.update(TABLE_ORDER_DISPENSE_LOCAL_DATA, values,  COLUMN_TRANSACTION_NO + " = ? ", args);


    }

    public void updateProgressOrder(String transactionid,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try{
            ContentValues cv = new ContentValues();
            cv.put(STATUS,status);
            id= db.update(PROGRESS_LIST, cv, TRANSACTION_ID + "= ?", new String[]{transactionid});


        }
        catch (Exception e){
            db.close();
            e.printStackTrace();

        }
        db.close();


    }

    public void deleteProgressOrderTransaction(String transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ PROGRESS_LIST + " WHERE " + TRANSACTION_ID + " = "+transaction+"");
        db.close();
    }
}
