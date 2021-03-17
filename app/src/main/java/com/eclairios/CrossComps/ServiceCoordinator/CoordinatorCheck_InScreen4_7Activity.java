package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.eclairios.CrossComps.R;

public class CoordinatorCheck_InScreen4_7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_check__in_screen4_7);
        getSupportActionBar().hide();
    }

    public void CheckIn5_7(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen4_7Activity.this,CoordinatorCheck_InScreen5_7Activity.class));
    }
}