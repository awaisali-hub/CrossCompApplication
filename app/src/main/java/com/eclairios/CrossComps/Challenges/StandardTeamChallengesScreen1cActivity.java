package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.ExtraUnusedClasses.ChallengeScreen0Activity;
import com.eclairios.CrossComps.R;

public class StandardTeamChallengesScreen1cActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_team_challenges_screen1c);
        getSupportActionBar().hide();
    }

    public void MoveToMyCrossCompChallenges(View view) {

        startActivity(new Intent(StandardTeamChallengesScreen1cActivity.this, ChallengeScreen0Activity.class));
    }
}