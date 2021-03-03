package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class MyTrainingProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training_program);
        getSupportActionBar().hide();
    }

    public void MoveToCoach(View view) {
        startActivity(new Intent(MyTrainingProgramActivity.this,HelperChatActivity.class));
    }

    public void MoveToWeeklyExerciseLogs(View view) {
        startActivity(new Intent(MyTrainingProgramActivity.this,PersonalizedTrainingWeeklyExerciseLogsActivity.class));
    }
}