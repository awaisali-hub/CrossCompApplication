package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class HomeTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_team);
        getSupportActionBar().hide();
    }

    public void MoveToCoManagers(View view) {
        startActivity(new Intent(HomeTeamActivity.this,CoManagersActivity.class));
    }

    public void MoveToHomeTeamMember(View view) {
        startActivity(new Intent(HomeTeamActivity.this,HomeTeamMemberActivity.class));
    }

    public void EditHomeTeam(View view) {
        startActivity(new Intent(HomeTeamActivity.this,EditYourHomeTeamActivity.class));
    }

    public void MembersScreenOfAffiliatesHomeActivity(View view) {
        startActivity(new Intent(HomeTeamActivity.this,MembersScreenOfAffiliatesHomeActivity.class));
    }

    public void MoveToCoManagerHome(View view) {
        startActivity(new Intent(HomeTeamActivity.this,CoManagerForNewHomeTeamActivity.class));
    }

    public void MembersScreenOfCoManagers(View view) {
        startActivity(new Intent(HomeTeamActivity.this,MembersScreenOfCoManagersActivity.class));
    }

    public void MembersScreenOfTeamManager(View view) {
        startActivity(new Intent(HomeTeamActivity.this,MembersScreenOfTeamManagerActivity.class));
    }
}