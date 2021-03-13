package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eclairios.CrossComps.R;

public class MembersScreenOfTeamManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen_of_team_manager);
        getSupportActionBar().hide();
    }
}