package com.eclairios.CrossComps.Model;

public class StateModel {

    String state_id,state_name;

    public StateModel() {
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @Override
    public String toString() {
        return state_name;
    }
}
