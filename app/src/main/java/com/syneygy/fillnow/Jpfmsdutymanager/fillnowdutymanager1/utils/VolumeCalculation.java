package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.LinkedHashMap;

abstract public class VolumeCalculation {
    public LinkedHashMap<String, Object> atgLinkedHashMapTank;
    public float response;
    private static double tank1MaxVolume = 0f, tankMaxHeight = 0f;
    public abstract float response(String value);

    public   float getAtgState(float response, @NonNull String  data){
        try {
            float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
            String service = data;
            JSONObject jsonObject1 = null;
            float tank1AtgReading = response;
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
                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
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
            }
            return 0.0f;
        }
        catch (Exception e){
            return 0.0f;
        }
    }
}

