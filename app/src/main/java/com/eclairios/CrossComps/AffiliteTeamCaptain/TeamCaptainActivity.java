package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class TeamCaptainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_captain);
        getSupportActionBar().hide();
    }

    public void MoveToCaptainTeamMember(View view) {
        startActivity(new Intent(TeamCaptainActivity.this, MembersScreenOfAffiliatesActivity.class));
    }

    public void MoveToCoCaptions(View view) {
        startActivity(new Intent(TeamCaptainActivity.this, CoCaptainsScreenOfAffiliatesActivity.class));
    }

    public void OpenCoManagersScreenOfTeamCaptain(View view) {
        startActivity(new Intent(TeamCaptainActivity.this, CoManagersScreenOfTeamCaptainActivity.class));
    }

    public void CategoryTeamAlreadyMember(View view) {
        startActivity(new Intent(TeamCaptainActivity.this, CategoryTeamAlreadyMemberActivity.class));
    }
}