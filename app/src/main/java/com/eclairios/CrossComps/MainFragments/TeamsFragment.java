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


public class TeamsFragment extends Fragment  implements InterfaceForSetTeams {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3,jsonArray4,jsonArray5,jsonArray6;
    JSONArray jsonArray7,jsonArray8,jsonArray9,jsonArray10,jsonArray11,jsonArray12,jsonArray13,jsonArray14,jsonArray15;

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
    Button myWorldTeam;



    ArrayList<JoinNewTeamModel> highSchoolTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> highSchoolTeam_adapter;

    ArrayList<JoinNewTeamModel> collegeUniversityTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> collegeUniversityTeam_adapter;

    ArrayList<JoinNewTeamModel> professionalSchoolTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> professionalSchoolTeam_adapter;

    ArrayList<JoinNewTeamModel> militaryBranchTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> militaryBranchTeam_adapter;

    ArrayList<JoinNewTeamModel> occupationTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> occupationTeam_adapter;

    ArrayList<JoinNewTeamModel> companyTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> companyTeam_adapter;

    ArrayList<JoinNewTeamModel> faithTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> faithTeam_adapter;

    ArrayList<JoinNewTeamModel> gymBrandTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> gymBrandTeam_adapter;

    ArrayList<JoinNewTeamModel> friendFamilyTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> friendFamilyTeam_adapter;


    AutoCompleteTextView highSchoolTeamNameAutoText,collegeTeamNameAutoText,professionalSchoolTeamNameAutoText,
            militaryBranchAutoText,occupationTeamNameAutoText,companyTeamNameAutoText,faithTeamNameAutoText,
            gymBrandTeamNameAutoText,friendFamilyTeamNameAutoText;


    Button saveHighSchoolTeamBtn,becomeVolunteerBtn;
    String HighSchoolTeamID,CollegeTeamID,ProfessionalSchoolID,MilitaryBranchID,OccupationID,CompanyID,FaithGroupID,GymBrandID,FriendFamilyID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        myWorldTeam = view.findViewById(R.id.MyWorldTeam);
        myWorldTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWorldTeam(view);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        currentUserID = preferences.getString("CurrentUserId", "");

//        allTeamRecyclerView = view.findViewById(R.id.myCrossCompTeamList);
//        myCrossCompAllTeamsMainAdapter = new MyCrossCompAllTeamsMainAdapter(getContext(),myCrossCompAllTeamsMainModels,this);
//        allTeamRecyclerView.setAdapter(myCrossCompAllTeamsMainAdapter);


        myCrossCompTeamList1 = view.findViewById(R.id.myCrossCompTeamList1);
        myCrossCompUserSelectedTeamAdapter = new MyCrossCompUserSelectedTeamAdapter(getContext(),myCrossCompAllTeamsMainModels2,this);
        myCrossCompTeamList1.setAdapter(myCrossCompUserSelectedTeamAdapter);



    //    new BackgroundTaskMySelectedTeam().execute();
    //    new BackgroundTaskGetAllTeams().execute();

        new BackgroundTaskForDefaultTeams().execute();
        new BackgroundTaskForCustomTeamSelect().execute();


