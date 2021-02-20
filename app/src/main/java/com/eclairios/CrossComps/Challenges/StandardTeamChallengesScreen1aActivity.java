package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class StandardTeamChallengesScreen1aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_team_challenges_screen1a);
    }

    public void MoveToCitySelection(View view) {
      //  startActivity(new Intent(StandardTeamChallengesScreen1aActivity.this,StandardTeamChallengesScreen1bActivity.class));

        startActivity(new Intent(StandardTeamChallengesScreen1aActivity.this,StandardTeamChallengesScreen1cActivity.class));
    }
}