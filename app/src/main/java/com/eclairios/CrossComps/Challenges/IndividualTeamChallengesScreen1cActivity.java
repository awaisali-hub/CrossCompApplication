package com.eclairios.CrossComps.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.eclairios.CrossComps.ExtraUnusedClasses.ChallengeScreen0Activity;
import com.eclairios.CrossComps.R;

public class IndividualTeamChallengesScreen1cActivity extends AppCompatActivity {

    TextView userScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_team_challenges_screen1c);
        getSupportActionBar().hide();

        userScore = findViewById(R.id.individual_challenge_user_score);

        String text="88.2";
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        userScore.setText(content);
    }

    public void MoveToMyCrossCompChallenges(View view) {
        startActivity(new Intent(IndividualTeamChallengesScreen1cActivity.this, ChallengeScreen0Activity.class));
    }
}