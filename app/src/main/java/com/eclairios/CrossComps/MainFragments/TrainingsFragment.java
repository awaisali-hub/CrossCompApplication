package com.eclairios.CrossComps.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Trainings.GymsMapActivity;
import com.eclairios.CrossComps.Trainings.TrainingPersonalizedActivity;
import com.eclairios.CrossComps.Trainings.TrainingRecommendationsActivity;


public class TrainingsFragment extends Fragment {

    Button MoveToRecommendationsTraining,MoveToPersonalizedTraining,MoveToCertifiedGyms;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);

        MoveToRecommendationsTraining = view.findViewById(R.id.MoveToRecommendationsTraining);
        MoveToPersonalizedTraining = view.findViewById(R.id.MoveToPersonalizedTraining);
        MoveToCertifiedGyms = view.findViewById(R.id.MoveToCertifiedGyms);

        MoveToRecommendationsTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToRecommendationsTraining(view);
            }
        });

        MoveToPersonalizedTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToPersonalizedTraining(view);
            }
        });

        MoveToCertifiedGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToCertifiedGyms(view);
            }
        });

        return view;
    }

    public void MoveToRecommendationsTraining(View view) {
        startActivity(new Intent(getContext(), TrainingRecommendationsActivity.class));
    }

    public void MoveToPersonalizedTraining(View view) {
        startActivity(new Intent(getContext(), TrainingPersonalizedActivity.class));
    }

    public void MoveToCertifiedGyms(View view) {
        startActivity(new Intent(getContext(), GymsMapActivity.class));
    }
}