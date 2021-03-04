package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CertifiedCrossCompGymPurchaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_certified_cross_comp_gym_purchase);
    }

    public void MoveToGymPayment(View view) {
        startActivity(new Intent(CertifiedCrossCompGymPurchaseActivity.this,GymPackageActivity.class));
    }
}