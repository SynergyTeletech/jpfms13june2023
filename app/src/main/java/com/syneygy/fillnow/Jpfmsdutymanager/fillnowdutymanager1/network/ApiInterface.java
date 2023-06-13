package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.ChangePassword;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AcceptedOrdersResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Arrival;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AssetStatusModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AssetfuelingStatus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.CheckAvailability;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.FranchiseInfoRec;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.GetCurrentAssetLatlong;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OfflineOrderDetailData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OrderStaus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PaymentMode;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PostOrderDelivered;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.RegularDispenseVehicleResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.RejectOrder;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.RequestOptModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Root;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.SaveRatingModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ViewProfile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.getFuelingState;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.sendVol;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("dmLogin")
    Call<LoginResponse> getLogin(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("notifyJourney")
    Call<OrderStaus> getNotify(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("notifyArrival")
    Call<Arrival> getNotifyArrival(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("notifyComplete")
    Call<Arrival> getNotifyComplete(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("orderDelivered")
    Call<PostOrderDelivered> postOrderDelivered(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("offlineOrderDelivered")
    Call<LoginResponse> postFreshDispenseOrderDelivered(@FieldMap HashMap<String, Object> hashMap);
    @FormUrlEncoded
    @POST("synergy_api.php")
    Call<ResponseBody> payNow(@FieldMap HashMap<String, String> hashMap);
    @FormUrlEncoded
    @POST("orderList")
    Call<Root> getOrder(@Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId, @Field(value = AppConstants.Status, encoded = true) String status);
    @FormUrlEncoded
    @POST("orderAddSpiner")
    Call<AcceptedOrdersResponse> getConfirmOrder(@Field(value = AppConstants.Vehicle_id, encoded = true) String VehicleId);
    @FormUrlEncoded
    @POST("orderCompleteDelivered")
    Call<LoginResponse> orderComplete(@Field(value = AppConstants.transaction_id, encoded = true) String transactinId, @Field(value = "user_id", encoded = true) String vehicleId);
    @FormUrlEncoded
    @POST("getCustomerProfile")
    Call<ViewProfile> getViewProfile(@Field(value = AppConstants.Location_Id, encoded = true) String locationId);
    @FormUrlEncoded
    @POST("orderStatus")
    Call<CheckAvailability> sendOrderList(@Field(value = AppConstants.transaction_id, encoded = true) String transactionId, @Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId, @Field(value = AppConstants.Duty_Id, encoded = true) String duty_id);
    @FormUrlEncoded
    @POST("deliveryInProgress")
    Call<DeliveryInProgress> getOrderDetail(@Field(value = AppConstants.transaction_id, encoded = true) String transactionId, @Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId);
    @FormUrlEncoded
    @POST("verifyFranchise")
    Call<LoginResponse> validateFranchiseCode(@Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId, @Field(value = AppConstants.franchise_id, encoded = true) String franchise_code);
    @FormUrlEncoded
    @POST("getLocationAndVehicleDetails")
    Call<RegularDispenseVehicleResponse> getFreshDispenseLocationData(@Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId, @Field(value = AppConstants.location, encoded = true) String locationId);
    @FormUrlEncoded
    @POST("orderOtp")
    Call<RequestOptModel> requestForOtp(@Field(value = "order_id", encoded = true) String orderId);
    @FormUrlEncoded
    @POST("verifyorderotp")
    Call<RequestOptModel> verify(@Field(value = "otp", encoded = true) String otp);
    @FormUrlEncoded
    @POST("GetFranchiseInfo")
    Call<FranchiseInfoRec> getFranchiseDetail(@Field(value = AppConstants.franchise_id, encoded = true) String franchise_id, @Field(value = AppConstants.Vehicle_id, encoded = true) String Vehicle_id);

//    @FormUrlEncoded
//    @POST("deliveryInProgress")
//    Call<ResponseBody> postOrderDelivered(
//            @Field(value = AppConstants.fueling_start_time, encoded = true) String fueling_start_time,
//            @Field(value = AppConstants.fueling_stop_time, encoded = true) String fueling_stop_time,
//            @Field(value = AppConstants.assets_id, encoded = true) String assets_id,
//            @Field(value = AppConstants.dispense_qty, encoded = true) String dispense_qty,
//            @Field(value = AppConstants.meter_reading, encoded = true) String meter_reading,
//            @Field(value = AppConstants.asset_other_reading, encoded = true) String asset_other_reading,
//            @Field(value = AppConstants.atg_tank_start_reading, encoded = true) String atg_tank_start_reading,
//            @Field(value = AppConstants.atg_tank_end_reading, encoded = true) String atg_tank_end_reading,
//            @Field(value = AppConstants.volume_totalizer, encoded = true) String volume_totalizer,
//            @Field(value = AppConstants.no_of_event_start_stop, encoded = true) String no_of_event_start_stop,
//            @Field(value = AppConstants.dispensed_in, encoded = true) String dispensed_in,
//            @Field(value = AppConstants.rfid_status, encoded = true) String rfid_status,
//            @Field(value = AppConstants.transaction_type, encoded = true) String transaction_type,
//            @Field(value = AppConstants.terminal_id, encoded = true) String terminal_id,
//            @Field(value = AppConstants.batch_no, encoded = true) String batch_no,
//            @Field(value = AppConstants.latitude, encoded = true) String latitude,
//            @Field(value = AppConstants.longitude, encoded = true) String longitude,
//            @Field(value = AppConstants.gps_status, encoded = true) String gps_status,
//            @Field(value = AppConstants.dcv_status, encoded = true) String dcv_status,
//            @Field(value = AppConstants.location_reading_1, encoded = true) String location_reading_1,
//            @Field(value = AppConstants.location_reading_2, encoded = true) String location_reading_2,
//            @Field(value = AppConstants.flag, encoded = true) String flag
//
//    );

    //getOffline order detail
    @FormUrlEncoded
    @POST("offlinedeliveryInProgress")
    Call<OfflineOrderDetailData> getOfflineData(
            @Field("transaction_id") String tra

    );

    // reject Order
    @FormUrlEncoded
    @POST("rejectorder")
    Call<RejectOrder> rejectOrder(@Field("transaction_id") String string);

    //create order
    @FormUrlEncoded
    @POST("createorder")
    Call<RejectOrder> createOrder(
            @Field("terminal_id") String string,
            @Field("location_id") String location_id,
            @Field("qty") String qty
    );

    @FormUrlEncoded
    @POST("changePassword")
    Call<ChangePasswordbean> changePaswordApi(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("change_asset_status")
    Call<ChangePasswordbean> change_asset_status(@Field("asset_id") String asset_id,
                                                 @Field("flag") int flag);


    @FormUrlEncoded
    @POST("forgotPassword")
    Call<Mobiles> getForgot(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("changePasswordForForgotPassword")
    Call<ChangePassword> getForgotPassword(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("verifyOtp")
    Call<ForgotResponse> getVerfityForgotPassword(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("offlineOrderstatuschanged")
    Call<ChangePasswordbean> offlineOrderstatuschanged(@Field("transaction_id") String transaction_id);


    @FormUrlEncoded
    @POST("change_asset_status")
    Call<AssetStatusModel> changeAssetStatus(
            @Field("asset_id") String assetid,
            @Field("flag") String flag

    );

    //get Fueling Status added by kamal 22.8.2020
    @FormUrlEncoded
    @POST("updatetransactionstaus")
    Call<getFuelingState> getFUelinState(
            @Field("transaction_id") String transaction_id
    );

    //update Fueling Status added by kamal 22.8.2020
    @FormUrlEncoded
    @POST("inserttransactionstaus")
    Call<getFuelingState> UpdateFUelinState(
            @Field("transaction_id") String transaction_id
    );

    @FormUrlEncoded
    @POST("get_category_list")
    Call<PaymentMode> getCategoryList(@Field("cat_id") String catId);

    // getAssetLatLong

    @FormUrlEncoded
    @POST("get_cuurent_location")
    Call<GetCurrentAssetLatlong> getassetLatLong(@Field("asset_id") String asset_id,@Field("vehicle_id") String vehicleId);

    // Api recieve fuel
    @FormUrlEncoded
    @POST("fuelRecieved")
    Call<sendVol> fuelRecieved(
            @Field("vehicle_id") String vehicle_id,
            @Field("fueling_start_time") String fueling_start_time,
            @Field("fueling_stop_time") String fueling_stop_time,
            @Field("transaction_type") String transaction_type,
            @Field("product") String product,
            @Field("fuel_price") String fuel_price,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("transaction_no") String transaction_no,
            @Field("batch_no") String batch_no,
            @Field("mismatch") String mismatch,
            @Field("atg_initial") String atg_initial,
            @Field("atg_final") String atg_final,
            @Field("density") String density,
            @Field("gps_status") String gps_status,
            @Field("manhole_status") String manhole_status,
            @Field("dcv_status") String dcv_status,
            @Field("atg_status") String atg_status,
            @Field("total_order") String total_order,
            @Field("total_receiv") String total_receiv
    );


    // Save Rating
    @FormUrlEncoded
    @POST("saverating")
    Call<SaveRatingModel> saverating(
            @Field("rate") String rate,
            @Field("order_id") String order_id,
            @Field("given_by") String given_by,
            @Field("cust_id") String cust_id,
            @Field("duty_manager_id") String duty_manager_id


    );


    //sendLiveData
    @FormUrlEncoded
    @POST("vehicle_live_data")
    Call<SaveRatingModel> sendData(
            @Field("vehicle_id") String vehicle_id,
            @Field("val") String val,
            @Field("amount") String amount,
            @Field("status") String status,
            @Field("vehicle_status") String vehicle_status,
            @Field("asset_id") String asset_id

    );
    @FormUrlEncoded
    @POST("setAssetLiveFuelingStatus")
    Call<AssetfuelingStatus> sendAssetStatus(
            @Field("status") String status,
            @Field("asset_id") String asset_id

    );
}
