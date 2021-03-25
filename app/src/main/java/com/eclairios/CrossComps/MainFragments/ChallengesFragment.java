package com.eclairios.CrossComps.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eclairios.CrossComps.Challenges.CustomTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.Challenges.IndividualTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.Challenges.StandardTeamChallengesScreen1aActivity;
import com.eclairios.CrossComps.R;


public class ChallengesFragment extends Fragment {

    Button MoveToStandardChallenge,MoveToCustomChallenge,MoveToIndividualChallenge;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        MoveToStandardChallenge = view.findViewById(R.id.MoveToStandardChallenge);
        MoveToCustomChallenge = view.findViewById(R.id.MoveToCustomChallenge);
        MoveToIndividualChallenge = view.findViewById(R.id.MoveToIndividualChallenge);


        MoveToStandardChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToStandardChallenge(view);
            }
        });

        MoveToCustomChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToCustomChallenge(view);
            }
        });

        MoveToIndividualChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToIndividualChallenge(view);
            }
        });
        return view;
    }

    public void MoveToStandardChallenge(View view) {
        startActivity(new Intent(getContext(), StandardTeamChallengesScreen1aActivity.class));
    }

    public void MoveToCustomChallenge(View view) {
        startActivity(new Intent(getContext(), CustomTeamChallengesScreen1aActivity.class));
    }

    public void MoveToIndividualChallenge(View view) {
        startActivity(new Intent(getContext(), IndividualTeamChallengesScreen1aActivity.class));
    }
}