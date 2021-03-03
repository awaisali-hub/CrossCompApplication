package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CustomTeamChallengesScreen1cActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_team_challenges_screen1c);
        getSupportActionBar().hide();
    }

    public void MoveToCustomScore(View view) {
        startActivity(new Intent(CustomTeamChallengesScreen1cActivity.this,CustomTeamChallengesScreen1bActivity.class));
    }
}