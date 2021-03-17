package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorServiceOrEventRegistration2a_4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_or_event_registration2a_4);
        getSupportActionBar().hide();
    }

    public void ServiceRegistration3_4(View view) {
        startActivity(new Intent(CoordinatorServiceOrEventRegistration2a_4Activity.this,CoordinatorRegistration2b_4EventActivity.class));
    }
}