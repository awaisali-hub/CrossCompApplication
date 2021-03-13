package com.eclairios.CrossComps.MainScoreDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eclairios.CrossComps.Challenges.ChallengeScreen0Activity;
import com.eclairios.CrossComps.CrossComp;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Dashboard;
import com.eclairios.CrossComps.MainActivity;
import com.eclairios.CrossComps.MorePages;
import com.eclairios.CrossComps.Profile.Profile;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Registration;
import com.eclairios.CrossComps.Scores;
import com.eclairios.CrossComps.Services;
import com.eclairios.CrossComps.SplashActivity;
import com.eclairios.CrossComps.Teams.AllTeamCategoryActivity;
import com.google.android.material.tabs.TabLayout;

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
import java.util.ArrayList;

public class Participent extends AppCompatActivity {

    ArrayList<String> values;
    FragmentParent fragmentParent;
    public static int current_card;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participent);
        getSupportActionBar().hide();


        try{
            WaitDialog.showDialog(Participent.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new BackgroundTask().execute();
        values = new ArrayList<>();
        fragmentParent = new FragmentParent();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onResume() {

        if(fragmentParent.adapter!=null){
            if(fragmentParent.adapter.getCount()>0){
                Log.d("mmm", fragmentParent.adapter.getCount()+"");
            /*  for(int i=0; i<fragmentParent.adapter.getCount();i++){
                  Log.d("mmm","woww"+i);
                  fragmentParent.adapter.removeFrag(i);
              }*/
                fragmentParent.removeAllPages();
            }}

        try {
            String id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "idNotFound");

            //   String aVoid = new BCTask(this).execute("http://app.itouchipay.com/api.php?get_cards=true&uid="+id).get();
            Context context=this;
            //  Log.d("asdf",aVoid);

            //=====================================

            //======================================
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("sss","onRestoreinstancestate");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }


    class BackgroundTask extends AsyncTask<String, Void, String>
    {
        String json_url;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Participent.this);
                String currentUserID = preferences.getString("CurrentUserId", "");

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8"); ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine()) != null)
                {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            json_url = "http://edevz.com/cross_comp/get_user_score_details.php";
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            String json_string = result;

                Log.e("bcjknjkksdjc ", "onCreate: "+json_string );
                try {
                    JSONObject jsonObject = new JSONObject(json_string);
                    JSONArray     jsonArray = jsonObject.getJSONArray("server_response");

                    int count = 0;
                    String Score_ID,userId,Date,Age,Meters,MetersGrade,Squats,SquatsGrade,Leg_raises,Leg_raisesGrade,PushUps,PushUpsGrade,Total_Score;

                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        Score_ID = JO.getString("Score_ID");
                        userId = JO.getString("User_ID");
                        Date = JO.getString("Date");
                        Age = JO.getString("Age");
                        Meters = JO.getString("Meters");
                        MetersGrade = JO.getString("Meters_Grade");
                        Squats = JO.getString("Squats");
                        SquatsGrade = JO.getString("Squats_Grade");
                        Leg_raises = JO.getString("Leg_raises");
                        Leg_raisesGrade = JO.getString("Leg_raises_Grade");
                        PushUps = JO.getString("Pushups");
                        PushUpsGrade = JO.getString("Pushup_Grade");
                        Total_Score = JO.getString("Total_Score");

                        double number = Double.parseDouble(Total_Score);
                        String score = String.format("%.1f", number);

                        fragmentParent.addPage(Score_ID,userId,Date,Age,Meters,MetersGrade,Squats,SquatsGrade,Leg_raises,Leg_raisesGrade,PushUps,PushUpsGrade,score);
                        count++;
                    }

                    viewPager.setCurrentItem(viewPager.getAdapter().getCount());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                WaitDialog.hideDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },500);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}