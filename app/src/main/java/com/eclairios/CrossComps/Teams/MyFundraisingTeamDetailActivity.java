package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.AllTeamCategoryActivity;
import com.eclairios.CrossComps.R;

public class MyFundraisingTeamDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fundraising_team_detail);
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyFundraisingTeamDetailActivity.this, Dashboard.class);
        intent.putExtra("fragmentNumber",5); //for example
        startActivity(intent);    }
}