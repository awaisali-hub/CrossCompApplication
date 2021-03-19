package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorRegistration3_4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration3_4);
        getSupportActionBar().hide();
    }

    public void Registration4_4(View view) {
        startActivity(new Intent(CoordinatorRegistration3_4Activity.this,CoordinatorRegistration4a_4FacilityActivity.class));
    }
}