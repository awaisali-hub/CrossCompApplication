package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.MainActivity;
import com.eclairios.CrossComps.R;
import com.view.circulartimerview.CircularTimerListener;
import com.view.circulartimerview.CircularTimerView;
import com.view.circulartimerview.TimeFormatEnum;

public class Judge2bTestingScreenRun_WalkActivity extends AppCompatActivity {

    TextView walk_meters;
    private boolean clicked = false;
    int walk_Meter = 10;

    Button RunWalkComplete;
    SharedPreferences preferences;
    Spinner meterGradeSpinner;
    String MeterGrade;

    int check_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge2b_testing_screen_run__walk);
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        walk_meters = findViewById(R.id.meters_walkRuns);
        RunWalkComplete = findViewById(R.id.RunWalkComplete);
        meterGradeSpinner = (Spinner) findViewById(R.id.meterGradeSpinner);



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
                    walk_meters.setText(String.valueOf(walk_Meter));
                    walk_meters.setPaintFlags(walk_meters.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    walk_Meter += 10;
                } else {
                    Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "Test started", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "TEST FINISHED", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("FINISHED THANKS!");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                progressBar.setEnabled(false);
            }
        }, 240, TimeFormatEnum.SECONDS, 10);



        RunWalkComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mGrade = meterGradeSpinner.getSelectedItem().toString();
//
//                if(mGrade.equals("Select One")){
//                    Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "Select Grade", Toast.LENGTH_SHORT).show();
//                }else {
//
//                }
                    SharedPreferences.Editor editor = preferences.edit();


                    if(check_user == 1){

                        if(walk_meters.getText().toString().equals("0")){
                            Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "Enter meter", Toast.LENGTH_SHORT).show();
                        }else{
                            editor.putString("meters",walk_meters.getText().toString());
                            //       editor.putString("meterGrade",mGrade);
                            editor.putInt("check_user", check_user);
                        }

                    }else{
                        if(walk_meters.getText().toString().equals("0")){
                            Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "Enter meter", Toast.LENGTH_SHORT).show();
                        }else{
                            editor.putString("meters1",walk_meters.getText().toString());
                            //        editor.putString("meterGrade1",mGrade);
                            editor.putInt("check_user1", check_user);
                        }
                    }

                    editor.apply();
                    startActivity(new Intent(Judge2bTestingScreenRun_WalkActivity.this,Judge2aRun_Walk_TestActivity.class));
                    finish();
                }


        });
    }


    public void MoveToJudge(View view) {
        startActivity(new Intent(Judge2bTestingScreenRun_WalkActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Judge2bTestingScreenRun_WalkActivity.this,Judge2aRun_Walk_TestActivity.class));
    }
}