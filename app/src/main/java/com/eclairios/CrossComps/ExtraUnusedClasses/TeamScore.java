package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eclairios.CrossComps.Adapter.AdapterCurrentTeams;
import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.Model.ModelCurrentTeams;
import com.eclairios.CrossComps.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class TeamScore extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONArray jsonArray1;
    JSONArray jsonArray2;
    JSONArray jsonArray3;

    RecyclerView recyclerView3;
    AdapterCurrentTeams adapterCurrentTeams;
    ArrayList<ModelCurrentTeams> chatitemmm = new ArrayList<ModelCurrentTeams>();

    FloatingActionButton joinTeam;

    TextView teamNAME,genderRatio,Address,Score,remarks,DATE;
    String currentUserID,teamId;

    int count = 0;
    float SCORE = 0;
    float totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_score);


        recyclerView3 = findViewById(R.id.LisTeamsName);
        joinTeam = findViewById(R.id.joinTeam);


        teamNAME = findViewById(R.id.teamName_scorePage);
        genderRatio = findViewById(R.id.genderRatio);
        Address = findViewById(R.id.address);
        Score = findViewById(R.id.score);
        remarks = findViewById(R.id.remarks);
        DATE = findViewById(R.id.date);




        Bundle bundle = getIntent().getExtras();
        String floatingButtonDecision = bundle.getString("showFloatingButton");

        if(floatingButtonDecision.equals("allTeams")){
            joinTeam.setVisibility(View.VISIBLE);
        }else{
            joinTeam.setVisibility(View.INVISIBLE);
        }



    }

    public void Teams(View view) {
        startActivity(new Intent(TeamScore.this, CrossCompTeams.class));
    }

    public void joinTeamRequest(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TeamScore.this);
        currentUserID = preferences.getString("currentUserID", "");
        String method = "joinTeam";

        Log.e("join", "joinTeamRequest: "+currentUserID );
        Log.e("join", "joinTeamRequest: "+teamId );

        String JoinTimeScore = String.valueOf(totalScore);

        Log.e("joinTeamScore", "joinTeamRequest: "+JoinTimeScore );

        BackgroundTask backgroundTask = new BackgroundTask(TeamScore.this);
        backgroundTask.execute(method,currentUserID,teamId,JoinTimeScore);
    }

    class BackgroundTasks extends AsyncTask<String, Void, String>
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
                Bundle bundle = getIntent().getExtras();
                String teamID = bundle.getString("teamID");

                Log.e("teamIDDDDDD", "doInBackground: "+teamID );

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TeamScore.this);
                String currentUserID = preferences.getString("CurrentUserId", "");


                String data = URLEncoder.encode("teamID","UTF-8") + "=" + URLEncoder.encode(teamID,"UTF-8") +"&+" +
                        URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")  ;
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
            json_url = "http://edevz.com/cross_comp/get_team_page.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("abcd", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                jsonArray1 = jsonObject.getJSONArray("Score");
                jsonArray2 = jsonObject.getJSONArray("users");
                jsonArray3 = jsonObject.getJSONArray("currentUserScore");



                int count1 =0;
                String teamName,address,score,date;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    JSONObject JO1 = jsonArray1.getJSONObject(count);

                    teamId = JO.getString("Team_ID");
                    teamName = JO.getString("Team_Name");
                    address = JO.getString("Address");
                    score = JO1.getString("Score");
                    date = JO1.getString("Date");

                    float myFloat = Float.parseFloat(score);
                    String formattedString = String.format("%.02f", myFloat);

                    Log.e("details", "onPostExecute: "+ teamId);
                    Log.e("details", "onPostExecute: "+ teamName);
                    Log.e("details", "onPostExecute: "+ address);
                    Log.e("details", "onPostExecute: "+ score);
                    Log.e("details", "onPostExecute: "+ date);


                    float scores = Float.parseFloat(score);

                    if(scores < 19){
                        remarks.setText("Critical");

                    }else if(scores > 20 && scores <39){
                        remarks.setText("Below Average");

                    }else if(scores > 40 && scores <59){
                        remarks.setText("Average");

                    }else if(scores > 60 && scores <79){
                        remarks.setText("Above Average");

                    }else if(scores > 80 && scores <99){
                        remarks.setText("Excellent");

                    }else if(scores > 100){
                        remarks.setText("Champion");

                    }




                    teamNAME.setText(teamName);
                    Address.setText(address);
                    Score.setText(formattedString);
                    DATE.setText(date);

                    count++;

                }

                String userFName;
                String userLName;
                String users;
                String gender;
                String age;
                String Score;

                int maleCount = 0;
                int femaleCount = 0;
                float ageAvg = 0;


                while(count1 < jsonArray2.length())
                {
                    JSONObject JO2 = jsonArray2.getJSONObject(count1);
                    userFName = JO2.getString("First_Name");
                    userLName = JO2.getString("Last_Name");
                    gender = JO2.getString("Gender");
                    age = JO2.getString("Age");
                    Score = JO2.getString("Score");

                    Log.e("scoressss", "onPostExecute: "+Score );

                    SCORE +=  Float.parseFloat(Score);


                    ageAvg += Float.parseFloat(age);


                    users = userFName + " "+userLName;

                    if(gender.equals("Male")){
                        maleCount ++;
                    }else if(gender.equals("Female")){
                        femaleCount ++;
                    }


                    ModelCurrentTeams modelCurrentTeams = new  ModelCurrentTeams();
                    modelCurrentTeams.setTeamUsers(users);
                        Log.e("detailsss", "onPostExecute: "+ users);
                        chatitemmm.add(modelCurrentTeams);

                    count1++;

                }

                float avgAge = ageAvg/count1;
                String formattedAgeString = String.format("%.02f", avgAge);
                genderRatio.setText("M:F "+maleCount +":"+femaleCount +"    "+"Avg Age "+formattedAgeString);

                int count3 = 0;
                String currentUserScore;
                float myScore = 0;

                while(count3<jsonArray3.length())
                {
                    JSONObject JO3 = jsonArray3.getJSONObject(count3);
                    currentUserScore = JO3.getString("Score");
                    myScore += Float.parseFloat(currentUserScore);

                    Log.e("cuscore", "onPostExecute: "+currentUserScore );
                    count3++;
                }

                Log.e("scorefloat", "onPostExecute: "+SCORE );

                float countty = count1+1;

                Log.e("countttttytytytymscore", "onPostExecute: "+myScore );

                float fullScore =SCORE + myScore;

                totalScore = fullScore/ countty;
                Log.e("total", "onPostExecute: "+ totalScore);

                if(chatitemmm != null){
                    adapterCurrentTeams= new AdapterCurrentTeams( chatitemmm,TeamScore.this );
                    recyclerView3.setAdapter(adapterCurrentTeams);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        chatitemmm.clear();
        new BackgroundTasks().execute();
    }

}