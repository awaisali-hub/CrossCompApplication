package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eclairios.CrossComps.R;

public class PersonalizedTrainingWeeklyExerciseLogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized_training_weekly_exercise_logs);
        getSupportActionBar().hide();
    }
}