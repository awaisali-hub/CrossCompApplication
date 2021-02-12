package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.eclairios.CrossComps.Adapter.AdapterParticepantTeams;
import com.eclairios.CrossComps.Model.ModelParticipentTeams;

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

public class ShowAllTeams extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView recyclerView2;
    AdapterParticepantTeams particepantTeamsAdapter;
    ArrayList<ModelParticipentTeams> chatitemm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_teams);

        recyclerView2 = findViewById(R.id.allTeamRecycle);
        particepantTeamsAdapter = new AdapterParticepantTeams( chatitemm,ShowAllTeams.this );
        recyclerView2.setAdapter(particepantTeamsAdapter);
    }

    public void Home(View view) {

        startActivity(new Intent(ShowAllTeams.this,Participent.class));

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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ShowAllTeams.this);
                String currentUserID = preferences.getString("CurrentUserId", "");

                Log.e("jhgg", "doInBackground: "+currentUserID );

                String data = URLEncoder.encode("User_ID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") ;
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
            json_url = "http://edevz.com/cross_comp/joinAllteams.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                Log.e("dhdhdhgd", "onPostExecute: "+jsonArray);


                int count = 0;


                String teamID,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamID = JO.getString("Team_ID");
                    teamName = JO.getString("Team_Name");


                    Log.e("jdjdudfgfgf", "onPostExecute: "+ teamName);
                    Log.e("jdjdudfgfgf", "onPostExecute: "+ teamID);


                    ModelParticipentTeams teams = new ModelParticipentTeams();
                    teams.setTeamID(teamID);
                    teams.setTeamName(teamName);
                    teams.setActiveUserTeamOrAllTeams("allTeams");

                    chatitemm.add(teams);
                    count++;

                }

                Log.e("fhfhfhf", "onPostExecute: "+count);
                particepantTeamsAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatitemm.clear();
        new BackgroundTasks().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowAllTeams.this,Participent.class));
    }
}