package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorCheck_InScreen5_7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_check__in_screen5_7);
        getSupportActionBar().hide();
    }

    public void CheckIn6_7(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen5_7Activity.this,CoordinatorCheck_InScreen6_7Activity.class));
    }
}