package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Arrival {
    private boolean succ;
    private String type;
    private String public_msg;
    ArrayList<Object> _err_codes = new ArrayList<Object>();
    ArrayList<Object> data = new ArrayList<Object>();
    @SerializedName("Arrived")
    ArrayList<ArriveJourney> Arrived = new ArrayList<ArriveJourney>();


    // Getter Methods

    public ArrayList<ArriveJourney> getArrived() {
        return Arrived;
    }

    public void setArrived(ArrayList<ArriveJourney> arrived) {
        Arrived = arrived;
    }

    public boolean getSucc() {
        return succ;
    }

    public String getType() {
        return type;
    }

    public String getPublic_msg() {
        return public_msg;
    }

    // Setter Methods

    public void setSucc( boolean succ ) {
        this.succ = succ;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public void setPublic_msg( String public_msg ) {
        this.public_msg = public_msg;
    }



}
