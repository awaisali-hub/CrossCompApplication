package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
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
import com.eclairios.CrossComps.Adapter.MyCrossCompUserSelectedTeamAdapter;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.CrossComp;
import com.eclairios.CrossComps.Dashboard;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Interface.MyInterface;
import com.eclairios.CrossComps.MainScoreDashboard.Participent;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Registration;

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
    ArrayList<MyCrossCompAllTeamsMainModel> myCrossCompAllTeamsMainModels = new ArrayList<>();


    RecyclerView myCrossCompTeamList1;
    MyCrossCompUserSelectedTeamAdapter myCrossCompUserSelectedTeamAdapter;
    ArrayList<MyCrossCompAllTeamsMainModel> myCrossCompAllTeamsMainModels2 = new ArrayList<>();

    EditText postal_code_for_team,city_team,county_team,conference_team,state_team,union_team,country_team,division_team,world_team;

    ArrayList postalCodeForTeams = new ArrayList();

    String SelectedPostalCode;
    String SelectedTeamGeneralID;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_team_category);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AllTeamCategoryActivity.this);
        currentUserID = preferences.getString("CurrentUserId", "");

        allTeamRecyclerView = findViewById(R.id.myCrossCompTeamList);
        myCrossCompAllTeamsMainAdapter = new MyCrossCompAllTeamsMainAdapter(AllTeamCategoryActivity.this,myCrossCompAllTeamsMainModels,this);
        allTeamRecyclerView.setAdapter(myCrossCompAllTeamsMainAdapter);


        myCrossCompTeamList1 = findViewById(R.id.myCrossCompTeamList1);
        myCrossCompUserSelectedTeamAdapter = new MyCrossCompUserSelectedTeamAdapter(AllTeamCategoryActivity.this,myCrossCompAllTeamsMainModels2,this);
        myCrossCompTeamList1.setAdapter(myCrossCompUserSelectedTeamAdapter);


        new BackgroundTaskMySelectedTeam().execute();
        new BackgroundTaskGetAllTeams().execute();
    }

    public void TeamsPostalCode(String SelectedTeamID) {

        SelectedTeamGeneralID = SelectedTeamID;

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
                Log.e("postttalcode", "onItemClick: "+postalCodeForTeams.get(position));
                SelectedPostalCode = (String) postalCodeForTeams.get(position);
                new BackgroundTask1().execute();
            }
        });



        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String method = "insertUserTeamSelected_url";
                String postalCode = acTextView.getText().toString();
                String str_city_team = city_team.getText().toString();
                String str_county_team = county_team.getText().toString();
                String str_conference_team = conference_team.getText().toString();
                String str_state_team = state_team.getText().toString();
                String str_union_team = union_team.getText().toString();
                String str_country_team = country_team.getText().toString();
                String str_division_team = division_team.getText().toString();
                String str_world_team = world_team.getText().toString();


                if (TextUtils.isEmpty(postalCode) || TextUtils.isEmpty(str_city_team) || TextUtils.isEmpty(str_county_team)
                        || TextUtils.isEmpty(str_conference_team) || TextUtils.isEmpty(str_state_team) || TextUtils.isEmpty(str_union_team)
                        || TextUtils.isEmpty(str_country_team) || TextUtils.isEmpty(str_division_team) || TextUtils.isEmpty(str_world_team)) {
                    Toast.makeText(AllTeamCategoryActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                } else {


                    BackgroundTask backgroundTask = new BackgroundTask(AllTeamCategoryActivity.this);
                    backgroundTask.execute(method, SelectedTeamGeneralID, currentUserID, postalCode, str_city_team, str_county_team, str_conference_team, str_state_team, str_union_team, str_country_team, str_division_team, str_world_team);

                }
            }
        });

    }


    public void TeamsChurch(String SelectedTeamID){

        SelectedTeamGeneralID = SelectedTeamID;

        Button saveTeam;

        AlertDialog.Builder builder = new AlertDialog.Builder(AllTeamCategoryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_church_auto_fill,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        saveTeam = dialogView.findViewById(R.id.saveTeam);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myFaithTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.My_Faith_Team_team_church);
        myFaithTeam.setThreshold(1);
        myFaithTeam.setAdapter(adapter);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myDivisionTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.MyDivisionTeam_church);
        myDivisionTeam.setThreshold(1);
        myDivisionTeam.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myUnionTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.MyUnionTeam_church);
        myUnionTeam.setThreshold(1);
        myUnionTeam.setAdapter(adapter2);


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myConferenceTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.MyConferenceTeam_church);
        myConferenceTeam.setThreshold(1);
        myConferenceTeam.setAdapter(adapter3);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        saveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String method = "insertUserChurchTeamSelected";
                String FaithTeam = myFaithTeam.getText().toString();
                String DivisionTeam = myDivisionTeam.getText().toString();
                String UnionTeam = myUnionTeam.getText().toString();
                String ConferenceTeam = myConferenceTeam.getText().toString();

                if(TextUtils.isEmpty(FaithTeam) || TextUtils.isEmpty(DivisionTeam) || TextUtils.isEmpty(UnionTeam)
                        || TextUtils.isEmpty(ConferenceTeam) ){
                    Toast.makeText(AllTeamCategoryActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }else{


                        BackgroundTask backgroundTask = new BackgroundTask(AllTeamCategoryActivity.this);
                        backgroundTask.execute(method,SelectedTeamGeneralID,currentUserID,FaithTeam, DivisionTeam, UnionTeam, ConferenceTeam);


                }

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

    public void MyWorldTeam(View view) {
        startActivity(new Intent(AllTeamCategoryActivity.this, TeamsScoreActivity.class));
    }


    class BackgroundTaskGetAllTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8");

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

                String data = URLEncoder.encode("SelectedPostalCode","UTF-8") + "=" + URLEncoder.encode(SelectedPostalCode,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_teams_accordingTo_postal.php";
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
                jsonArray = jsonObject.getJSONArray("selected_postal_code_teams");


                int count = 0;
                String City_Team,County_Team,Conference_Team,State_Team,Union_Team,Country_Team,Division_Team,World_Team;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    City_Team = JO.getString("City_Team");
                    County_Team = JO.getString("County_Team");
                    Conference_Team = JO.getString("Conference_Team");
                    State_Team = JO.getString("State_Team");
                    Union_Team = JO.getString("Union_Team");
                    Country_Team = JO.getString("Country_Team");
                    Division_Team = JO.getString("Division_Team");
                    World_Team = JO.getString("World_Team");


                    city_team.setText(City_Team);
                    county_team.setText(County_Team);
                    conference_team.setText(Conference_Team);
                    state_team.setText(State_Team);
                    union_team.setText(Union_Team);
                    country_team.setText(Country_Team);
                    division_team.setText(Division_Team);
                    world_team.setText(World_Team);

                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

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

                String data = URLEncoder.encode("General_TeamID","UTF-8") + "=" + URLEncoder.encode(SelectedTeamGeneralID,"UTF-8");

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

                    if(!postalCodeForTeams.contains(teamPostalCode)){
                        postalCodeForTeams.add(teamPostalCode);
                    }
                    count++;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    class BackgroundTaskMySelectedTeam extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/getUserSelectedTeamGreen.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string!=null){
                Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("selected_teams");


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
                        myCrossCompAllTeamsMainModels2.add(mainModel);
                        count++;

                    }
                    myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}