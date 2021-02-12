package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Fragment.SwipeAdapter;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Participent extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView userName,userAgeGender,userAddress,userScore,userScoreRemarks,userScoreDate,scoreExpireDate;
    String str_userName, str_userAgeGender,str_userAddress,str_userScore,strUserRemarks,newDateString,str_scoreExpireDate,serviceID;

    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participent);


        viewpager = findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(1);
        SwipeAdapter swipeAdapter= new SwipeAdapter(getSupportFragmentManager());
        viewpager.setAdapter(swipeAdapter);
        viewpager.setCurrentItem(1);


        userName = findViewById(R.id.userNAME);
        userAgeGender = findViewById(R.id.userAgeGender);
        userAddress = findViewById(R.id.userAddress);
        userScore = findViewById(R.id.userScore);
        userScoreRemarks = findViewById(R.id.userRemarks);
        userScoreDate = findViewById(R.id.userScoreDate);
        scoreExpireDate = findViewById(R.id.userScoreExpireDate);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            str_userName = bundle.getString("userName");
            str_userAgeGender = bundle.getString("userAgeGender");
            str_userAddress = bundle.getString("userAddress");
            str_userScore = bundle.getString("userScore");
            strUserRemarks = bundle.getString("userScoreRemarks");
            newDateString = bundle.getString("userScoreDate");
            str_scoreExpireDate = bundle.getString("scoreExpireDate");
            serviceID = bundle.getString("serviceID");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("userName",str_userName);
            editor.putString("userAgeGender",str_userAgeGender);
            editor.putString("userAddress",str_userAddress);
            editor.putString("userScore",str_userScore);
            editor.putString("userScoreRemarks",strUserRemarks);
            editor.putString("userScoreDate",newDateString);
            editor.putString("scoreExpireDate",str_scoreExpireDate);
            editor.putString("serviceID",serviceID);
            editor.apply();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Participent.this);
            str_userName = preferences.getString("userName", "");
            str_userAgeGender = preferences.getString("userAgeGender", "");
            str_userAddress = preferences.getString("userAddress", "");
            str_userScore = preferences.getString("userScore", "");
            strUserRemarks = preferences.getString("userScoreRemarks", "");
            newDateString = preferences.getString("userScoreDate", "");
            str_scoreExpireDate = preferences.getString("scoreExpireDate", "");
            serviceID = preferences.getString("serviceID","");
        }





//        userName.setText(str_userName);
//        userAgeGender.setText(str_userAgeGender);
//        userAddress.setText(str_userAddress);
//        userScore.setText(str_userScore);
//        userScoreRemarks.setText(strUserRemarks);
//        userScoreDate.setText(newDateString);
//        scoreExpireDate.setText(str_scoreExpireDate);
    }

    public void moveToScore(View view) {
        startActivity(new Intent(Participent.this,Scores.class));
    }

    public void moveToTeams(View view) {
        startActivity(new Intent(Participent.this,CrossCompTeams.class));
    }

    public void moveToChallenge(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("service_ID_forChallenge",serviceID);
        editor.apply();

        Log.e("serviceIDChallenges", "moveToChallenge: "+serviceID);

        Intent intent = new Intent(Participent.this,Challenge.class);
        startActivity(intent);
    }

    public void moveToService(View view) {
        startActivity(new Intent(Participent.this,Services.class));
    }

    public void moveToProfile(View view) {
        startActivity(new Intent(Participent.this,Profile.class));
    }

    public void moveToMorePages(View view) {
        startActivity(new Intent(Participent.this,MorePages.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}