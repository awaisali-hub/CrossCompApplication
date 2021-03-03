package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class IndividualTeamChallengesScreen1bActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_team_challenges_screen1b);
        getSupportActionBar().hide();
    }

    public void MoveToIndividualScore(View view) {
        startActivity(new Intent(IndividualTeamChallengesScreen1bActivity.this,IndividualTeamChallengesScreen1cActivity.class));
    }
}