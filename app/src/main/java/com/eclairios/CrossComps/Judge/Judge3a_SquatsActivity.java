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

public class Judge3a_SquatsActivity extends AppCompatActivity {
    String user1Name,user2Name;
    TextView participant1, participant2;
    SharedPreferences preferences;

    int check_user,check_user1 ;
    String squats1,squats2,squatGrade1,squatGrade2;

    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge3a__squats);
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
                    moveTo3a2Test(v);
                }
            });
        }


        participant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo3a1Test(v);
            }
        });


        squats1 = preferences.getString("squats1", null);
        squatGrade1 = preferences.getString("squatGrade1", null);
        check_user = preferences.getInt("check_user",0);


        squats2 = preferences.getString("squats2", null);
        squatGrade2 = preferences.getString("squatGrade2", null);
        check_user1 = preferences.getInt("check_user1",0);


        Log.e("jdsajfadsf", "onCreate: "+squats1 );
        Log.e("jdsajfadsf", "onCreate: "+squatGrade1 );
        Log.e("jdsajfadsf", "onCreate: "+check_user );
        Log.e("jdsajfadsf", "onCreate: "+squats2 );
        Log.e("jdsajfadsf", "onCreate: "+squatGrade2 );
        Log.e("jdsajfadsf", "onCreate: "+check_user1 );






        if(squats1 != null && squatGrade1 != null && check_user == 1 && squats2 != null && squatGrade2 != null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);

        }else if(squats1 != null && check_user == 1 && squatGrade2 == null && check_user1 == 2){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
            submitBtn.setClickable(true);
        }
        else if(squats1 != null  && check_user == 1){
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }else if( squats2 != null && check_user1 == 2){
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));

        }
        else{
            participant1.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
            participant2.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
        }


    
    }



    public void moveTo3a1Test(View view) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("squats1",null);
        editor.putString("squatGrade1",null);
        editor.putInt("check_user",0);
        editor.apply();

        Intent intent = new Intent(Judge3a_SquatsActivity.this,Judge3bTesting_Screen_SquatsActivity.class);
        intent.putExtra("check1","1");
        startActivity(intent);
    }
    public void moveTo3a2Test(View view) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("squats2",null);
        editor.putString("squatGrade2",null);
        editor.putInt("check_user1",0);
        editor.apply();

        Intent intent = new Intent(Judge3a_SquatsActivity.this,Judge3bTesting_Screen_SquatsActivity.class);
        intent.putExtra("check1","2");
        startActivity(intent);
    }


    public void MoveTo4bSquats(View view) {
        startActivity(new Intent(Judge3a_SquatsActivity.this,Judge4aLeg_RaisesActivity.class));
    }

    public void jusge(View view) {
        startActivity(new Intent(Judge3a_SquatsActivity.this,JudgeHomePageParticipantRegistrationActivity.class));
    }
}