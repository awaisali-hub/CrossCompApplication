package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class IndividualTeamChallengesScreen1aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_team_challenges_screen1a);
        getSupportActionBar().hide();
    }

    public void MoveToChallenger(View view) {
        startActivity(new Intent(IndividualTeamChallengesScreen1aActivity.this,IndividualTeamChallengesScreen1bActivity.class));
    }

    public void MoveToScoreIndividual(View view) {
        startActivity(new Intent(IndividualTeamChallengesScreen1aActivity.this,IndividualTeamChallengesScreen1cActivity.class));
    }
}