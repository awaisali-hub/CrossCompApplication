package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.eclairios.CrossComps.CoordinatorServicePage;
import com.eclairios.CrossComps.R;

public class PersonalizedTrainingWeeklyExerciseLogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized_training_weekly_exercise_logs);
        getSupportActionBar().hide();
    }

    public void MoveToRecordToExerciseLog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalizedTrainingWeeklyExerciseLogsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_exercise_log,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        AlertDialog pickFileImage = builder.create();

        pickFileImage.show();

    }
}