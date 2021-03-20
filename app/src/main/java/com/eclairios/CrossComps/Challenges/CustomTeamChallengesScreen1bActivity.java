package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CustomTeamChallengesScreen1bActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_team_challenges_screen1b);
        getSupportActionBar().hide();
    }

    public void MoveToCustomizeGenderAgeRange(View view) {
        startActivity(new Intent(CustomTeamChallengesScreen1bActivity.this,CustomTeamChallengesScreen1cActivity.class));
    }

    public void MoveToMyCrossCompChallenges(View view) {
        startActivity(new Intent(CustomTeamChallengesScreen1bActivity.this,ChallengeScreen0Activity.class));
    }
}