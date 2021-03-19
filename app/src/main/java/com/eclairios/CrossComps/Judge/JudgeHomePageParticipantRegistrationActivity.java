package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class JudgeHomePageParticipantRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_home_page_participant_registration);
        getSupportActionBar().hide();
    }

    public void RunWalkScreen(View view) {
        startActivity(new Intent(JudgeHomePageParticipantRegistrationActivity.this,Judge2aRun_Walk_TestActivity.class));
    }
}