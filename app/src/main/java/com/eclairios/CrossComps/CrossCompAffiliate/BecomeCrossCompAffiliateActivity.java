package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class BecomeCrossCompAffiliateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_cross_comp_affiliate);
        getSupportActionBar().hide();
    }

    public void MoveToAgreement(View view) {
        startActivity(new Intent(BecomeCrossCompAffiliateActivity.this,CrossCompAffiliateAgreementActivity.class));
    }
}