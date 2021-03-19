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

    public void MoveTo4bTestingLegRaises(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,Judge4bTestingScreen_Leg_RaisesActivity.class));
    }
}