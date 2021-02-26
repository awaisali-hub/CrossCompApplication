package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class TrainingPersonalizedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_personalized);
    }

    public void PaymentActivity(View view) {
        startActivity(new Intent(TrainingPersonalizedActivity.this, PaymentActivity.class));
    }
}