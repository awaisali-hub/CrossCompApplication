package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class TeamCaptainsAllTeamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_captains_all_teams);
        getSupportActionBar().hide();
    }

    public void MoveToChurchTeam(View view) {
        startActivity(new Intent(TeamCaptainsAllTeamsActivity.this,TeamCaptainActivity.class));
    }

    public void MoveToChurchNewTeamOrJoin(View view) {
        startActivity(new Intent(TeamCaptainsAllTeamsActivity.this,MyChurchTeamJoinCreateActivity.class));
    }
}