package com.eclairios.CrossComps.Model;

public class JoinNewTeamModel {
    String TeamID,TeamName,ParentTeamID,teamStatus,teamType,SelectedTeamOpenType,teamCategory;

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
