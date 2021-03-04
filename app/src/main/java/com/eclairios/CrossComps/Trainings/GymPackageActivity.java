package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class GymPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_package);
        getSupportActionBar().hide();
    }

    public void MoveToGymsPayment(View view) {
        startActivity(new Intent(GymPackageActivity.this,CertifiedCrossCompGymPaymentActivity.class));
    }
}