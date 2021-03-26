package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainsAllTeamsActivity;
import com.eclairios.CrossComps.AffiliteTeamManager.HomeTeamActivity;
import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainActivity;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.Judge.JudgeHomePageParticipantRegistrationActivity;
import com.eclairios.CrossComps.MainFragments.ScoresFragment;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.ServiceCoordinatorHomePageActivity;

public class AffiliateDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_dashboard);
        getSupportActionBar().hide();
        try{
            WaitDialog.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("check_user_affiliate", 1);
        editor.apply();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AffiliateDashboardActivity.this, Dashboard.class));
    }

    public void HomeScore(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, Dashboard.class));
    }
}