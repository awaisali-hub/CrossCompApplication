package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainsAllTeamsActivity;
import com.eclairios.CrossComps.AffiliteTeamManager.HomeTeamActivity;
import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainActivity;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.Judge.JudgeHomePageParticipantRegistrationActivity;
import com.eclairios.CrossComps.MainFragments.ScoresFragment;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.ServiceCoordinatorHomePageActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AffiliateDashboardActivity extends AppCompatActivity {
    TextView ManagerAgreement,judgeAgreement,serviceCoordinatorAgreement,directorAgreement;
    CircleImageView HomeFab;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_dashboard);
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        ManagerAgreement = findViewById(R.id.ManagerAgreement);
        judgeAgreement = findViewById(R.id.judgeAgreement);
        serviceCoordinatorAgreement = findViewById(R.id.serviceCoordinatorAgreement);
        directorAgreement = findViewById(R.id.directorAgreement);

        HomeFab = findViewById(R.id.MoveToMainHomePage);

        SharedPreferences.Editor editor = preferences.edit();


        ManagerAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("affiliateType","Manager");
                editor.apply();
                MoveToAgreement(v);
            }
        });

        judgeAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("affiliateType","Judge");
                editor.apply();
                MoveToAgreement(v);
            }
        });

        serviceCoordinatorAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("affiliateType","ServiceCoordinator");
                editor.apply();
                MoveToAgreement(v);
            }
        });

        directorAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("affiliateType","Director");
                editor.apply();
                MoveToAgreement(v);
            }
        });

        HomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToMainHomePage(v);
            }
        });


    }


    public void MoveToAgreement(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, CrossCompAffiliateAgreementActivity.class));
    }



    public void MoveToMainHomePage(View view) {
        startActivity(new Intent(AffiliateDashboardActivity.this, Dashboard.class));

//        Fragment mFragment = null;
//        mFragment = new ScoresFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.navHostFragment, mFragment).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AffiliateDashboardActivity.this, Dashboard.class));
    }

//    @Override
//    public void onResume() {
//
//        super.onResume();
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//
//                    // handle back button
//
//                    startActivity(new Intent(getContext(), Dashboard.class));
//
//                    return true;
//
//                }
//
//                return false;
//            }
//        });
//    }
}