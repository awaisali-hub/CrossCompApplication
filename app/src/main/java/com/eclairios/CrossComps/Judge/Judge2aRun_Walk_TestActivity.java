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

import com.eclairios.CrossComps.R;

public class Judge2aRun_Walk_TestActivity extends AppCompatActivity {
    String user1Name,user2Name;
    TextView participant1, participant2;
    String meters,meterGrades,meters1,meterGrade1;
    SharedPreferences preferences;

    int check_user,check_user1 ;

    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge2a_run__walk__test);
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
                    moveTo2bTestUser2Task();
                }
            });
        }


        participant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo2bUser1Task();
            }
        });




        meters = preferences.getString("meters", null);
    //    meterGrades = preferences.getString("meterGrade", null);
        check_user = preferences.getInt("check_user",0);


        meters1 = preferences.getString("meters1", null);
   //     meterGrade1 = preferences.getString("meterGrade1", null);
        check_user1 = preferences.getInt("check_user1",0);


        Log.e("jdsajfadsf", "onCreate: "+meters );
        Log.e("jdsajfadsf", "onCreate: "+check_user );
        Log.e("jdsajfadsf", "onCreate: "+meters1 );
        Log.e("jdsajfadsf", "onCreate: "+check_user1 );



        if(meters != null && check_user == 1 && meters1 != null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);

        }else if(meters != null && check_user == 1 && meters1 == null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);
        }else if(meters != null  && check_user == 1){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }else if( meters1 != null && check_user1 == 2){
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }else{
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
        }




    }

    public void MoveTo3aSquatsTesting(View view) {


        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("squats1",null);
        editor.putString("squatGrade1",null);
        editor.putInt("check_user",0);
        editor.putString("squats2",null);
        editor.putString("squatGrade2",null);
        editor.putInt("check_user1",0);
        editor.apply();

        Intent intent = new Intent(Judge2aRun_Walk_TestActivity.this,Judge3a_SquatsActivity.class);
        intent.putExtra("User1Name",user1Name);
        intent.putExtra("User2Name",user2Name);
        startActivity(intent);
    }

    public void moveTo2bUser1Task() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("meters",null);
      //  editor.putString("meterGrade",null);
        editor.apply();

        Intent intent = new Intent(Judge2aRun_Walk_TestActivity.this,Judge2bTestingScreenRun_WalkActivity.class);
        intent.putExtra("check1","1");
        startActivity(intent);

    }

    public void moveTo2bTestUser2Task() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("meters1",null);
    //    editor.putString("meterGrade1",null);
        editor.apply();

        Intent intent = new Intent(Judge2aRun_Walk_TestActivity.this,Judge2bTestingScreenRun_WalkActivity.class);
        intent.putExtra("check1","2");
        startActivity(intent);
    }


    public void moveToJudge(View view) {
        startActivity(new Intent(Judge2aRun_Walk_TestActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("meters",null);
        editor.putString("meterGrade",null);
        editor.apply();
    }
}