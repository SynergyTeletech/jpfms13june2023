package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

public class FuelingModel {
    double amount;
    double rate;
    double volume;
    int status=0;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
