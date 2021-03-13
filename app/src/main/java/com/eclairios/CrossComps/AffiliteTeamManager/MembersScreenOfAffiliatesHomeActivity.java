package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eclairios.CrossComps.R;

public class MembersScreenOfAffiliatesHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen_of_affiliates_home);
        getSupportActionBar().hide();
    }
}