package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge4aLeg_RaisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge4a_leg__raises);
        getSupportActionBar().hide();
    }


    public void Judge4aTeastingg(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,Judge4bTestingScreen_Leg_RaisesActivity.class));
        finish();
    }

    public void Judge4aTeasting(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,Judge4bTestingScreen_Leg_RaisesActivity.class));
        finish();
    }

    public void MoveTo5Bpush_ups(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,Judge5aPush_UpsActivity.class));
        finish();
    }

    public void jjude(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
        finish();
    }
}