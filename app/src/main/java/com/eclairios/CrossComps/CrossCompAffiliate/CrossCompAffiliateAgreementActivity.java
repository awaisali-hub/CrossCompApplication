package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.R;

public class CrossCompAffiliateAgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_comp_affiliate_agreement);
        getSupportActionBar().hide();
    }

    public void MoveToPledge(View view) {
        startActivity(new Intent(CrossCompAffiliateAgreementActivity.this,OptiHealthPledgeActivity.class));
    }
}