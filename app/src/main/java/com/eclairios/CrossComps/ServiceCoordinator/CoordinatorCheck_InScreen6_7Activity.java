package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoordinatorCheck_InScreen6_7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_check__in_screen6_7);
        getSupportActionBar().hide();
    }

    public void CheckIn7_7(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen6_7Activity.this,CoordinatorCheck_InScreen7_7Activity.class));
    }
}