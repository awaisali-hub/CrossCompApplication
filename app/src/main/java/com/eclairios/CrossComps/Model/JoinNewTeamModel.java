package com.eclairios.CrossComps.Model;

public class JoinNewTeamModel {
    String TeamID,TeamName,ParentTeamID,teamStatus,teamType,SelectedTeamOpenType,teamCategory;
    String countryTeamID,stateTeamID,cityTeamID,postalCodeID,communityTeamID;

    public String getPostalCodeID() {
        return postalCodeID;
    }

    public void setPostalCodeID(String postalCodeID) {
        this.postalCodeID = postalCodeID;
    }

    public String getCountryTeamID() {
        return countryTeamID;
    }

    public void setCountryTeamID(String countryTeamID) {
        this.countryTeamID = countryTeamID;
    }

    public String getStateTeamID() {
        return stateTeamID;
    }

    public void setStateTeamID(String stateTeamID) {
        this.stateTeamID = stateTeamID;
    }

    public String getCityTeamID() {
        return cityTeamID;
    }

    public void setCityTeamID(String cityTeamID) {
        this.cityTeamID = cityTeamID;
    }

    public String getCommunityTeamID() {
        return communityTeamID;
    }

    public void setCommunityTeamID(String communityTeamID) {
        this.communityTeamID = communityTeamID;
    }

    public JoinNewTeamModel() {
    }

    public String getParentTeamID() {
        return ParentTeamID;
    }

    public void setParentTeamID(String parentTeamID) {
        ParentTeamID = parentTeamID;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public String getSelectedTeamOpenType() {
        return SelectedTeamOpenType;
    }

    public void setSelectedTeamOpenType(String selectedTeamOpenType) {
        SelectedTeamOpenType = selectedTeamOpenType;
    }

    public String getTeamCategory() {
        return teamCategory;
    }

    public void setTeamCategory(String teamCategory) {
        this.teamCategory = teamCategory;
    }

    public String getTeamID() {
        return TeamID;
    }

    public void setTeamID(String teamID) {
        TeamID = teamID;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    @Override
    public String toString() {
        return TeamName;
    }
}
