package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eclairios.CrossComps.R;

public class StandardTeamChallengesScreen1aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_team_challenges_screen1a);
        getSupportActionBar().hide();
    }

    public void MoveToCitySelection(View view) {
        startActivity(new Intent(StandardTeamChallengesScreen1aActivity.this,StandardTeamChallengesScreen1bActivity.class));

       // startActivity(new Intent(StandardTeamChallengesScreen1aActivity.this,StandardTeamChallengesScreen1cActivity.class));
    }

//    public void Change(View view) {
//        Button btn = findViewById(R.id.btn);
//        btn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
//    }
}