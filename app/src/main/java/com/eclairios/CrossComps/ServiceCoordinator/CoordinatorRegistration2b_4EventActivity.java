package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorRegistration2b_4EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration2b_4_event);
        getSupportActionBar().hide();
    }

    public void ServiceRegistration3_4Event(View view) {
        startActivity(new Intent(CoordinatorRegistration2b_4EventActivity.this,CoordinatorRegistration3_4Activity.class));
    }
}