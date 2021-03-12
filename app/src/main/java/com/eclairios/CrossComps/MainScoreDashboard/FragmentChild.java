package com.eclairios.CrossComps.MainScoreDashboard;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentChild extends Fragment {
    private String Score_ID,User_ID,Dates,Age,Meters,Squats,leg_raises,PushUps,totalScore;
    private TextView tvDate,tvStatus,tvMeters,tvSquats,tvLeg_raises,tvPushUps,tvTotalScore;
    private String Current = "Current";
    private String Expiring = "Expiring";
    private String Expired = "Expired";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();
        Score_ID = bundle.getString("Score_ID");
        User_ID = bundle.getString("User_ID");
        Dates = bundle.getString("Date");
        Age = bundle.getString("Age");
        Meters = bundle.getString("Meters");
        Squats = bundle.getString("Squats");
        leg_raises = bundle.getString("leg_raises");
        PushUps = bundle.getString("PushUps");
        totalScore = bundle.getString("totalScore");

        try {
            getIDs(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists", "SetTextI18n"})
    private void getIDs(View view) throws ParseException {

        tvDate = (TextView) view.findViewById(R.id.userScoreDate);
        tvStatus = (TextView) view.findViewById(R.id.scoreStatus);
        tvMeters = (TextView) view.findViewById(R.id.meters);
        tvSquats = (TextView) view.findViewById(R.id.squats);
        tvLeg_raises = (TextView) view.findViewById(R.id.leg_raises);
        tvPushUps = (TextView) view.findViewById(R.id.push_ups);
        tvTotalScore = (TextView) view.findViewById(R.id.userScore);

        DateFormat outputFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String inputText = Dates;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);

        tvDate.setText(outputText);
        tvMeters.setText(Meters);
        tvSquats.setText(Squats);
        tvLeg_raises.setText(leg_raises);
        tvPushUps.setText(PushUps);
        tvTotalScore.setText(totalScore);
        tvTotalScore.setPaintFlags(tvTotalScore.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);

        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.MONTH, -1);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MONTH, -2);

        if(Dates.contains(format.format(cal.getTime()))){
            tvStatus.setText("Status: "+Current);
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
        }else if (Dates.contains(format.format(cal1.getTime()))){
            tvStatus.setText("Status: "+Expiring);
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
        }else if (Dates.contains(format.format(cal2.getTime()))){
            tvStatus.setText("Status: "+Expired);
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.colorRed));
        }else{
            tvStatus.setText("Status: "+Expired);
            tvStatus.setBackgroundDrawable( getResources().getDrawable(R.drawable.servicenameitems) );
            tvStatus.setBackgroundTintList(getResources().getColorStateList(R.color.colorRed));
        }
    }
}