package com.eclairios.CrossComps.Model;

public class MyCrossCompAllTeamsMainModel {

    private String teamID,teamName,teamStatus,teamType,SelectedTeamOpenType,teamCategory;

    public String getTeamCategory() {
        return teamCategory;
    }

    public void setTeamCategory(String teamCategory) {
        this.teamCategory = teamCategory;
    }

    public String getSelectedTeamOpenType() {
        return SelectedTeamOpenType;
    }

    public void setSelectedTeamOpenType(String selectedTeamOpenType) {
        SelectedTeamOpenType = selectedTeamOpenType;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public MyCrossCompAllTeamsMainModel() {
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
    }
}
