package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class Judge3a_SquatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge3a__squats);
        getSupportActionBar().hide();
    }

    public void MoveTo3bTestingSquats(View view) {
        startActivity(new Intent(Judge3a_SquatsActivity.this,Judge3bTesting_Screen_SquatsActivity.class));
    }
}