package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Fragment.SwipeAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
import java.text.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Participent extends AppCompatActivity {

    public static TextView scoreStatus;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView userName,userAgeGender,userAddress,userScore,userScoreRemarks,userScoreDate,scoreExpireDate;
    String str_userName, str_userAgeGender,str_userAddress,str_userScore,strUserRemarks,newDateString,str_scoreExpireDate,serviceID;

    ViewPager viewpager;

    Button reservationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participent);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 133),
                new DataPoint(1, 118.2),
                new DataPoint(2, 160),
                new DataPoint(3, 120),
//                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        scoreStatus = findViewById(R.id.scoreStatus);

        viewpager = findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(1);
        SwipeAdapter swipeAdapter= new SwipeAdapter(getSupportFragmentManager());
        viewpager.setAdapter(swipeAdapter);
        viewpager.setCurrentItem(1);



        //////////////////////////

        reservationButton = findViewById(R.id.reservationButton);



        String boldText = "Make a Reservation\n";
        String normalText = "for your next CrossComps";
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        reservationButton.setText(str);
        ////////////////////////////

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

                Log.e("nmz", "onPageSelected: "+(arg0));
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                scoreStatus.setAnimation(animation);
                userScore = findViewById(R.id.userScore);
                if(arg0 == 1 || arg0 == 2){

                    scoreStatus.setVisibility(View.VISIBLE);
                    scoreStatus.setText("Current");
                    userScore.setText("133.0");

                }else{
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    scoreStatus.setAnimation(animation1);
                    scoreStatus.setVisibility(View.GONE);
                    userScore.setText("118.2");
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

                System.out.println("onPageScrolled");
            }

            @Override
            public void onPageScrollStateChanged(int num) {
                // TODO Auto-generated method stub


            }
        });

        ////////////////////////////


        userName = findViewById(R.id.userNAME);
        userAgeGender = findViewById(R.id.userAgeGender);
        userAddress = findViewById(R.id.userAddress);

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