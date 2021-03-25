package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Trainings.GymsMapActivity;
import com.eclairios.CrossComps.Trainings.TrainingPersonalizedActivity;
import com.eclairios.CrossComps.Trainings.TrainingRecommendationsActivity;

public class TrainingMainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_main_page);
        getSupportActionBar().hide();
    }

    public void MoveToRecommendationsTraining(View view) {
        startActivity(new Intent(TrainingMainPageActivity.this, TrainingRecommendationsActivity.class));
    }

    public void MoveToPersonalizedTraining(View view) {
        startActivity(new Intent(TrainingMainPageActivity.this, TrainingPersonalizedActivity.class));
    }

    public void MoveToCertifiedGyms(View view) {
        startActivity(new Intent(TrainingMainPageActivity.this, GymsMapActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TrainingMainPageActivity.this, Participent.class));
    }
}