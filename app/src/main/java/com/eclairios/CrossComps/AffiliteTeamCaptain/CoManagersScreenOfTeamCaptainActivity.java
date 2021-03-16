package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoManagersScreenOfTeamCaptainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_managers_screen_of_team_captain);
        getSupportActionBar().hide();
    }

    public void MembersScreenOfCoCaptains(View view) {
        startActivity(new Intent(CoManagersScreenOfTeamCaptainActivity.this,MembersScreenOfCoCaptainsActivity.class));
    }
}