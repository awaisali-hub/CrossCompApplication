package com.eclairios.CrossComps.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eclairios.CrossComps.CrossCompAffiliate.CrossCompAffiliateAgreementActivity;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class MoreAffilitesFragment extends Fragment {


    TextView ManagerAgreement,judgeAgreement,serviceCoordinatorAgreement,directorAgreement;
    CircleImageView HomeFab;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_affilites, container, false);

        ManagerAgreement = view.findViewById(R.id.ManagerAgreement);
        judgeAgreement = view.findViewById(R.id.judgeAgreement);
        serviceCoordinatorAgreement = view.findViewById(R.id.serviceCoordinatorAgreement);
        directorAgreement = view.findViewById(R.id.directorAgreement);

        HomeFab = view.findViewById(R.id.MoveToMainHomePage);



        ManagerAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToAgreement(view);
            }
        });

        judgeAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToAgreement(view);
            }
        });

        serviceCoordinatorAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToAgreement(view);
            }
        });

        directorAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToAgreement(view);
            }
        });

        HomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToMainHomePage(view);
            }
        });


        return view;
    }


    public void MoveToAgreement(View view) {
        startActivity(new Intent(getContext(), CrossCompAffiliateAgreementActivity.class));
    }



    public void MoveToMainHomePage(View view) {
        startActivity(new Intent(getContext(), Participent.class));
    }
}