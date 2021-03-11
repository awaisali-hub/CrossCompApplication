package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CoManagersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_managers);
        getSupportActionBar().hide();
    }

    public void MoveToMessage(View view) {
        startActivity(new Intent(CoManagersActivity.this,TeamMessageActivity.class));
    }

    public void RequestForCoManager(View view) {
        startActivity(new Intent(CoManagersActivity.this,UserRequestCoManagersActivity.class));
    }
}