package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterAllTeams;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.Interface.BasicTeamChallenge;
import com.eclairios.CrossComps.Model.ModelAllTeams;
import com.eclairios.CrossComps.R;

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

public class Team_challengeActivity extends AppCompatActivity implements BasicTeamChallenge {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView teamListRecycler;
    AdapterAllTeams adapterAllTeams;
    ArrayList<ModelAllTeams> modelAllTeams = new ArrayList<>();
    String challengeType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_challenge);

        teamListRecycler = findViewById(R.id.TeamList);
        adapterAllTeams= new AdapterAllTeams( modelAllTeams,Team_challengeActivity.this,this);
        teamListRecycler.setAdapter(adapterAllTeams);

        Bundle bundle = getIntent().getExtras();
        challengeType = bundle.getString("challengeType");
    }

    @Override
    public void MakeTeamChallenge(String TeamID, String teamName, String teamScore, String teamAddress) {

        Log.e("teamtest", "MakeTeamChallenge: "+TeamID );
        Log.e("teamtest", "MakeTeamChallenge: "+teamName );
        Log.e("teamtest", "MakeTeamChallenge: "+teamScore );
        Log.e("teamtest", "MakeTeamChallenge: "+teamAddress );

        EditText challengeName;
        Button sendChallenge;
        TextView showChallengeType;

        AlertDialog.Builder builder = new AlertDialog.Builder(Team_challengeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_individual_user_challenge,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        challengeName = dialogView.findViewById(R.id.insertChallengeName);
        sendChallenge = dialogView.findViewById(R.id.sendChallenge);
        showChallengeType = dialogView.findViewById(R.id.ShowChallenge);
        showChallengeType.setText("Challenge To a Team");


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();



        String method = "basicTeamChallenge";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Team_challengeActivity.this);
        String CurrentUserID = preferences.getString("CurrentUserId", "");



        Log.e("teamtest", "MakeTeamChallenge: "+TeamID );
        Log.e("teamtest", "MakeTeamChallenge: "+teamName );
        Log.e("teamtest", "MakeTeamChallenge: "+teamScore );
        Log.e("teamtest", "MakeTeamChallenge: "+teamAddress );


        sendChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_challengeName = challengeName.getText().toString();
                Log.e("challengeName", "MakeIndividualChallenge: "+str_challengeName );
                if(TextUtils.isEmpty(str_challengeName)){
                    Toast.makeText(Team_challengeActivity.this, "Challenge Name Required", Toast.LENGTH_SHORT).show();
                }else{
                    BackgroundTask backgroundTask = new BackgroundTask(Team_challengeActivity.this);
                    backgroundTask.execute(method,CurrentUserID,TeamID,challengeType,str_challengeName,teamScore,teamAddress);
                    pickFileImage.dismiss();
                }
            }
        });


    }

    class BackgroundTasksAllTeams extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Team_challengeActivity.this);
                String serviceID = preferences.getString("service_ID_forChallenge", "");


                Log.e("ServicessssIDIIDDIDI", "doInBackground: "+serviceID );

                String data = URLEncoder.encode("service_ID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/getAllTeamsForChallenges.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("teamsForC ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");


                int count = 0;

                String teamID,teamName,teamScore,teamAddress;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamID = JO.getString("Team_ID");
                    teamName = JO.getString("Team_Name");
                    teamScore = JO.getString("Score");

                    teamAddress = JO.getString("Address");



                    ModelAllTeams allTeams = new ModelAllTeams();
                    allTeams.setTeamID(teamID);
                    allTeams.setTeamName(teamName);
                    allTeams.setTeamScore(teamScore);
                    allTeams.setTeamAddress(teamAddress);

                    modelAllTeams.add(allTeams);

                    count++;

                }

                adapterAllTeams= new AdapterAllTeams( modelAllTeams,Team_challengeActivity.this,Team_challengeActivity.this);
                teamListRecycler.setAdapter(adapterAllTeams);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        modelAllTeams.clear();
        new BackgroundTasksAllTeams().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Team_challengeActivity.this, Challenge.class));
    }
}