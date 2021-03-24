package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.R;
import com.view.circulartimerview.CircularTimerListener;
import com.view.circulartimerview.CircularTimerView;
import com.view.circulartimerview.TimeFormatEnum;

public class Judge4bTestingScreen_Leg_RaisesActivity extends AppCompatActivity {
    TextView Leg_Raises;
    private boolean clicked = false;
    int Leg_Raises_reps = 0;

    Button Leg_RaisesComplete,saveBtn;
    SharedPreferences preferences;
    Spinner Leg_RaisesGradeSpinner;
    String Leg_RaisesGrade;

    int check_user;
    EditText editJudgeTest;

    int gradePoint3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge4b_testing_screen__leg__raises);
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Leg_Raises = findViewById(R.id.Leg_Raises_reps);
        Leg_RaisesComplete = findViewById(R.id.leg_RaisesComplete);
        Leg_RaisesGradeSpinner = (Spinner) findViewById(R.id.leg_RaisesGradeSpinner);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            check_user = Integer.parseInt(extras.getString("check1"));
        }


        CircularTimerView progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);
        progressBar.setStartingAngle(270);
        progressBar.setClockwise(false);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(clicked) {
                    Leg_Raises.setText(String.valueOf(Leg_Raises_reps));
                    Leg_Raises.setPaintFlags(Leg_Raises.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    Leg_Raises_reps += 1;
                } else {
                    Toast.makeText(Judge4bTestingScreen_Leg_RaisesActivity.this, "Test started", Toast.LENGTH_SHORT).show();
                    progressBar.startTimer();
                }

                clicked = true;
            }
        });

// To Initialize Timer
        progressBar.setCircularTimerListener(new CircularTimerListener() {
            @Override
            public String updateDataOnTick(long remainingTimeInMs) {
                return String.valueOf((int)Math.ceil((remainingTimeInMs / 1000.f)));
            }

            @Override
            public void onTimerFinished() {
                Toast.makeText(Judge4bTestingScreen_Leg_RaisesActivity.this, "TEST FINISHED", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("FINISHED THANKS!");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                progressBar.setEnabled(false);
            }
        }, 120, TimeFormatEnum.SECONDS, 10);



        Leg_RaisesComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sGrade = Leg_RaisesGradeSpinner.getSelectedItem().toString();

                if(sGrade.equals("Select One")){
                    Toast.makeText(Judge4bTestingScreen_Leg_RaisesActivity.this, "Select Grade", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = preferences.edit();



                    if(check_user == 1){

                        if(Leg_Raises.getText().toString().equals("0")){
                            Toast.makeText(Judge4bTestingScreen_Leg_RaisesActivity.this, "Enter Leg-Raises", Toast.LENGTH_SHORT).show();
                        }else{

                            editor.putString("LegRaises1",Leg_Raises.getText().toString());
                            editor.putString("LegRaisesGrade1",sGrade);
                            editor.putInt("check_user", check_user);


                            if (sGrade.equals("A")){
                                gradePoint3 = 0;
                            }else if (sGrade.equals("B")){
                                gradePoint3 = 10;
                            }else if (sGrade.equals("C")){
                                gradePoint3 = 20;
                            }else if (sGrade.equals("D")){
                                gradePoint3 = 30;
                            }

                            editor.putInt("gradePoint3",gradePoint3);
                            editor.apply();
                        }

                    }else{
                        if(Leg_Raises.getText().toString().equals("0")){
                            Toast.makeText(Judge4bTestingScreen_Leg_RaisesActivity.this, "Enter Leg-Raises", Toast.LENGTH_SHORT).show();
                        }else{

                            editor.putString("LegRaises2",Leg_Raises.getText().toString());
                            editor.putString("LegRaisesGrade2",sGrade);
                            editor.putInt("check_user1", check_user);
                        }
                    }

                    editor.apply();

                    new AlertDialog.Builder(Judge4bTestingScreen_Leg_RaisesActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Test Complete")
                            .setMessage("Are you sure you want to submit test result?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Judge4bTestingScreen_Leg_RaisesActivity.this,Judge4aLeg_RaisesActivity.class));
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();



                }

            }

        });

    }


    public void moveToJudge(View view) {
        startActivity(new Intent(Judge4bTestingScreen_Leg_RaisesActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

    public void JudgeEditTest4a(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Judge4bTestingScreen_Leg_RaisesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_edit_judge_test,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        editJudgeTest = dialogView.findViewById(R.id.judge_edit);
        saveBtn = dialogView.findViewById(R.id.saveBtn);

        editJudgeTest.setText(Leg_Raises.getText().toString());

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leg_Raises.setText(editJudgeTest.getText().toString());
                pickFileImage.dismiss();
            }
        });
    }
}