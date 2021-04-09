package com.eclairios.CrossComps.MainFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.MyCrossCompAllTeamsMainAdapter;
import com.eclairios.CrossComps.Adapter.MyCrossCompUserSelectedTeamAdapter;
import com.eclairios.CrossComps.Authentication.Registration;
import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Model.CityModel;
import com.eclairios.CrossComps.Model.CountryModel;
import com.eclairios.CrossComps.Model.JoinNewTeamModel;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
import com.eclairios.CrossComps.Model.PostalCodeModel;
import com.eclairios.CrossComps.Model.StateModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.CoordinatorRegistration4a_4FacilityActivity;
import com.eclairios.CrossComps.ServiceCoordinator.CoordinatorServicesScreenActivity;
import com.eclairios.CrossComps.Teams.JoinTeamsActivity;
import com.eclairios.CrossComps.Teams.MyFundraisingTeamDetailActivity;
import com.eclairios.CrossComps.Teams.TeamsScoreActivity;

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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TeamsFragment extends Fragment implements InterfaceForSetTeams{

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4,jsonArray5,jsonArray6;
    JSONArray jsonArray7,jsonArray8,jsonArray9,jsonArray10,jsonArray11,jsonArray12,jsonArray13,jsonArray14,jsonArray15;
    JSONArray jsonArray16, jsonArray17, jsonArray18, jsonArray19, jsonArray20, jsonArray21, jsonArray22, jsonArray23;

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
    Button joinTeamBtn;


    Button saveHighSchoolTeamBtn,becomeVolunteerBtn;

    String HighSchoolTeamID,HighSchoolClassTeamID,CollegeTeamID,CollegeClassTeamID,ProfessionalSchoolID,ProfessionalSchoolClassID,MilitaryBranchID,MilitaryBranchLocalID,
            OccupationID,OccupationLocalID,CompanyID,CompanyLocalID,FaithGroupID,FaithGroupLocalID,GymBrandID,GymBrandLocalID,FriendFamilyID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teams, container, false);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        currentUserID = preferences.getString("CurrentUserId", "");

        joinTeamBtn = view.findViewById(R.id.JoinATeam);

        joinTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JoinTeamsActivity.class));
            }
        });


        allTeamRecyclerView = view.findViewById(R.id.myCrossCompTeamList);
        myCrossCompAllTeamsMainAdapter = new MyCrossCompAllTeamsMainAdapter(getContext(),myCrossCompAllTeamsMainModels, this);
        allTeamRecyclerView.setAdapter(myCrossCompAllTeamsMainAdapter);


        myCrossCompTeamList1 = view.findViewById(R.id.myCrossCompTeamList1);
        myCrossCompUserSelectedTeamAdapter = new MyCrossCompUserSelectedTeamAdapter(getContext(),myCrossCompAllTeamsMainModels2, this);
        myCrossCompTeamList1.setAdapter(myCrossCompUserSelectedTeamAdapter);



    //    new BackgroundTaskMySelectedTeam().execute();
    //    new BackgroundTaskGetAllTeams().execute();

        new BackgroundTaskForDefaultTeams().execute();
    //    new BackgroundTaskForCustomTeamSelect().execute();


        return view;


    }

    @Override
    public void TeamsPostalCode(String SelectedGeneralTeamID) {
        
    }

    @Override
    public void TeamsChurch(String SelectedTeamID) {

    }

    @Override
    public void SelectHighSchool() {

    }

    @Override
    public void CollegeUniversity() {

    }

    @Override
    public void ProfessionalSchool() {

    }

    @Override
    public void MilitaryBranch() {

    }

    @Override
    public void Occupation() {

    }

    @Override
    public void Company() {

    }

    @Override
    public void Faith() {

    }

    @Override
    public void GymBrand() {

    }

    @Override
    public void FriendFamily() {

    }

    @Override
    public void SelectCommunity() {

    }


    class BackgroundTaskForDefaultTeams extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/crossCompsDefaultTeams.php";
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
                    jsonArray = jsonObject.getJSONArray("worldTeamMember");
                    jsonArray1 = jsonObject.getJSONArray("homeTeamMember");
                    jsonArray2 = jsonObject.getJSONArray("countryTeamMember");
                    jsonArray3 = jsonObject.getJSONArray("stateTeamMember");
                    jsonArray4 = jsonObject.getJSONArray("cityTeamMember");
                    jsonArray5 = jsonObject.getJSONArray("communityTeamMember");

                    jsonArray7 = jsonObject.getJSONArray("HighSchoolTeamMember");
                    jsonArray8 = jsonObject.getJSONArray("CollegeClassTeamMember");
                    jsonArray9 = jsonObject.getJSONArray("ProfessionalSchoolTeamMember");
                    jsonArray10 = jsonObject.getJSONArray("FaithGroupTeamMember");
                    jsonArray11 = jsonObject.getJSONArray("GymTeamMember");
                    jsonArray12 = jsonObject.getJSONArray("CompanyTeamMember");
                    jsonArray13 = jsonObject.getJSONArray("OccupationTeamMember");
                    jsonArray14 = jsonObject.getJSONArray("MilitaryTeamMember");
                    jsonArray15 = jsonObject.getJSONArray("FriendAndFamilyTeamMember");

                    jsonArray16 = jsonObject.getJSONArray("HighSchoolClassTeamMember");
                    jsonArray17 = jsonObject.getJSONArray("CollegeSubClassTeamMember");
                    jsonArray18 = jsonObject.getJSONArray("ProfessionalSchoolClassTeamMember");
                    jsonArray19 = jsonObject.getJSONArray("FaithGroupLocalTeamMember");
                    jsonArray20 = jsonObject.getJSONArray("GymLocalTeamMember");
                    jsonArray21 = jsonObject.getJSONArray("CompanyLocalTeamMember");
                    jsonArray22 = jsonObject.getJSONArray("OccupationLocalTeamMember");
                    jsonArray23 = jsonObject.getJSONArray("MilitaryLocalTeamMember");

                    int count = 0;
                    String teamId,teamName;
                    if(jsonArray!=null){
                        while(count < jsonArray.length())
                        {
                            JSONObject JO = jsonArray.getJSONObject(count);
                            teamId = JO.getString("WorldTeamID");
                            teamName = JO.getString("WorldTeamName");

                            Log.e("hgjghrgheghf", "onPostExecute: "+teamId);

                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("WorldTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count++;

                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count1 = 0;
                    if(jsonArray1 != null){
                        while(count1 < jsonArray1.length())
                        {
                            JSONObject JO = jsonArray1.getJSONObject(count1);
                            teamId = JO.getString("HomeTeamID");
                            teamName = JO.getString("HomeTeamName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("HomeTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count1++;

                        }

                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count2 = 0;
                    if(jsonArray2 != null){
                        while(count2 < jsonArray2.length())
                        {
                            JSONObject JO = jsonArray2.getJSONObject(count2);
                            teamId = JO.getString("CountryTeamID");
                            teamName = JO.getString("CountryTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CountryTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count2++;

                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count3 = 0;
                    if(jsonArray3 != null){
                        while(count3 < jsonArray3.length())
                        {
                            JSONObject JO = jsonArray3.getJSONObject(count3);
                            teamId = JO.getString("StateTeamID");
                            teamName = JO.getString("StateTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("StateTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count3++;

                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count4 = 0;
                    if(jsonArray4 != null){
                        while(count4 < jsonArray4.length())
                        {
                            JSONObject JO = jsonArray4.getJSONObject(count4);
                            teamId = JO.getString("CityTeamID");
                            teamName = JO.getString("CityTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CityTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count4++;

                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count5 = 0;
                    if(jsonArray5 != null){
                        while(count5 < jsonArray5.length())
                        {
                            JSONObject JO = jsonArray5.getJSONObject(count5);
                            teamId = JO.getString("CommunityTeamID");
                            teamName = JO.getString("CommunityTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CommunityTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count5++;

                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count7 = 0;
                    if(jsonArray7 != null){
                        while(count7 < jsonArray7.length())
                        {
                            JSONObject JO = jsonArray7.getJSONObject(count7);
                            teamId = JO.getString("HighSchoolTeamID");
                            teamName = JO.getString("HighSchoolTeamName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("HighSchoolTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count7++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count16 = 0;
                    if(jsonArray16 != null){
                        while(count16 < jsonArray16.length())
                        {
                            JSONObject JO = jsonArray16.getJSONObject(count16);
                            teamId = JO.getString("HighSchoolClassTeamID");
                            teamName = JO.getString("HS_SubClassName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("HighSchoolClassTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count16++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count8 = 0;
                    if(jsonArray8 != null){
                        while(count8 < jsonArray8.length())
                        {
                            JSONObject JO = jsonArray8.getJSONObject(count8);
                            teamId = JO.getString("CollegeUniversityTeamID");
                            teamName = JO.getString("CollegeTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CollegeTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count8++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count17 = 0;
                    if(jsonArray17 != null){
                        while(count17 < jsonArray17.length())
                        {
                            JSONObject JO = jsonArray17.getJSONObject(count17);
                            teamId = JO.getString("CollegeUniversityClassTeamID");
                            teamName = JO.getString("CU_SubClassName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CollegeSubTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count17++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count9 = 0;
                    if(jsonArray9 != null){
                        while(count9 < jsonArray9.length())
                        {
                            JSONObject JO = jsonArray9.getJSONObject(count9);
                            teamId = JO.getString("ProfessionalSchoolTeamID");
                            teamName = JO.getString("ProfessionalTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("ProfessionalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count9++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count18 = 0;
                    if(jsonArray18 != null){
                        while(count18 < jsonArray18.length())
                        {
                            JSONObject JO = jsonArray18.getJSONObject(count18);
                            teamId = JO.getString("ProfessionalSchoolClassTeamID");
                            teamName = JO.getString("PS_SubClassName");

                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("ProfessionalClassTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count18++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count10 = 0;
                    if(jsonArray10 != null){
                        while(count10 < jsonArray10.length())
                        {
                            JSONObject JO = jsonArray10.getJSONObject(count10);
                            teamId = JO.getString("FaithTeamID");
                            teamName = JO.getString("FaithTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("FaithTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count10++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count19 = 0;
                    if(jsonArray19 != null){
                        while(count19 < jsonArray19.length())
                        {
                            JSONObject JO = jsonArray19.getJSONObject(count19);
                            teamId = JO.getString("LocalFaithGroupTeamID");
                            teamName = JO.getString("Faith_SubClassName");



                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("FaithLocalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count19++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count11 = 0;
                    if(jsonArray11 != null){
                        while(count11 < jsonArray11.length())
                        {
                            JSONObject JO = jsonArray11.getJSONObject(count11);
                            teamId = JO.getString("GymBrandTeamID");
                            teamName = JO.getString("GymTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("GymTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count11++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count20 = 0;
                    if(jsonArray20 != null){
                        while(count20 < jsonArray20.length())
                        {
                            JSONObject JO = jsonArray20.getJSONObject(count20);
                            teamId = JO.getString("LocalGymTeamID");
                            teamName = JO.getString("Gym_SubClassName");

                            Log.e("fsdfds", "onPostExecute: "+teamName);

                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("GymLocalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count20++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count12 = 0;
                    if(jsonArray12 != null){
                        while(count12 < jsonArray12.length())
                        {
                            JSONObject JO = jsonArray12.getJSONObject(count12);
                            teamId = JO.getString("CompanyTeamID");
                            teamName = JO.getString("CompanyTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CompanyTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count12++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count21 = 0;
                    if(jsonArray21 != null){
                        while(count21 < jsonArray21.length())
                        {
                            JSONObject JO = jsonArray21.getJSONObject(count21);
                            teamId = JO.getString("LocalCompanyGroupTeamID");
                            teamName = JO.getString("Comapny_SubClassName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CompanyLocalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count21++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count13 = 0;
                    if(jsonArray13 != null){
                        while(count13 < jsonArray13.length())
                        {
                            JSONObject JO = jsonArray13.getJSONObject(count13);
                            teamId = JO.getString("OccupationTeamID");
                            teamName = JO.getString("OccupationTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("OccupationTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count13++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count22 = 0;
                    if(jsonArray22 != null){
                        while(count22 < jsonArray22.length())
                        {
                            JSONObject JO = jsonArray22.getJSONObject(count22);
                            teamId = JO.getString("OccupationLocalGroupTeamID");
                            teamName = JO.getString("Occupation_SubClassName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("OccupationLocalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count22++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count14 = 0;
                    if(jsonArray14 != null){
                        while(count14 < jsonArray14.length())
                        {
                            JSONObject JO = jsonArray14.getJSONObject(count14);
                            teamId = JO.getString("MilitaryTeamID");
                            teamName = JO.getString("MilitaryGroupTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("MilitaryTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("MainClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count14++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count23 = 0;
                    if(jsonArray23 != null){
                        while(count23 < jsonArray23.length())
                        {
                            JSONObject JO = jsonArray23.getJSONObject(count23);
                            teamId = JO.getString("MilitaryGroupTeamID");
                            teamName = JO.getString("Military_SubClassName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("MilitaryLocalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count23++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }

                    int count15 = 0;
                    if(jsonArray15 != null){
                        while(count15 < jsonArray15.length())
                        {
                            JSONObject JO = jsonArray15.getJSONObject(count15);
                            teamId = JO.getString("FriendFamilyTeamID");
                            teamName = JO.getString("MyFriendsFamilyTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("FriendTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamCategory("SubClass");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count15++;
                        }
                        myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}