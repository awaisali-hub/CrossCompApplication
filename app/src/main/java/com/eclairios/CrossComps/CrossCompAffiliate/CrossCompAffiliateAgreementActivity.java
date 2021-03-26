package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.MainFragments.ScoresFragment;
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

    public void MoveToMainHomePage(View view) {
       // startActivity(new Intent(CrossCompAffiliateAgreementActivity.this, Participent.class));
        startActivity(new Intent(CrossCompAffiliateAgreementActivity.this, Dashboard.class));
    }

}