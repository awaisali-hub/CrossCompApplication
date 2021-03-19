package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainsAllTeamsActivity;
import com.eclairios.CrossComps.AffiliteTeamManager.HomeTeamActivity;
import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainActivity;
import com.eclairios.CrossComps.Judge.JudgeHomePageParticipantRegistrationActivity;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.ServiceCoordinatorHomePageActivity;

public class AffiliateDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_dashboard);
        getSupportActionBar().hide();
    }

    public void MoveToTeamManager(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, HomeTeamActivity.class));
    }

    public void MoveToTeamCaptain(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, TeamCaptainsAllTeamsActivity.class));

    }

    public void ServiceCoordinator(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, ServiceCoordinatorHomePageActivity.class));
    }

    public void Judge(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, JudgeHomePageParticipantRegistrationActivity.class));
    }
}