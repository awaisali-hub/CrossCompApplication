package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorRegistration4a_4FacilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration4a_4_facility);
        getSupportActionBar().hide();
    }

    public void Registration4b4(View view) {
        startActivity(new Intent(CoordinatorRegistration4a_4FacilityActivity.this,CoordinatorRegistration4b_4EventActivity.class));
    }
}