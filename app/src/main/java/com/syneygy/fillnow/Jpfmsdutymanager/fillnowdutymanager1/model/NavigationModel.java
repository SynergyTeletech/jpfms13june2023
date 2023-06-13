package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model;

public class NavigationModel {

    private int icon;
    private String name;

    public NavigationModel(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
