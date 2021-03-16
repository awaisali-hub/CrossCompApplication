package com.eclairios.CrossComps.AffiliteTeamCaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class MembersScreenOfCoCaptainsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen_of_co_captains);
        getSupportActionBar().hide();
    }

    public void MembersScreenOfTeamCaptain(View view) {
        startActivity(new Intent(MembersScreenOfCoCaptainsActivity.this,MembersScreenOfTeamCaptainActivity.class));
    }
}