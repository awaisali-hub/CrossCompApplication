package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge4bTestingScreen_Leg_RaisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge4b_testing_screen__leg__raises);
        getSupportActionBar().hide();
    }

    public void moveTo4a(View view) {
        startActivity(new Intent(Judge4bTestingScreen_Leg_RaisesActivity.this,Judge4aLeg_RaisesActivity.class));
    }

    public void moveToJudege(View view) {
        startActivity(new Intent(Judge4bTestingScreen_Leg_RaisesActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

//    public void Judge5a(View view) {
//        startActivity(new Intent(Judge4bTestingScreen_Leg_RaisesActivity.this,Judge5aPush_UpsActivity.class));
//    }
}