        return view;


    }

    public void TeamsPostalCode(String SelectedTeamID) {

        SelectedTeamGeneralID = SelectedTeamID;

        new BackgroundTaskForTeamPostalCode().execute();

        Button saveTeam;


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, postalCodeForTeams);
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
                    Toast.makeText(getContext(), "All fields Required", Toast.LENGTH_SHORT).show();
                } else {


                    BackgroundTask backgroundTask = new BackgroundTask(getContext());
                    backgroundTask.execute(method, SelectedTeamGeneralID, currentUserID, postalCode, str_city_team, str_county_team, str_conference_team, str_state_team, str_union_team, str_country_team, str_division_team, str_world_team);

                }
            }
        });

    }

    public void TeamsChurch(String SelectedTeamID){

        SelectedTeamGeneralID = SelectedTeamID;

        Button saveTeam;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_church_auto_fill,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        saveTeam = dialogView.findViewById(R.id.saveTeam);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myFaithTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.My_Faith_Team_team_church);
        myFaithTeam.setThreshold(1);
        myFaithTeam.setAdapter(adapter);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myDivisionTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.MyDivisionTeam_church);
        myDivisionTeam.setThreshold(1);
        myDivisionTeam.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, postalCodeForTeams);
        AutoCompleteTextView myUnionTeam = (AutoCompleteTextView) dialogView.findViewById(R.id.MyUnionTeam_church);
        myUnionTeam.setThreshold(1);
        myUnionTeam.setAdapter(adapter2);


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, postalCodeForTeams);
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
                    Toast.makeText(getContext(), "All fields Required", Toast.LENGTH_SHORT).show();
                }else{


                    BackgroundTask backgroundTask = new BackgroundTask(getContext());
                    backgroundTask.execute(method,SelectedTeamGeneralID,currentUserID,FaithTeam, DivisionTeam, UnionTeam, ConferenceTeam);


                }

            }
        });

    }

    public void MoveToFundTeam(View view) {
        startActivity(new Intent(getContext(), MyFundraisingTeamDetailActivity.class));
    }

    public void MyWorldTeam(View view) {
        startActivity(new Intent(getContext(), TeamsScoreActivity.class));
    }


    public void SelectHighSchool(){

        new BackgroundTaskForAllHighSchoolTeams().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_high_school_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        saveHighSchoolTeamBtn = dialogView.findViewById(R.id.saveHighSchoolTeam);
        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);


        highSchoolTeamsArray.clear();

        highSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, highSchoolTeamsArray);
        highSchoolTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.high_school_team);
        highSchoolTeamNameAutoText.setThreshold(1);
        highSchoolTeamNameAutoText.setAdapter(highSchoolTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        highSchoolTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                highSchoolTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) highSchoolTeamNameAutoText.getAdapter();
                HighSchoolTeamID = highSchoolTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+HighSchoolTeamID );


            }
        });

        Log.e("dsfdsf", "SelectHighSchool: "+highSchoolTeamsArray.toString());
        highSchoolTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(highSchoolTeamNameAutoText.getText().toString())){

                                                    String words = highSchoolTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < highSchoolTeamsArray.size())
                                                    {
                                                        if(highSchoolTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your High School");
                                                        }
                                                        count++;

                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );

        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_high_school_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void CollegeUniversity(){

        new BackgroundTaskForAllCollegeClass().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_college_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        collegeUniversityTeamsArray.clear();

        collegeUniversityTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, collegeUniversityTeamsArray);
        collegeTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.collegeAutoText);
        collegeTeamNameAutoText.setThreshold(1);
        collegeTeamNameAutoText.setAdapter(collegeUniversityTeam_adapter);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        collegeTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                collegeUniversityTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) collegeTeamNameAutoText.getAdapter();
                CollegeTeamID = collegeUniversityTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+CollegeTeamID );


            }
        });

        collegeTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(collegeTeamNameAutoText.getText().toString())){

                                                    String words = collegeTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < collegeUniversityTeamsArray.size())
                                                    {
                                                        if(collegeUniversityTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your College/University");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );



        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_college_university_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void ProfessionalSchool(){

        new BackgroundTaskForProfessionalSchool().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_professional_school_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);



        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        professionalSchoolTeamsArray.clear();

        professionalSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, professionalSchoolTeamsArray);
        professionalSchoolTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.professionalSchool_team);
        professionalSchoolTeamNameAutoText.setThreshold(1);
        professionalSchoolTeamNameAutoText.setAdapter(professionalSchoolTeam_adapter);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        professionalSchoolTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                professionalSchoolTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) professionalSchoolTeamNameAutoText.getAdapter();
                ProfessionalSchoolID = professionalSchoolTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+ProfessionalSchoolID );


            }
        });

        professionalSchoolTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(professionalSchoolTeamNameAutoText.getText().toString())){

                                                    String words = professionalSchoolTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < professionalSchoolTeamsArray.size())
                                                    {
                                                        if(professionalSchoolTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Professional School");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );


        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_professional_school_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void MilitaryBranch(){

        new BackgroundTaskForMilitaryBranch().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_military_branch_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        militaryBranchTeamsArray.clear();

        militaryBranchTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, militaryBranchTeamsArray);
        militaryBranchAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.militaryBranchAutoText);
        militaryBranchAutoText.setThreshold(1);
        militaryBranchAutoText.setAdapter(militaryBranchTeam_adapter);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        militaryBranchAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                militaryBranchTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) militaryBranchAutoText.getAdapter();
                MilitaryBranchID = militaryBranchTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+MilitaryBranchID );


            }
        });

        militaryBranchAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(militaryBranchAutoText.getText().toString())){

                                                    String words = militaryBranchAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < militaryBranchTeamsArray.size())
                                                    {
                                                        if(militaryBranchTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Military Branch");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );


        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_military_group_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void Occupation(){

        new BackgroundTaskForOccupation().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialg_for_occupation_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        occupationTeamsArray.clear();

        occupationTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, occupationTeamsArray);
        occupationTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.occupationTeamNameAutoText);
        occupationTeamNameAutoText.setThreshold(1);
        occupationTeamNameAutoText.setAdapter(occupationTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        occupationTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                occupationTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) occupationTeamNameAutoText.getAdapter();
                OccupationID = occupationTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+OccupationID );


            }
        });

        occupationTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(occupationTeamNameAutoText.getText().toString())){

                                                    String words = occupationTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < occupationTeamsArray.size())
                                                    {
                                                        if(occupationTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Occupation");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );



        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_occupation_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void Company(){

        new BackgroundTaskForCompany().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_company_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        companyTeamsArray.clear();

        companyTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, companyTeamsArray);
        companyTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.companyTeamNameAutoText);
        companyTeamNameAutoText.setThreshold(1);
        companyTeamNameAutoText.setAdapter(companyTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        companyTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                companyTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) companyTeamNameAutoText.getAdapter();
                CompanyID = companyTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+CompanyID );


            }
        });

        companyTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(companyTeamNameAutoText.getText().toString())){

                                                    String words = companyTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < companyTeamsArray.size())
                                                    {
                                                        if(companyTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Company");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );


        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_company_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });

    }

    public void Faith(){

        new BackgroundTaskForFaithGroup().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_faith_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        faithTeamsArray.clear();

        faithTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, faithTeamsArray);
        faithTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.faithTeamNameAutoText);
        faithTeamNameAutoText.setThreshold(1);
        faithTeamNameAutoText.setAdapter(faithTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        faithTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                faithTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) faithTeamNameAutoText.getAdapter();
                FaithGroupID = faithTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+FaithGroupID );


            }
        });

        faithTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(faithTeamNameAutoText.getText().toString())){

                                                    String words = faithTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < faithTeamsArray.size())
                                                    {
                                                        if(faithTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Faith");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );



        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_faith_group_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });

    }

    public void GymBrand(){

        new BackgroundTaskForGymBrand().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_gym_brand_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        gymBrandTeamsArray.clear();

        gymBrandTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, gymBrandTeamsArray);
        gymBrandTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.gymBrandTeamNameAutoText);
        gymBrandTeamNameAutoText.setThreshold(1);
        gymBrandTeamNameAutoText.setAdapter(gymBrandTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        gymBrandTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                gymBrandTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) gymBrandTeamNameAutoText.getAdapter();
                GymBrandID = gymBrandTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+GymBrandID );


            }
        });

        gymBrandTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(gymBrandTeamNameAutoText.getText().toString())){

                                                    String words = gymBrandTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < gymBrandTeamsArray.size())
                                                    {
                                                        if(gymBrandTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your Gym Brand");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );


        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(becomeVolunteerBtn.getText().toString().equals("Confirmation")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_gym_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();
                }
            }
        });


    }

    public void FriendFamily(){

        new BackgroundTaskForFriendAndFamily().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_friend_family_team_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        friendFamilyTeamsArray.clear();

        friendFamilyTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(getContext(),android.R.layout.simple_list_item_1, friendFamilyTeamsArray);
        friendFamilyTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.friendFamilyTeamNameAutoText);
        friendFamilyTeamNameAutoText.setThreshold(1);
        friendFamilyTeamNameAutoText.setAdapter(friendFamilyTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        friendFamilyTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.getLayoutParams().width=500;
                becomeVolunteerBtn.setText("Confirmation");


                friendFamilyTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) friendFamilyTeamNameAutoText.getAdapter();
                FriendFamilyID = friendFamilyTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+FriendFamilyID );


            }
        });

        friendFamilyTeamNameAutoText.addTextChangedListener(
                new TextWatcher() {

                    private Timer timer = new Timer();
                    private final long DELAY = 2000; // Milliseconds

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask()
                                {
                                    @Override
                                    public void run() {
                                        // TODO: Do what you need here (refresh list).
                                        // You will probably need to use
                                        // runOnUiThread(Runnable action) for some
                                        // specific actions (e.g., manipulating views).

                                        getActivity().runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(friendFamilyTeamNameAutoText.getText().toString())){

                                                    String words = friendFamilyTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < friendFamilyTeamsArray.size())
                                                    {
                                                        if(friendFamilyTeamsArray.get(count).getTeamName().equals(words)){
                                                            becomeVolunteerBtn.getLayoutParams().width=500;
                                                            becomeVolunteerBtn.setText("Confirmation");
                                                            break;
                                                        }else{
                                                            Toast.makeText(getContext(), "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            becomeVolunteerBtn.getLayoutParams().width=1200;
                                                            becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Personal Team of your own");
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.INVISIBLE);
                                                }

                                            }   //closes run(){}
                                        });


                                    }
                                },
                                DELAY
                        ); }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }



                    @Override
                    public void afterTextChanged(final Editable s) {


                    }
                }
        );



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
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count++;

                        }
                    }

                    int count1 = 0;
                    if(jsonArray1 != null){
                        while(count1 < jsonArray1.length())
                        {
                            JSONObject JO = jsonArray1.getJSONObject(count1);
                            teamId = JO.getString("Team_ID");
                            teamName = JO.getString("HomeTeamName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("HomeTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count1++;

                        }
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
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count2++;

                        }
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
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count3++;

                        }
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
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count4++;

                        }
                    }

                    int count5 = 0;
                    if(jsonArray5 != null){
                        while(count5 < jsonArray5.length())
                        {
                            JSONObject JO = jsonArray5.getJSONObject(count5);
                            teamId = JO.getString("CommunityTeam_ID");
                            teamName = JO.getString("CommunityTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CommunityTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count5++;

                        }
                    }

                    int count7 = 0;
                    if(jsonArray7 != null){
                        while(count7 < jsonArray7.length())
                        {
                            JSONObject JO = jsonArray7.getJSONObject(count7);
                            teamId = JO.getString("HighSchool_ID");
                            teamName = JO.getString("HighSchoolTeamName");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("HighSchoolTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count7++;
                        }
                    }

                    int count8 = 0;
                    if(jsonArray8 != null){
                        while(count8 < jsonArray8.length())
                        {
                            JSONObject JO = jsonArray8.getJSONObject(count8);
                            teamId = JO.getString("CollegeClassTeam_ID");
                            teamName = JO.getString("CollegeTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CollegeTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count8++;
                        }
                    }

                    int count9 = 0;
                    if(jsonArray9 != null){
                        while(count9 < jsonArray9.length())
                        {
                            JSONObject JO = jsonArray9.getJSONObject(count9);
                            teamId = JO.getString("ProfessionalTeam_ID");
                            teamName = JO.getString("ProfessionalTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("ProfessionalTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count9++;
                        }
                    }

                    int count10 = 0;
                    if(jsonArray10 != null){
                        while(count10 < jsonArray10.length())
                        {
                            JSONObject JO = jsonArray10.getJSONObject(count10);
                            teamId = JO.getString("FaithTeam_ID");
                            teamName = JO.getString("FaithTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("FaithTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count10++;
                        }
                    }

                    int count11 = 0;
                    if(jsonArray11 != null){
                        while(count11 < jsonArray11.length())
                        {
                            JSONObject JO = jsonArray11.getJSONObject(count11);
                            teamId = JO.getString("GymTeam_ID");
                            teamName = JO.getString("GymTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("GymTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count11++;
                        }
                    }

                    int count12 = 0;
                    if(jsonArray12 != null){
                        while(count12 < jsonArray12.length())
                        {
                            JSONObject JO = jsonArray12.getJSONObject(count12);
                            teamId = JO.getString("CompanyTeam_ID");
                            teamName = JO.getString("CompanyTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("CompanyTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count12++;
                        }
                    }

                    int count13 = 0;
                    if(jsonArray13 != null){
                        while(count13 < jsonArray13.length())
                        {
                            JSONObject JO = jsonArray13.getJSONObject(count13);
                            teamId = JO.getString("OccupationTeam_ID");
                            teamName = JO.getString("OccupationTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("OccupationTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count13++;
                        }
                    }

                    int count14 = 0;
                    if(jsonArray14 != null){
                        while(count14 < jsonArray14.length())
                        {
                            JSONObject JO = jsonArray14.getJSONObject(count14);
                            teamId = JO.getString("MilitaryGroupTeam_ID");
                            teamName = JO.getString("MilitaryGroupTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("MilitaryTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count14++;
                        }
                    }

                    int count15 = 0;
                    if(jsonArray15 != null){
                        while(count15 < jsonArray15.length())
                        {
                            JSONObject JO = jsonArray15.getJSONObject(count15);
                            teamId = JO.getString("FriendTeamID");
                            teamName = JO.getString("MyFriends&FamilyTeam_Name");


                            MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                            mainModel.setSelectedTeamOpenType("FriendTeam");
                            mainModel.setTeamType("Default");
                            mainModel.setTeamID(teamId);
                            mainModel.setTeamName(teamName);
                            myCrossCompAllTeamsMainModels2.add(mainModel);
                            count15++;
                        }
                    }

                    myCrossCompUserSelectedTeamAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BackgroundTaskForCustomTeamSelect extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/crossCompsNonDefaultTeams.php";
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
                    jsonArray6 = jsonObject.getJSONArray("nonSelectedTeam");


                    int count = 0;
                    String teamId,teamName;
                    while(count < jsonArray6.length())
                    {
                        JSONObject JO = jsonArray6.getJSONObject(count);
                        teamId = JO.getString("TeamID");
                        teamName = JO.getString("TeamName");


                        MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                        mainModel.setSelectedTeamOpenType("NewJoinTeam");
                        mainModel.setTeamType("Unselected");
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

    private class BackgroundTaskForAllHighSchoolTeams extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_HighSchool.php";
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
                jsonArray = jsonObject.getJSONArray("All_HighSchool_Teams");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("HighSchool_ID");
                    teamName = JO.getString("HighSchoolTeamName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);

//                    if(!highSchoolTeamsArray.contains(teamName)){
//                        highSchoolTeamsArray.add(teamName);
//                    }

                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    highSchoolTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllCollegeClass extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_college_class_teams.php";
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
                jsonArray = jsonObject.getJSONArray("CollegeClassTeams");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("CollegeTeam_ID");
                    teamName = JO.getString("CollegeTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    collegeUniversityTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForProfessionalSchool extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_Professional_School.php";
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
                jsonArray = jsonObject.getJSONArray("ProfessionalClassTeams");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("ProfessionalTeam_ID");
                    teamName = JO.getString("ProfessionalTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    professionalSchoolTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForMilitaryBranch  extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_Military_Branch.php";
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
                jsonArray = jsonObject.getJSONArray("MilitaryGroupTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("MilitaryGroupTeam_ID");
                    teamName = JO.getString("MilitaryGroupTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    militaryBranchTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForOccupation extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_Occupations.php";
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
                jsonArray = jsonObject.getJSONArray("OccupationTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("OccupationTeam_ID");
                    teamName = JO.getString("OccupationTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    occupationTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForCompany extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_Companies.php";
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
                jsonArray = jsonObject.getJSONArray("CompanyTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("CompanyTeam_ID");
                    teamName = JO.getString("CompanyTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    companyTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForFaithGroup extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_FaithGroups.php";
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
                jsonArray = jsonObject.getJSONArray("FaithGroupTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("FaithTeam_ID");
                    teamName = JO.getString("FaithTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    faithTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForGymBrand extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_GymBrand.php";
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
                jsonArray = jsonObject.getJSONArray("GymTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("GymTeam_ID");
                    teamName = JO.getString("GymTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    gymBrandTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForFriendAndFamily extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/get_all_FriendFamilyTeam.php";
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
                jsonArray = jsonObject.getJSONArray("FriendsFamilyTeam");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("FriendsFamilyTeam_ID");
                    teamName = JO.getString("MyFriendsFamilyTeam_Name");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("fgfgdfgertret", "onPostExecute: "+teamName);

                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    friendFamilyTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}