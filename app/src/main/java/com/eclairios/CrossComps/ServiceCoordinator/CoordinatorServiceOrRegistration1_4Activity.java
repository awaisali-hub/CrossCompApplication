package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorServiceOrRegistration1_4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_or_registration1_4);
        getSupportActionBar().hide();
    }

    public void ServiceRegistration2a_4(View view) {
        startActivity(new Intent(CoordinatorServiceOrRegistration1_4Activity.this,CoordinatorServiceOrEventRegistration2a_4Activity.class));
    }
}