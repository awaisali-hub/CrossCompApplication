package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoCaptainsScreenOfAffiliatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_captains_screen_of_affiliates);
        getSupportActionBar().hide();
    }

    public void MoveCoCaptainsAfterRequest(View view) {
        startActivity(new Intent(CoCaptainsScreenOfAffiliatesActivity.this,CoCapatainsScreenOfCoCaptainsActivity.class));
    }
}