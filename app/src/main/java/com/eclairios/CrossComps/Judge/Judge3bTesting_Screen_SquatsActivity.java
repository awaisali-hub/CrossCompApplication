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

public class Judge3bTesting_Screen_SquatsActivity extends AppCompatActivity {
    TextView Squats;
    private boolean clicked = false;
    int squats_reps = 0;

    Button squatsComplete,saveBtn;
    SharedPreferences preferences;
    Spinner squatsGradeSpinner;
    String squatsGrade;

    int check_user;
    EditText editJudgeTest;

    int gradePoint2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge3b_testing__screen__squats);
        getSupportActionBar().hide();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Squats = findViewById(R.id.squats_reps);
        squatsComplete = findViewById(R.id.squats_complete);
        squatsGradeSpinner = (Spinner) findViewById(R.id.squatsGradeSpinner);


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
                    Squats.setText(String.valueOf(squats_reps));
                    Squats.setPaintFlags(Squats.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    squats_reps += 1;
                } else {
                    Toast.makeText(Judge3bTesting_Screen_SquatsActivity.this, "Test started", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Judge3bTesting_Screen_SquatsActivity.this, "TEST FINISHED", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("FINISHED THANKS!");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                progressBar.setEnabled(false);
            }
        }, 180, TimeFormatEnum.SECONDS, 10);


        squatsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sGrade = squatsGradeSpinner.getSelectedItem().toString();

                if(sGrade.equals("Select One")){
                    Toast.makeText(Judge3bTesting_Screen_SquatsActivity.this, "Select Grade", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = preferences.edit();



                    if(check_user == 1){

                        if(Squats.getText().toString().equals("0")){
                            Toast.makeText(Judge3bTesting_Screen_SquatsActivity.this, "Enter Squats", Toast.LENGTH_SHORT).show();
                        }else{
                            editor.putString("squats1",Squats.getText().toString());
                            editor.putString("squatGrade1",sGrade);
                            editor.putInt("check_user", check_user);


                            if(sGrade.equals("A")){
                                gradePoint2 = 0;
                            }else if (sGrade.equals("B")){
                                gradePoint2 = 10;
                            }else if (sGrade.equals("C")){
                                gradePoint2 = 20;
                            }else if (sGrade.equals("D")){
                                gradePoint2 = 30;
                            }

                            editor.putInt("gradePoint2", gradePoint2);
                            editor.apply();
                        }

                    }else{
                        if(Squats.getText().toString().equals("0")){
                            Toast.makeText(Judge3bTesting_Screen_SquatsActivity.this, "Enter Squats", Toast.LENGTH_SHORT).show();
                        }else{
                            editor.putString("squats2",Squats.getText().toString());
                            editor.putString("squatGrade2",sGrade);
                            editor.putInt("check_user1", check_user);
                        }
                    }

                    editor.apply();

                    new AlertDialog.Builder(Judge3bTesting_Screen_SquatsActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Test Complete")
                            .setMessage("Are you sure you want to submit test result?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Judge3bTesting_Screen_SquatsActivity.this,Judge3a_SquatsActivity.class));
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();

                }

            }

        });


    }

    public void Judge4a(View view) {
        startActivity(new Intent(Judge3bTesting_Screen_SquatsActivity.this,Judge3a_SquatsActivity.class));
    }

    public void moveToJudge(View view) {
        startActivity(new Intent(Judge3bTesting_Screen_SquatsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

    public void JudgeEditTest3a(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Judge3bTesting_Screen_SquatsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_edit_judge_test,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        editJudgeTest = dialogView.findViewById(R.id.judge_edit);
        saveBtn = dialogView.findViewById(R.id.saveBtn);

        editJudgeTest.setText(Squats.getText().toString());

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Squats.setText(editJudgeTest.getText().toString());
                pickFileImage.dismiss();
            }
        });
    }
}