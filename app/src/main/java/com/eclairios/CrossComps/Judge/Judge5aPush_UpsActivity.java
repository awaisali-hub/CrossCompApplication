package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Judge5aPush_UpsActivity extends AppCompatActivity {

    String user1Name,user2Name;
    TextView participant1, participant2;
    SharedPreferences preferences;

    int check_user,check_user1 ;
    String PushUps1,PushUpsGrade1,PushUps2,PushUpsGrade2;

    Button submitBtn;

    int gradePoint1,gradePoint2,gradePoint3,gradePoint4;
    double totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge5a_push__ups);
        getSupportActionBar().hide();


        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        participant1 = findViewById(R.id.participant1Name);
        participant2 = findViewById(R.id.participant2Name);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setClickable(false);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            user1Name = extras.getString("User1Name");
            user2Name = extras.getString("User2Name");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user1Name",user1Name);
            editor.putString("user2Name",user2Name);
            editor.apply();
        }else{
            user1Name = preferences.getString("user1Name", "");
            user2Name = preferences.getString("user2Name", "");
        }

        if(user2Name.equals("None")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("check_user1",2);
            editor.apply();
            participant1.setText(user1Name);
            participant2.setText("");
            participant2.setClickable(false);
        }else{
            participant1.setText(user1Name);
            participant2.setText(user2Name);

            participant2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Judge5bTestUser2(v);
                }
            });
        }


        participant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Judge5bTestUser1(v);
            }
        });


        PushUps1 = preferences.getString("PushUps1", null);
        PushUpsGrade1 = preferences.getString("PushUpsGrade1", null);
        check_user = preferences.getInt("check_user",0);


        PushUps2 = preferences.getString("PushUps2", null);
        PushUpsGrade2 = preferences.getString("PushUpsGrade2", null);
        check_user1 = preferences.getInt("check_user1",0);



        if(PushUps1 != null && PushUpsGrade1 != null && check_user == 1 && PushUps2 != null && PushUpsGrade2 != null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);

        }else if(PushUps1 != null && check_user == 1 && PushUpsGrade2 == null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);
        }
        else if(PushUps1 != null  && check_user == 1){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }else if( PushUps2 != null && check_user1 == 2){
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }
        else{
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
        }



    }

    private void Judge5bTestUser1(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PushUps1",null);
        editor.putString("PushUpsGrade1",null);
        editor.putInt("check_user",0);
        editor.apply();

        Intent intent = new Intent(Judge5aPush_UpsActivity.this,Judge5bTestingScreen_Push_UpsActivity.class);
        intent.putExtra("check1","1");
        startActivity(intent);
    }

    private void Judge5bTestUser2(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PushUps2",null);
        editor.putString("PushUpsGrade2",null);
        editor.putInt("check_user1",0);
        editor.apply();

        Intent intent = new Intent(Judge5aPush_UpsActivity.this,Judge5bTestingScreen_Push_UpsActivity.class);
        intent.putExtra("check1","2");
        startActivity(intent);
    }


    public void judgeHomePage(View view) {
        startActivity(new Intent(Judge5aPush_UpsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

    public void SubmitAllJudgeTestData(View view) {

        String method = "judgeInsertUserScore";
        BackgroundTask backgroundTask = new BackgroundTask(Judge5aPush_UpsActivity.this);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            // for participant 1
            String User1ID = preferences.getString("User1ID",null);
            String RunWalk1 = preferences.getString("meters", null);
            String Squats1 = preferences.getString("squats1", null);
            String GradeSquats1 = preferences.getString("squatGrade1", null);
            String Leg_Raises1 = preferences.getString("LegRaises1", null);
            String GradeLeg_Raises1 = preferences.getString("LegRaisesGrade1", null);
            String PushUp1 = preferences.getString("PushUps1", null);
            String GradePushUp1 = preferences.getString("PushUpsGrade1", null);

        // for participant 2
        String User2ID = preferences.getString("User2ID",null);
        String RunWalk2 = preferences.getString("meters1", null);
        String Squats2 = preferences.getString("squats2", null);
        String GradeSquats2 = preferences.getString("squatGrade2", null);
        String Leg_Raises2 = preferences.getString("LegRaises2", null);
        String GradeLeg_Raises2 = preferences.getString("LegRaisesGrade2", null);
        String PushUp2 = preferences.getString("PushUps2", null);
        String GradePushUp2 = preferences.getString("PushUpsGrade2", null);



            float run = Float.parseFloat(RunWalk1);
            float squats = Float.parseFloat(Squats1);
            float leg_raises = Float.parseFloat(Leg_Raises1);
            float push_ups = Float.parseFloat(PushUp1);

            gradePoint2 = preferences.getInt("gradePoint2",0);
            gradePoint3 = preferences.getInt("gradePoint3",0);
            gradePoint4 = preferences.getInt("gradePoint4",0);


            float a = (run/800)*100;
            float b = ((squats/120)*100)-gradePoint2;
            float c = ((leg_raises/60)*100)-gradePoint3;
            float d = ((push_ups/30)*100)-gradePoint4;

        Log.e("dsfh", "SubmitAllJudgeTestData: "+a);
        Log.e("dsfh", "SubmitAllJudgeTestData: "+b);
        Log.e("dsfh", "SubmitAllJudgeTestData: "+c);
        Log.e("dsfh", "SubmitAllJudgeTestData: "+d);

        totalScore = (a+b+c+d)/4;
        Log.e("score", "SubmitAllJudgeTestData: "+totalScore );


            backgroundTask.execute(method, User1ID, date, RunWalk1, "A",Squats1, GradeSquats1, Leg_Raises1, GradeLeg_Raises1, PushUp1, GradePushUp1, String.valueOf(totalScore));



//            if(User2ID!=null){
//                if(GradeSquats2.equals("A") || GradeLeg_Raises2.equals("A") || GradePushUp2.equals("A")){
//                    gradePoint = 0;
//                }else if (GradeSquats2.equals("B") || GradeLeg_Raises2.equals("B") || GradePushUp2.equals("B")){
//                    gradePoint = 10;
//                }else if (GradeSquats2.equals("C") || GradeLeg_Raises2.equals("C") || GradePushUp2.equals("C")){
//                    gradePoint = 20;
//                }else{
//                    gradePoint = 30;
//                }
//            }
//                if(User2ID!=null){
//                    backgroundTask.execute(method, User2ID, date, RunWalk2, "A",Squats2, GradeSquats2, Leg_Raises2, GradeLeg_Raises2, PushUp2, GradePushUp2,"150");
//                }


        startActivity(new Intent(Judge5aPush_UpsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));

    }
}