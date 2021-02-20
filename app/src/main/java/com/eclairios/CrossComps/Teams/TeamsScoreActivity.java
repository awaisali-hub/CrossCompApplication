package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eclairios.CrossComps.R;

public class TeamsScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_score);
    }

    public void MoveToFundTeam(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TeamsScoreActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_fund_team,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();
    }

    public void EditPostalCode(View view) {
        ImageButton selectDateDayBtn,selectTimeBtn;
        Button moveToScore;
        TextView makeAppointment;



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TeamsScoreActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_postal_team_auto_fill,null);
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


        android.app.AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

    }
}