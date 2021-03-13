package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class EditYourHomeTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_home_team);
        getSupportActionBar().hide();
    }

    public void W9ScreenForHomeTeam(View view) {
        startActivity(new Intent(EditYourHomeTeamActivity.this,W9NewHomeTeamActivity.class));
    }
}