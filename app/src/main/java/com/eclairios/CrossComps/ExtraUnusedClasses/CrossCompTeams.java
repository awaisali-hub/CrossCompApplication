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
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterParticepantTeams;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.Model.ModelParticipentTeams;
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

public class CrossCompTeams extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView recyclerView2;
    AdapterParticepantTeams particepantTeamsAdapter;
    ArrayList<ModelParticipentTeams> chatitemm = new ArrayList<>();

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_comp_teams);
        recyclerView2 = findViewById(R.id.recyclee);
        particepantTeamsAdapter = new AdapterParticepantTeams( chatitemm,CrossCompTeams.this );
        recyclerView2.setAdapter(particepantTeamsAdapter);

    }

    public void JoinTeam(View view) {
        if(count >= 99){
            Toast.makeText(this, "You cannot join more then 99 Teams", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(CrossCompTeams.this, ShowAllTeams.class));
        }
    }

    public void Home(View view) {
        startActivity(new Intent(CrossCompTeams.this, Participent.class));
    }

    public void New_Team_Form(View view) {

        EditText insertTeamName,insertTeamAddress,insertScore;
        Button submitBtn;

        AlertDialog.Builder builder = new AlertDialog.Builder(CrossCompTeams.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_add_new_team,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        insertTeamName = dialogView.findViewById(R.id.insertTeamName);
        insertTeamAddress = dialogView.findViewById(R.id.insertTeamAddress);
        submitBtn = dialogView.findViewById(R.id.submitTeam);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_teamName = insertTeamName.getText().toString();
                String str_teamAddress = insertTeamAddress.getText().toString();

                String method = "insertNewTeam";

                if(TextUtils.isEmpty(str_teamName) && TextUtils.isEmpty(str_teamAddress)){
                    Toast.makeText(CrossCompTeams.this, "All Fields are Required !!!", Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossCompTeams.this);
                    String serviceID = preferences.getString("service_ID_forChallenge", "");
                    String CurrentUserID = preferences.getString("CurrentUserId", "");

                    BackgroundTask backgroundTask = new BackgroundTask(CrossCompTeams.this);
                    backgroundTask.execute(method,str_teamName,str_teamAddress,serviceID,CurrentUserID);
                    pickFileImage.dismiss();
                }

            }
        });

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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossCompTeams.this);
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
            json_url = "http://edevz.com/cross_comp/get_all_teams.php";
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



                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");


                String teamID,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamID = JO.getString("Team_ID");
                    teamName = JO.getString("Team_Name");

                    Log.e("jdjdud", "onPostExecute: "+ teamID);
                    Log.e("jdjdud", "onPostExecute: "+ teamName);



                    ModelParticipentTeams teams = new ModelParticipentTeams();
                    teams.setTeamName(teamName);
                    teams.setActiveUserTeamOrAllTeams("currentUserTeams");
                    teams.setTeamID(teamID);
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
        startActivity(new Intent(CrossCompTeams.this,Participent.class));
    }
}
