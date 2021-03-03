package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eclairios.CrossComps.R;

public class AllTeamCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_team_category);
        getSupportActionBar().hide();
    }

    public void TeamsPostalCode(View view) {


        ImageButton selectDateDayBtn,selectTimeBtn;
        Button moveToScore;
        TextView makeAppointment;



        AlertDialog.Builder builder = new AlertDialog.Builder(AllTeamCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_postal_team_auto_fill,null);
        builder.setCancelable(true);
        builder.setView(dialogView);
//
//        selectDateDay = dialogView.findViewById(R.id.datePicker);
//        selectTime = dialogView.findViewById(R.id.timePicker);

//        selectDateDayBtn = dialogView.findViewById(R.id.selectDate);
//        selectTimeBtn = dialogView.findViewById(R.id.selectTime);
        moveToScore = dialogView.findViewById(R.id.MoveToScore);
//        makeAppointment = dialogView.findViewById(R.id.ShowAppointment);
//
//        makeAppointment.setText("Make Reservation");


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        moveToScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllTeamCategoryActivity.this, TeamsScoreActivity.class));
            }
        });

    }

    public void MoveToMYChurchTeam(View view) {

        ImageButton selectDateDayBtn,selectTimeBtn;
        Button moveToScore;
        TextView makeAppointment;



        AlertDialog.Builder builder = new AlertDialog.Builder(AllTeamCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_church_team,null);
        builder.setCancelable(true);
        builder.setView(dialogView);
//
//        selectDateDay = dialogView.findViewById(R.id.datePicker);
//        selectTime = dialogView.findViewById(R.id.timePicker);

//        selectDateDayBtn = dialogView.findViewById(R.id.selectDate);
//        selectTimeBtn = dialogView.findViewById(R.id.selectTime);
 //       moveToScore = dialogView.findViewById(R.id.MoveToScore);
//        makeAppointment = dialogView.findViewById(R.id.ShowAppointment);
//
//        makeAppointment.setText("Make Reservation");


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

    }

    public void MoveToFundTeam(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(AllTeamCategoryActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.alert_dialog_for_fund_team,null);
//        builder.setCancelable(true);
//        builder.setView(dialogView);
//
//
//        AlertDialog pickFileImage = builder.create();
//        pickFileImage.show();

        startActivity(new Intent(AllTeamCategoryActivity.this, MyFundraisingTeamDetailActivity.class));
    }
}