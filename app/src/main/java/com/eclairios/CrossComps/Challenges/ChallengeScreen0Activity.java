package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class ChallengeScreen0Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_screen0);
    }

    public void MoveToStandardChallenge(View view) {
        startActivity(new Intent(ChallengeScreen0Activity.this, StandardTeamChallengesScreen1aActivity.class));
    }

    public void MoveToCustomChallenge(View view) {
        startActivity(new Intent(ChallengeScreen0Activity.this, CustomTeamChallengesScreen1aActivity.class));
    }
}