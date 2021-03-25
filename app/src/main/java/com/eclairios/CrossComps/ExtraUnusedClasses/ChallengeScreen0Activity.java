package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.Challenges.CustomTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.Challenges.IndividualTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.Challenges.StandardTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.R;

public class ChallengeScreen0Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_screen0);
        getSupportActionBar().hide();
    }

    public void MoveToStandardChallenge(View view) {
        startActivity(new Intent(ChallengeScreen0Activity.this, StandardTeamChallengesScreen1aActivity.class));
    }

    public void MoveToCustomChallenge(View view) {
        startActivity(new Intent(ChallengeScreen0Activity.this, CustomTeamChallengesScreen1aActivity.class));
    }

    public void MoveToIndividualChallenge(View view) {
        startActivity(new Intent(ChallengeScreen0Activity.this, IndividualTeamChallengesScreen1aActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChallengeScreen0Activity.this, Participent.class));
    }
}