package com.eclairios.CrossComps.Model;

public class ModelParticipentTeams {
    String teamName;
    String ActiveUserTeamOrAllTeams;
    String teamID;


    public ModelParticipentTeams() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getActiveUserTeamOrAllTeams() {
        return ActiveUserTeamOrAllTeams;
    }

    public void setActiveUserTeamOrAllTeams(String activeUserTeamOrAllTeams) {
        ActiveUserTeamOrAllTeams = activeUserTeamOrAllTeams;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
}
