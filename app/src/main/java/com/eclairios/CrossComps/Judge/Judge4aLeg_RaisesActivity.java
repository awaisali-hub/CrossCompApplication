package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eclairios.CrossComps.R;

public class Judge4aLeg_RaisesActivity extends AppCompatActivity {

    String user1Name,user2Name;
    TextView participant1, participant2;
    SharedPreferences preferences;

    int check_user,check_user1 ;
    String LegRaises1,LegRaisesGrade1,LegRaises2,LegRaisesGrade2;

    Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge4a_leg__raises);
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
                    Judge4bTestUser2(v);
                }
            });
        }


        participant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Judge4bTestUser1(v);
            }
        });

        LegRaises1 = preferences.getString("LegRaises1", null);
        LegRaisesGrade1 = preferences.getString("LegRaisesGrade1", null);
        check_user = preferences.getInt("check_user",0);


        LegRaises2 = preferences.getString("LegRaises2", null);
        LegRaisesGrade2 = preferences.getString("LegRaisesGrade2", null);
        check_user1 = preferences.getInt("check_user1",0);



        if(LegRaises1 != null && LegRaisesGrade1 != null && check_user == 1 && LegRaises2 != null && LegRaisesGrade2 != null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);

        }else if(LegRaises1 != null && check_user == 1 && LegRaisesGrade2 == null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);
        }
        else if(LegRaises1 != null  && check_user == 1){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }else if( LegRaises2 != null && check_user1 == 2){
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }
        else{
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
        }



    }


    public void Judge4bTestUser1(View view) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LegRaises1",null);
        editor.putString("LegRaisesGrade1",null);
        editor.putInt("check_user",0);
        editor.apply();

        Intent intent = new Intent(Judge4aLeg_RaisesActivity.this,Judge4bTestingScreen_Leg_RaisesActivity.class);
        intent.putExtra("check1","1");
        startActivity(intent);

    }

    public void Judge4bTestUser2(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LegRaises2",null);
        editor.putString("LegRaisesGrade2",null);
        editor.putInt("check_user1",0);
        editor.apply();

        Intent intent = new Intent(Judge4aLeg_RaisesActivity.this,Judge4bTestingScreen_Leg_RaisesActivity.class);
        intent.putExtra("check1","2");
        startActivity(intent);

    }

    public void MoveTo5a_push_ups(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PushUps1",null);
        editor.putString("PushUpsGrade1",null);
        editor.putInt("check_user",0);
        editor.putString("PushUps2",null);
        editor.putString("PushUpsGrade2",null);
        editor.putInt("check_user1",0);
        editor.apply();

        Intent intent = new Intent(Judge4aLeg_RaisesActivity.this,Judge5aPush_UpsActivity.class);
        intent.putExtra("User1Name",user1Name);
        intent.putExtra("User2Name",user2Name);
        startActivity(intent);

    }

    public void judge(View view) {
        startActivity(new Intent(Judge4aLeg_RaisesActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}