package com.eclairios.CrossComps.Model;

public class MapModel {
    Integer Latitude,Longitude,IconResID;
    String Title;

    public MapModel() {
    }

    public Integer getLatitude() {
        return Latitude;
    }

    public void setLatitude(Integer latitude) {
        Latitude = latitude;
    }

    public Integer getLongitude() {
        return Longitude;
    }

    public void setLongitude(Integer longitude) {
        Longitude = longitude;
    }

    public Integer getIconResID() {
        return IconResID;
    }

    public void setIconResID(Integer iconResID) {
        IconResID = iconResID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
