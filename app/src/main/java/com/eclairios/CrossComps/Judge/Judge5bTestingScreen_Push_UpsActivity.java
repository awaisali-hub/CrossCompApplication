package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge5bTestingScreen_Push_UpsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge5b_testing_screen__push__ups);
        getSupportActionBar().hide();
    }

    public void Judge5a(View view) {
        startActivity(new Intent(Judge5bTestingScreen_Push_UpsActivity.this,Judge5aPush_UpsActivity.class));
    }

    public void MoveToJudgeScreen(View view) {
        startActivity(new Intent(Judge5bTestingScreen_Push_UpsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}