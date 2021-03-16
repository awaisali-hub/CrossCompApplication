package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterHorizontal;
import com.eclairios.CrossComps.Adapter.MyCrossCompAllTeamsMainAdapter;
import com.eclairios.CrossComps.Dashboard;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Interface.MyInterface;
import com.eclairios.CrossComps.MainScoreDashboard.Participent;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
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
import java.util.Collection;
import java.util.List;

public class AllTeamCategoryActivity extends AppCompatActivity implements InterfaceForSetTeams {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;


    RecyclerView allTeamRecyclerView;
    MyCrossCompAllTeamsMainAdapter myCrossCompAllTeamsMainAdapter;
    ArrayList<MyCrossCompAllTeamsMainModel> myCrossCompAllTeamsMainModels = new ArrayList<>() ;

    EditText city_team,county_team,conference_team,state_team,union_team,country_team,division_team,world_team;

   // String[] postalCodeForTeams = new String[10000];

    ArrayList postalCodeForTeams = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_team_category);
        getSupportActionBar().hide();

        allTeamRecyclerView = findViewById(R.id.myCrossCompTeamList);
        myCrossCompAllTeamsMainAdapter = new MyCrossCompAllTeamsMainAdapter(AllTeamCategoryActivity.this,myCrossCompAllTeamsMainModels,this);
        allTeamRecyclerView.setAdapter(myCrossCompAllTeamsMainAdapter);


        new BackgroundTask().execute();
    }

    public void TeamsPostalCode() {

        new BackgroundTaskForTeamPostalCode().execute();

        Button saveTeam;


        AlertDialog.Builder builder = new AlertDialog.Builder(AllTeamCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_postal_team_auto_fill,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        city_team = dialogView.findViewById(R.id.city_team);
        county_team = dialogView.findViewById(R.id.county_team);
        conference_team = dialogView.findViewById(R.id.conference_team);
        state_team = dialogView.findViewById(R.id.state_team);
        union_team = dialogView.findViewById(R.id.union_team);
        country_team = dialogView.findViewById(R.id.country_team);
        division_team = dialogView.findViewById(R.id.division_team);
        world_team = dialogView.findViewById(R.id.world_team);

        saveTeam = dialogView.findViewById(R.id.saveTeam);


        postalCodeForTeams.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, postalCodeForTeams);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) dialogView.findViewById(R.id.postal_code_for_team);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);
        


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                new BackgroundTask1().execute();
            }
        });



        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllTeamCategoryActivity.this, TeamsScoreActivity.class));
            }
        });

    }



    public void MoveToFundTeam(View view) {
     startActivity(new Intent(AllTeamCategoryActivity.this, MyFundraisingTeamDetailActivity.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AllTeamCategoryActivity.this, Participent.class));
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


                    int count = 0;
                    String teamId,teamName;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        teamId = JO.getString("Team_ID");
                        teamName = JO.getString("Team_Name");


                        MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                        mainModel.setTeamID(teamId);
                        mainModel.setTeamName(teamName);
                        myCrossCompAllTeamsMainModels.add(mainModel);

                        count++;

                    }

                    myCrossCompAllTeamsMainAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }


    class BackgroundTask1 extends AsyncTask<String, Void, String>
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

            city_team.setText("1");
            county_team.setText("2");
            conference_team.setText("3");
            state_team.setText("4");
            union_team.setText("5");
            country_team.setText("6");
            division_team.setText("7");
            world_team.setText("8");

//            try {
//
//                jsonObject = new JSONObject(json_string);
//                jsonArray = jsonObject.getJSONArray("server_response");
//
//
//                int count = 0;
//                String teamId,teamName;
//                while(count < jsonArray.length())
//                {
//                    JSONObject JO = jsonArray.getJSONObject(count);
//                    teamId = JO.getString("Team_ID");
//                    teamName = JO.getString("Team_Name");
//
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }


    class BackgroundTaskForTeamPostalCode extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("General_TeamID","UTF-8") + "=" + URLEncoder.encode("2","UTF-8");

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
            json_url = "http://edevz.com/cross_comp/getTeamPostalCode.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;


            Log.e("postalcode ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("teams_postal_code");


                int count = 0;
                String teamPostalCode;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamPostalCode = JO.getString("Postal_Code");

                    postalCodeForTeams.add(teamPostalCode);
                    count++;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}