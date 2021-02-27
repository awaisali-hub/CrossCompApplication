package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class AfterFitnessConsultationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_fitness_consultation);
    }

    public void MoveToMyTrainingProgram(View view) {
        startActivity(new Intent(AfterFitnessConsultationActivity.this,MyTrainingProgramActivity.class));
    }
}