package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

public class TokheiumCalculation  extends VolumeCalculation{
    @Override
    public float response(String value) {
        String responseAscii=value;
        String[] separated = responseAscii.split("=");
        try {
            float d = Float.parseFloat(separated[2]);
            return d;
        }
        catch (NumberFormatException e){
            return 0.0f;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 0.0f;
        }
    }
}
