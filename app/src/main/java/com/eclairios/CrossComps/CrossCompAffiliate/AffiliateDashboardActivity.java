package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.AffiliteTeamManager.TeamManagerActivity;
import com.eclairios.CrossComps.R;

public class AffiliateDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_dashboard);
        getSupportActionBar().hide();
    }

    public void MoveToTeamManager(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, TeamManagerActivity.class));
    }
}