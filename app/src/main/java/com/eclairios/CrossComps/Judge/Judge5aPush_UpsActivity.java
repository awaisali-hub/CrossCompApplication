package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge5aPush_UpsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge5a_push__ups);
        getSupportActionBar().hide();
    }

    public void MoveTo5bTestingPushUps(View view) {
        startActivity(new Intent(Judge5aPush_UpsActivity.this,Judge5bTestingScreen_Push_UpsActivity.class));
    }


    public void judgeHomePage(View view) {
        startActivity(new Intent(Judge5aPush_UpsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}