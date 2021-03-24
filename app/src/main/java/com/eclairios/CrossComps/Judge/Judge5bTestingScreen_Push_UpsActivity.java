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

public class Judge5bTestingScreen_Push_UpsActivity extends AppCompatActivity {
    TextView Push_Ups;
    private boolean clicked = false;
    int Push_Ups_reps = 0;

    Button Push_UpsComplete,saveBtn;
    SharedPreferences preferences;
    Spinner Push_UpsGradeSpinner;
    String Push_UpsGrade;

    int check_user;
    EditText editJudgeTest;
    int gradePoint4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge5b_testing_screen__push__ups);
        getSupportActionBar().hide();


        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Push_Ups = findViewById(R.id.Push_Ups_reps);
        Push_UpsComplete = findViewById(R.id.Push_UpsComplete);
        Push_UpsGradeSpinner = (Spinner) findViewById(R.id.Push_UpsGradeSpinner);



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
                    Push_Ups.setText(String.valueOf(Push_Ups_reps));
                    Push_Ups.setPaintFlags(Push_Ups.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    Push_Ups_reps += 1;
                } else {
                    Toast.makeText(Judge5bTestingScreen_Push_UpsActivity.this, "Test started", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Judge5bTestingScreen_Push_UpsActivity.this, "TEST FINISHED", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("FINISHED THANKS!");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                progressBar.setEnabled(false);
            }
        }, 60, TimeFormatEnum.SECONDS, 10);


        Push_UpsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sGrade = Push_UpsGradeSpinner.getSelectedItem().toString();

                if(sGrade.equals("Select One")){
                    Toast.makeText(Judge5bTestingScreen_Push_UpsActivity.this, "Select Grade", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = preferences.edit();



                    if(check_user == 1){

                        if(Push_Ups.getText().toString().equals("0")){
                            Toast.makeText(Judge5bTestingScreen_Push_UpsActivity.this, "Enter Push-Ups", Toast.LENGTH_SHORT).show();
                        }else{

                            editor.putString("PushUps1",Push_Ups.getText().toString());
                            editor.putString("PushUpsGrade1",sGrade);
                            editor.putInt("check_user", check_user);


                            if (sGrade.equals("A")){
                                gradePoint4 = 0;
                            }else if (sGrade.equals("B")){
                                gradePoint4 = 10;
                            }else if (sGrade.equals("C")){
                                gradePoint4 = 20;
                            }else if (sGrade.equals("D")){
                                gradePoint4 = 30;
                            }

                            editor.putInt("gradePoint4",gradePoint4);
                            editor.apply();
                        }

                    }else{
                        if(Push_Ups.getText().toString().equals("0")){
                            Toast.makeText(Judge5bTestingScreen_Push_UpsActivity.this, "Enter Push-Ups", Toast.LENGTH_SHORT).show();
                        }else{

                            editor.putString("PushUps2",Push_Ups.getText().toString());
                            editor.putString("PushUpsGrade2",sGrade);
                            editor.putInt("check_user1", check_user);
                        }
                    }

                    editor.apply();


                    new AlertDialog.Builder(Judge5bTestingScreen_Push_UpsActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Test Complete")
                            .setMessage("Are you sure you want to submit test result?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Judge5bTestingScreen_Push_UpsActivity.this,Judge5aPush_UpsActivity.class));
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();


                }

            }

        });

    }



    public void MoveToJudgeScreen(View view) {
        startActivity(new Intent(Judge5bTestingScreen_Push_UpsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

    public void JudgeEditTest5a(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Judge5bTestingScreen_Push_UpsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_edit_judge_test,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        editJudgeTest = dialogView.findViewById(R.id.judge_edit);
        saveBtn = dialogView.findViewById(R.id.saveBtn);

        editJudgeTest.setText(Push_Ups.getText().toString());

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Push_Ups.setText(editJudgeTest.getText().toString());
                pickFileImage.dismiss();
            }
        });
    }
}