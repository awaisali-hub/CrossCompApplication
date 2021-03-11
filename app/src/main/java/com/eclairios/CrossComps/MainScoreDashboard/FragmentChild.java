package com.eclairios.CrossComps.MainScoreDashboard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.R;

public class FragmentChild extends Fragment {
    String Score_ID,User_ID,Date,Age,Meters,Squats,leg_raises,PushUps,totalScore;
    TextView tvDate,tvStatus,tvMeters,tvSquats,tvLeg_raises,tvPushUps,tvTotalScore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();
        Score_ID = bundle.getString("Score_ID");
        User_ID = bundle.getString("User_ID");
        Date = bundle.getString("Date");
        Age = bundle.getString("Age");
        Meters = bundle.getString("Meters");
        Squats = bundle.getString("Squats");
        leg_raises = bundle.getString("leg_raises");
        PushUps = bundle.getString("PushUps");
        totalScore = bundle.getString("totalScore");

        getIDs(view);
        return view;
    }

    private void getIDs(View view) {

        tvDate = (TextView) view.findViewById(R.id.userScoreDate);
        tvStatus = (TextView) view.findViewById(R.id.scoreStatus);
        tvMeters = (TextView) view.findViewById(R.id.meters);
        tvSquats = (TextView) view.findViewById(R.id.squats);
        tvLeg_raises = (TextView) view.findViewById(R.id.leg_raises);
        tvPushUps = (TextView) view.findViewById(R.id.push_ups);
        tvTotalScore = (TextView) view.findViewById(R.id.userScore);


        tvDate.setText(Date);

        tvMeters.setText(Meters);
        tvSquats.setText(Squats);
        tvLeg_raises.setText(leg_raises);
        tvPushUps.setText(PushUps);
        tvTotalScore.setText(totalScore);

        if(tvDate.getText().equals("2021-02-03")){
            tvStatus.setText("Status: "+"Expired");
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.colorRed));
        }else if(tvDate.getText().equals("2021-03-24")){
            tvStatus.setText("Status: "+"Current");
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
        }else{
            tvStatus.setText("Status: "+"Expiring");
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
        }
    }
}