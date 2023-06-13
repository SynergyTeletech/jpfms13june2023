package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import org.json.JSONObject;

import java.util.LinkedHashMap;

public class AtgVolumeCalculation extends ViewModel {

    public static LinkedHashMap<String, Object> atgLinkedHashMapTank;
    private static double tank1MaxVolume = 0f, tankMaxHeight = 0f, totalAvailableVolume = 0f;


    public static float getAtgState(int tank1AtgReading) {
        try {


            float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
            String service = SharedPref.getLoginResponse().getVehicle_data().get(0).getAtgDipChart();
            JSONObject jsonObject1 = null;

            jsonObject1 = new JSONObject(service);
            atgLinkedHashMapTank = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);

            Object obj = atgLinkedHashMapTank.entrySet().toArray()[atgLinkedHashMapTank.size() - 1];
            LinkedHashMap.Entry linkedTreeMaps = (LinkedHashMap.Entry) (obj);
            JsonObject jsonObjects = (new Gson()).toJsonTree(linkedTreeMaps.getValue()).getAsJsonObject();
            if (!jsonObjects.get("A").getAsString().equalsIgnoreCase("ATG")) {
                tank1MaxVolume = Float.parseFloat(jsonObjects.get("B").getAsString());
                tankMaxHeight = Float.parseFloat(jsonObjects.get("A").getAsString());
            }


            for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank.entrySet()) {


                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();


                float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());

                if (tableAtgReading == tank1AtgReading) {
                    float tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());

                    return tank1Volume;


                } else if (tableAtgReading < tank1AtgReading) {
                    preAtgReading = tableAtgReading;
                    preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());

                } else if (tableAtgReading > tank1AtgReading) {
                    float prevAtgreading = preAtgReading;
                    float prevAtgVolume = preAtgVolume;
                    postAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
                    postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                    meanAtgVolume = (postAtgVolume - prevAtgVolume) / (postAtgReading - prevAtgreading) * (tank1AtgReading - prevAtgreading) + prevAtgVolume;

                    return meanAtgVolume;

                }


            }

        } catch (Exception e) {

        }
        return 0.0f;
    }

    private static float findHeight2(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }

    private static float findHeight1(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }

    private static float findHeight(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);


    }
    private static float findHeight3(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }
}