package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.AllTeamCategoryActivity;

public class MyChurchTeamJoinCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_church_team_join_create);
        getSupportActionBar().hide();
    }

    public void MoveToChurchJoin(View view) {
        startActivity(new Intent(MyChurchTeamJoinCreateActivity.this, AllTeamCategoryActivity.class));
    }

    public void ChainOfTeamsChurch(View view) {
        startActivity(new Intent(MyChurchTeamJoinCreateActivity.this, TeamChainScreenChurchActivity.class));
    }
}