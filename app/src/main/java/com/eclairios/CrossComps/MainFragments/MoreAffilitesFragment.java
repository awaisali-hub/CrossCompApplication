package com.eclairios.CrossComps.MainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eclairios.CrossComps.AffiliteTeamCaptain.TeamCaptainsAllTeamsActivity;
import com.eclairios.CrossComps.AffiliteTeamManager.HomeTeamActivity;
import com.eclairios.CrossComps.CrossCompAffiliate.AffiliateDashboardActivity;
import com.eclairios.CrossComps.CrossCompAffiliate.CrossCompAffiliateAgreementActivity;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.Judge.JudgeHomePageParticipantRegistrationActivity;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.ServiceCoordinatorHomePageActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MoreAffilitesFragment extends Fragment {

    Button Judge,TeamCaptain,serviceCoordinator,TeamManager,Store,Earnings;
    CircleImageView fabHomeScore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_affilites, container, false);

        try{
            WaitDialog.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Judge = view.findViewById(R.id.Judge);
        TeamCaptain = view.findViewById(R.id.TeamCaptain);
        serviceCoordinator = view.findViewById(R.id.ServiceCoordinator);
        TeamManager = view.findViewById(R.id.TeamManager);
        Store = view.findViewById(R.id.Store);
        Earnings = view.findViewById(R.id.Earnings);
        fabHomeScore = view.findViewById(R.id.fabHomeScore);


        Judge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Judge(v);
            }
        });

        TeamCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToTeamCaptain(v);
            }
        });

        serviceCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceCoordinator(v);
            }
        });

        TeamManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToTeamManager(v);
            }
        });


        fabHomeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScore(v);
            }
        });


        return view;
    }


    public void MoveToTeamManager(View view) {
        startActivity(new Intent(getContext(), HomeTeamActivity.class));
    }

    public void MoveToTeamCaptain(View view) {
        startActivity(new Intent(getContext(), TeamCaptainsAllTeamsActivity.class));

    }

    public void ServiceCoordinator(View view) {
        startActivity(new Intent(getContext(), ServiceCoordinatorHomePageActivity.class));
    }

    public void Judge(View view) {
        startActivity(new Intent(getContext(), JudgeHomePageParticipantRegistrationActivity.class));
    }



    public void HomeScore(View view) {
        startActivity(new Intent(getContext(), Dashboard.class));
    }



    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button

                    startActivity(new Intent(getContext(), Dashboard.class));

                    return true;

                }

                return false;
            }
        });
    }
}