package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eclairios.CrossComps.R;

public class MembersScreenOfCoManagersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen_of_co_managers);
        getSupportActionBar().hide();
    }
}