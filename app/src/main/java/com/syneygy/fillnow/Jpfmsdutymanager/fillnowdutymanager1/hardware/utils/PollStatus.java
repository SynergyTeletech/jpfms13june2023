package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils;

import android.util.Log;

/**
 * Created by Ved Yadav on 1/31/2019.
 */
public class PollStatus {

    public static String
    getPollState(String response) {
        String op_code = getPollStatusBit(response);
        Log.d("Opcode", String.valueOf(op_code));
        switch (op_code) {
            case "30":
                Log.d("pumpState:", "STATE IDLE");
//                if (response.contains("014124307f")) {
//                    return "STATE IDLE:NOZZLE OFF HOOK";
//                } else {
//                    return "STATE IDLE:NOZZLE ON HOOK";
//                }
                return "STATE IDLE";
            case "31":
                Log.d("pumpState:", "CALL STATE");
                return "CALL STATE";
            case "32":
                Log.d("pumpState:", "PRESET READY STATE");
                return "PRESET READY STATE";
            case "33":
                return "FUELING STATE";
            case "34":
                return "PAYABLE STATE";
            case "35":
                return "SUSPENDED STATE";
            case "36":
                return "STOPPED STATE";
            case "38":
                return "IN-OPERATIVE STATE";
            case "39":
                return "AUTHORIZE STATE";
            case "3d":
                return "Suspend Started State";
            case "3e":
                return "Wait For Preset State";
            case "3b":
                return "STARTED STATE";

            default:
                return "";

        }


    }

    private static String getPollStatusBit(String hexResponse) {
        if (hexResponse != null && hexResponse.toLowerCase().contains("7f")) {
            Log.e("statusResponseKamal",hexResponse);
            try {
                Log.d("responseBit", hexResponse.substring(hexResponse.indexOf("7f") - 2, hexResponse.indexOf("7f")).trim());
                return hexResponse.substring(hexResponse.indexOf("7f") - 2, hexResponse.indexOf("7f")).trim();
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }


}
