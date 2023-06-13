package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

public class FineteckCalculation extends VolumeCalculation{
    @Override
    public float response(String values) {
        if(values.contains("0103")) {
            String respone1 = values.substring(6, 8);
            String respone2 = values.substring(8, 10);
            String respone3 = values.substring(10, 12);
            String respone4 = values.substring(12, 14);
            float myFloat = Float.intBitsToFloat((int) Long.parseLong(respone3 + respone4 + respone1 + respone2, 16));
            float tank1AtgReading = Float.parseFloat(String.valueOf(myFloat).substring(0, String.valueOf(myFloat).indexOf(".") + 2));
            return tank1AtgReading;
        }
        return 0.0f;
    }

    public  String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public  byte[] stringToBytesASCII(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) str.charAt(i);
        }
        return b;
    }

}
