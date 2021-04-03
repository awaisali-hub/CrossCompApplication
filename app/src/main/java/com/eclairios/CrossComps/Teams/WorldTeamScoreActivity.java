package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.R;

public class WorldTeamScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_team_score);
        getSupportActionBar().hide();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WorldTeamScoreActivity.this, Dashboard.class);
        intent.putExtra("fragmentNumber",5); //for example
        startActivity(intent);
    }
}