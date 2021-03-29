package com.eclairios.CrossComps.Model;

public class NewServiceModel {
    private   String serviceName,day,startTime,endTime;



//    public NewServiceModel(String serviceName, String day, String startTime, String endTime) {
//        this.serviceName = serviceName;
//        this.day = day;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

    public NewServiceModel() {

    }


    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
