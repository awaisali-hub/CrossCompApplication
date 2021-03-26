package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.R;

public class OptiHealthPledgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opti_health_pledge);
        getSupportActionBar().hide();
    }

    public void MoveToAffiliateDashboard(View view) {
        startActivity(new Intent(OptiHealthPledgeActivity.this,AffiliateDashboardActivity.class));
    }

    public void MoveToMainHomePage(View view) {
      //  startActivity(new Intent(OptiHealthPledgeActivity.this, Participent.class));
    }
}