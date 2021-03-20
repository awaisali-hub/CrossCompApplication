package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge2aRun_Walk_TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge2a_run__walk__test);
        getSupportActionBar().hide();
    }

    public void MoveTo2bTesting(View view) {
        startActivity(new Intent(Judge2aRun_Walk_TestActivity.this,Judge3a_SquatsActivity.class));
    }

    public void moveToTask(View view) {

        startActivity(new Intent(Judge2aRun_Walk_TestActivity.this,Judge2bTestingScreenRun_WalkActivity.class));
        finish();

    }

    public void moveToTaskk(View view) {
        startActivity(new Intent(Judge2aRun_Walk_TestActivity.this,Judge2bTestingScreenRun_WalkActivity.class));
        finish();
    }


    public void moveToJudeee(View view) {
        startActivity(new Intent(Judge2aRun_Walk_TestActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}