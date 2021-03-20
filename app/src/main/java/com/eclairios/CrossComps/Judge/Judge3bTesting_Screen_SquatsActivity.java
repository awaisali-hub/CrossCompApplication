package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge3bTesting_Screen_SquatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge3b_testing__screen__squats);
        getSupportActionBar().hide();
    }

    public void Judge4a(View view) {
        startActivity(new Intent(Judge3bTesting_Screen_SquatsActivity.this,Judge3a_SquatsActivity.class));
    }

    public void moveToJudge(View view) {
        startActivity(new Intent(Judge3bTesting_Screen_SquatsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}