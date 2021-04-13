package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.MyCrossCompUserSelectedTeamAdapter;
import com.eclairios.CrossComps.Adapter.UnSelectedTeamAdapter;
import com.eclairios.CrossComps.Authentication.Registration;
import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTaskForJoinTeams;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.MainFragments.TeamsFragment;
import com.eclairios.CrossComps.Model.JoinNewTeamModel;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JoinTeamsActivity extends AppCompatActivity implements InterfaceForSetTeams {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView myCrossCompTeamList1;
    UnSelectedTeamAdapter myCrossCompUserSelectedTeamAdapter;
    ArrayList<MyCrossCompAllTeamsMainModel> myCrossCompAllTeamsMainModels2 = new ArrayList<>();

    String currentUserID;

    EditText postal_code_for_team,city_team,county_team,conference_team,state_team,union_team,country_team,division_team,world_team;

    ArrayList postalCodeForTeams = new ArrayList();

    String SelectedPostalCode;
    String SelectedTeamGeneralID;
    Button joinTeamBtn,confirmBtn,addTeamButton;


    ArrayList<JoinNewTeamModel> communityTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> communityTeam_adapter;

    ArrayList<JoinNewTeamModel> highSchoolTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> highSchoolTeam_adapter;

    ArrayList<JoinNewTeamModel> highSchoolClassTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> highSchoolClassTeam_adapter;


    ArrayList<JoinNewTeamModel> collegeUniversityTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> collegeUniversityTeam_adapter;

    ArrayList<JoinNewTeamModel> collegeUniversityClassTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> collegeUniversityClassTeam_adapter;

    ArrayList<JoinNewTeamModel> professionalSchoolTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> professionalSchoolTeam_adapter;

    ArrayList<JoinNewTeamModel> professionalSchoolClassTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> professionalSchoolClassTeam_adapter;
    private ArrayAdapter<JoinNewTeamModel> militaryBranchTeam_adapter;

    ArrayList<JoinNewTeamModel> militaryBranchTeamsArray = new ArrayList();

    ArrayList<JoinNewTeamModel> militaryBranchLocalTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> militaryBranchLocalTeam_adapter;

    ArrayList<JoinNewTeamModel> occupationTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> occupationTeam_adapter;

    ArrayList<JoinNewTeamModel> occupationLocalTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> occupationLocalTeam_adapter;

    ArrayList<JoinNewTeamModel> companyTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> companyTeam_adapter;

    ArrayList<JoinNewTeamModel> companyLocalTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> companyLocalTeam_adapter;

    ArrayList<JoinNewTeamModel> faithTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> faithTeam_adapter;

    ArrayList<JoinNewTeamModel> faithLocalTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> faithLocalTeam_adapter;

    ArrayList<JoinNewTeamModel> gymBrandTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> gymBrandTeam_adapter;

    ArrayList<JoinNewTeamModel> gymBrandLocalTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> gymBrandLocalTeam_adapter;

    ArrayList<JoinNewTeamModel> friendFamilyTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> friendFamilyTeam_adapter;



    AutoCompleteTextView communityTeamNameAutoText,highSchoolTeamNameAutoText,highSchoolClassTeamNameAutoText,collegeTeamNameAutoText,collegeClassTeamNameAutoText,
            professionalSchoolTeamNameAutoText,professionalSchoolClassTeamNameAutoText,militaryBranchAutoText,militaryBranchLocalAutoText,
            occupationTeamNameAutoText,occupationTeamLocalNameAutoText,companyTeamNameAutoText,companyTeamLocalNameAutoText,
            faithTeamNameAutoText,faithTeamLocalNameAutoText,gymBrandTeamNameAutoText,gymBrandTeamLocalNameAutoText,friendFamilyTeamNameAutoText;


    Button saveHighSchoolTeamBtn,becomeVolunteerBtn;

    String CommunityID,CountryID,StateID,CityID,PostalCodeID,HighSchoolTeamID,HighSchoolClassTeamID,CollegeTeamID,CollegeClassTeamID,ProfessionalSchoolID,ProfessionalSchoolClassID,MilitaryBranchID,MilitaryBranchLocalID,
            OccupationID,OccupationLocalID,CompanyID,CompanyLocalID,FaithGroupID,FaithGroupLocalID,GymBrandID,GymBrandLocalID,FriendFamilyID;


    String highSchoolSelected = "";
    String collegeSelected = "";
    String professionalSchoolSelected = "";
    String militarySelected = "";
    String occupationSelected = "";
    String companySelected = "";
    String faithSelected = "";
    String gymSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_teams);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentUserID = preferences.getString("CurrentUserId", "");


        myCrossCompTeamList1 = findViewById(R.id.myCrossCompTeamList1);
        myCrossCompUserSelectedTeamAdapter = new UnSelectedTeamAdapter(this,myCrossCompAllTeamsMainModels2, JoinTeamsActivity.this);
        myCrossCompTeamList1.setAdapter(myCrossCompUserSelectedTeamAdapter);

        new BackgroundTaskForCustomTeamSelect().execute();
    }

    @Override
    public void TeamsPostalCode(String SelectedGeneralTeamID) {

    }

    @Override
    public void TeamsChurch(String SelectedTeamID) {

    }

    @Override
    public void SelectCommunity() {
        new BackgroundTaskForAllCommunityTeams().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_community,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        communityTeamsArray.clear();

        communityTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, communityTeamsArray);
        communityTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.communityTeamNameAutoText);
        communityTeamNameAutoText.setThreshold(1);
        communityTeamNameAutoText.setAdapter(communityTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        communityTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                addTeamButton.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.setVisibility(View.GONE);


                communityTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) communityTeamNameAutoText.getAdapter();

                CountryID = communityTeam_adapter.getItem(position).getCountryTeamID();
                StateID = communityTeam_adapter.getItem(position).getStateTeamID();
                CityID = communityTeam_adapter.getItem(position).getCityTeamID();
                PostalCodeID = communityTeam_adapter.getItem(position).getPostalCodeID();
                CommunityID = communityTeam_adapter.getItem(position).getCommunityTeamID();


                Log.e("dsfdsfsd", "onItemClick: "+CountryID );
                Log.e("dsfdsfsd", "onItemClick: "+StateID );
                Log.e("dsfdsfsd", "onItemClick: "+CityID );
                Log.e("dsfdsfsd", "onItemClick: "+PostalCodeID );
                Log.e("dsfdsfsd", "onItemClick: "+CommunityID );

            }
        });

        Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
        communityTeamNameAutoText.addTextChangedListener(
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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(communityTeamNameAutoText.getText().toString())){

                                                    String words = communityTeamNameAutoText.getText().toString();


                                                    int counter = 0;

                                                    int count = 0;
                                                    while(count < communityTeamsArray.size())
                                                    {
                                                        if(communityTeamsArray.get(count).getTeamName().equals(words)){

                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }

                                                    if(counter > 0){
                                                        addTeamButton.setVisibility(View.VISIBLE);
                                                        becomeVolunteerBtn.setVisibility(View.GONE);
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                    }


                                                }else if(TextUtils.isEmpty(communityTeamNameAutoText.getText().toString())){
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);
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

        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTeamButton.getText().toString().equals("Add Team")){
                    confirmBtn.setVisibility(View.VISIBLE);
                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                            String currentUserID = preferences.getString("CurrentUserId", "");

                            String method = "joinCommunityTeams";

                            BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                            backgroundTask.execute(method,currentUserID,CountryID,StateID,CityID,PostalCodeID,CommunityID);
                        }
                    });
                }
            }
        });

        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void SelectHighSchool(){

        new BackgroundTaskForAllHighSchoolTeams().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_high_school_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        highSchoolTeamsArray.clear();

        highSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, highSchoolTeamsArray);
        highSchoolTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.highSchoolTeamNameAutoText);
        highSchoolClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.highSchoolClassTeamNameAutoText);
        highSchoolTeamNameAutoText.setThreshold(1);
        highSchoolTeamNameAutoText.setAdapter(highSchoolTeam_adapter);


        highSchoolTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                highSchoolClassTeamNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);

                highSchoolClassTeamsArray.clear();
                highSchoolTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) highSchoolTeamNameAutoText.getAdapter();
                HighSchoolTeamID = highSchoolTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+HighSchoolTeamID );

                new BackgroundTaskForAllHighSchoolClassTeams().execute();

                highSchoolClassTeamsArray.clear();

                highSchoolClassTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, highSchoolClassTeamsArray);
//                highSchoolClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.highSchoolClassTeamNameAutoText);
                highSchoolClassTeamNameAutoText.setThreshold(1);
                highSchoolClassTeamNameAutoText.setAdapter(highSchoolClassTeam_adapter);

                highSchoolClassTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        highSchoolSelected = "SchoolSelected";

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);

                        highSchoolClassTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) highSchoolClassTeamNameAutoText.getAdapter();
                        HighSchoolClassTeamID = highSchoolClassTeam_adapter.getItem(position).getTeamID();
                        HighSchoolTeamID = highSchoolClassTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+HighSchoolClassTeamID );
                        Log.e("dsfdsfsd", "onItemClick: "+HighSchoolTeamID );

                    }
                });

                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                highSchoolClassTeamNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(highSchoolClassTeamNameAutoText.getText().toString())){

                                                            String words = highSchoolClassTeamNameAutoText.getText().toString();

                                                            int counter = 0;


                                                            int count = 0;
                                                            while(count < highSchoolClassTeamsArray.size())
                                                            {
                                                                if(highSchoolClassTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }


                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }

                                                        }else if(TextUtils.isEmpty(highSchoolTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(highSchoolClassTeamNameAutoText.getText().toString())){

                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if(highSchoolSelected.equals("SchoolSelected") && TextUtils.isEmpty(highSchoolClassTeamNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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


                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "HighSchool";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, HighSchoolTeamID,HighSchoolClassTeamID );
                                }
                            });
                        }
                    }
                });
            }
        });


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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(highSchoolTeamNameAutoText.getText().toString())){

                                                    String words = highSchoolTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < highSchoolTeamsArray.size())
                                                    {
                                                        if(highSchoolTeamsArray.get(count).getTeamName().equals(words)){

                                                            counter ++;
                                                        }
//
                                                        count++;

                                                    }

                                                    if(counter > 0){
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.GONE);
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                        highSchoolSelected = "SchoolUnSelected";

                                                        highSchoolClassTeamsArray.clear();
                                                        highSchoolClassTeamNameAutoText.setText("");
                                                        highSchoolClassTeamNameAutoText.setFocusableInTouchMode(false);

                                                    }


                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    highSchoolSelected = "SchoolUnSelected";
                                                    highSchoolClassTeamsArray.clear();
                                                    highSchoolClassTeamNameAutoText.setText("");
                                                    highSchoolClassTeamNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void CollegeUniversity(){

        new BackgroundTaskForAllCollegeClass().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_college_university_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        collegeUniversityTeamsArray.clear();

        collegeUniversityTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, collegeUniversityTeamsArray);
        collegeTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.collegeUniversityTeamNameAutoText);
        collegeClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.collegeUniversityClassTeamNameAutoText);
        collegeTeamNameAutoText.setThreshold(1);
        collegeTeamNameAutoText.setAdapter(collegeUniversityTeam_adapter);

        collegeTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                collegeClassTeamNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                collegeSelected = "collegeSelected";

                collegeUniversityTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) collegeTeamNameAutoText.getAdapter();
                CollegeTeamID = collegeUniversityTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+CollegeTeamID );

                new BackgroundTaskForAllCollegeClassTeams().execute();

                collegeUniversityClassTeamsArray.clear();

                collegeUniversityClassTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, collegeUniversityClassTeamsArray);
//                collegeClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.collegeUniversityClassTeamNameAutoText);
                collegeClassTeamNameAutoText.setThreshold(1);
                collegeClassTeamNameAutoText.setAdapter(collegeUniversityClassTeam_adapter);



                collegeClassTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);


                        collegeUniversityClassTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) collegeClassTeamNameAutoText.getAdapter();
                        CollegeClassTeamID = collegeUniversityClassTeam_adapter.getItem(position).getTeamID();
                        CollegeTeamID = collegeUniversityClassTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+CollegeClassTeamID );
                        Log.e("dsfdsfsd", "onItemClick: "+CollegeTeamID );

                    }
                });

                collegeClassTeamNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(collegeClassTeamNameAutoText.getText().toString())){

                                                            String words = collegeClassTeamNameAutoText.getText().toString();


                                                            int counter = 0;


                                                            int count = 0;
                                                            while(count < collegeUniversityClassTeamsArray.size())
                                                            {
                                                                if(collegeUniversityClassTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(collegeClassTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(collegeTeamNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (collegeSelected.equals("collegeSelected") && TextUtils.isEmpty(collegeClassTeamNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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

                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "CollegeUniversity";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, CollegeTeamID, CollegeClassTeamID);
                                }
                            });
                        }
                    }
                });
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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(collegeTeamNameAutoText.getText().toString())){

                                                    String words = collegeTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < collegeUniversityTeamsArray.size())
                                                    {
                                                        if(collegeUniversityTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        collegeSelected = "collegeUnSelected";
                                                        collegeUniversityClassTeamsArray.clear();
                                                        collegeClassTeamNameAutoText.setText("");
                                                        collegeClassTeamNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);
                                                    collegeSelected = "collegeUnSelected";
                                                    collegeUniversityClassTeamsArray.clear();
                                                    collegeClassTeamNameAutoText.setText("");
                                                    collegeClassTeamNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void ProfessionalSchool(){

        new BackgroundTaskForProfessionalSchool().execute();


        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_professional_school_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        professionalSchoolTeamsArray.clear();

        professionalSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, professionalSchoolTeamsArray);
        professionalSchoolTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.professionalTeamNameAutoText);
        professionalSchoolClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.professionalClassTeamNameAutoText);
        professionalSchoolTeamNameAutoText.setThreshold(1);
        professionalSchoolTeamNameAutoText.setAdapter(professionalSchoolTeam_adapter);


        professionalSchoolTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                professionalSchoolClassTeamNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                professionalSchoolSelected = "professionalSchoolSelected";


                professionalSchoolTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) professionalSchoolTeamNameAutoText.getAdapter();
                ProfessionalSchoolID = professionalSchoolTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+ProfessionalSchoolID );

                new BackgroundTaskForAllProfessionalSchoolClassTeams().execute();

                professionalSchoolClassTeamsArray.clear();

                professionalSchoolClassTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, professionalSchoolClassTeamsArray);
//        professionalSchoolClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.professionalClassTeamNameAutoText);
                professionalSchoolClassTeamNameAutoText.setThreshold(1);
                professionalSchoolClassTeamNameAutoText.setAdapter(professionalSchoolClassTeam_adapter);


                professionalSchoolClassTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);


                        professionalSchoolClassTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) professionalSchoolClassTeamNameAutoText.getAdapter();
                        ProfessionalSchoolClassID = professionalSchoolClassTeam_adapter.getItem(position).getTeamID();
                        ProfessionalSchoolID = professionalSchoolClassTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+ProfessionalSchoolClassID );
                        Log.e("dsfdsfsd", "onItemClick: "+ProfessionalSchoolID );

                    }
                });

                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                professionalSchoolClassTeamNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(professionalSchoolClassTeamNameAutoText.getText().toString())){

                                                            String words = professionalSchoolClassTeamNameAutoText.getText().toString();

                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < professionalSchoolClassTeamsArray.size())
                                                            {
                                                                if(professionalSchoolClassTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(professionalSchoolTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(professionalSchoolClassTeamNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (professionalSchoolSelected.equals("professionalSchoolSelected") && TextUtils.isEmpty(professionalSchoolClassTeamNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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


                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "ProfessionalSchool";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, ProfessionalSchoolID, ProfessionalSchoolClassID);
                                }
                            });
                        }
                    }
                });
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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(professionalSchoolTeamNameAutoText.getText().toString())){

                                                    String words = professionalSchoolTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < professionalSchoolTeamsArray.size())
                                                    {
                                                        if(professionalSchoolTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        professionalSchoolSelected = "professionalSchoolUnSelected";
                                                        professionalSchoolClassTeamsArray.clear();
                                                        professionalSchoolClassTeamNameAutoText.setText("");
                                                        professionalSchoolClassTeamNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);
                                                    professionalSchoolSelected = "professionalSchoolUnSelected";
                                                    professionalSchoolClassTeamsArray.clear();
                                                    professionalSchoolClassTeamNameAutoText.setText("");
                                                    professionalSchoolClassTeamNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });

    }

    public void MilitaryBranch(){

        new BackgroundTaskForMilitaryBranch().execute();


        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_military_group_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        militaryBranchTeamsArray.clear();

        militaryBranchTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, militaryBranchTeamsArray);
        militaryBranchAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.militaryTeamNameAutoText);
        militaryBranchLocalAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.militaryLocalTeamNameAutoText);
        militaryBranchAutoText.setThreshold(1);
        militaryBranchAutoText.setAdapter(militaryBranchTeam_adapter);

        militaryBranchAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                militaryBranchLocalAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                militarySelected = "militarySelected";

                militaryBranchTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) militaryBranchAutoText.getAdapter();
                MilitaryBranchID = militaryBranchTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+MilitaryBranchID );

                new BackgroundTaskForAllMilitaryLocalTeams().execute();

                militaryBranchLocalTeamsArray.clear();

                militaryBranchLocalTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, militaryBranchLocalTeamsArray);
//                militaryBranchLocalAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.militaryLocalTeamNameAutoText);
                militaryBranchLocalAutoText.setThreshold(1);
                militaryBranchLocalAutoText.setAdapter(militaryBranchLocalTeam_adapter);



                militaryBranchLocalAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);


                        militaryBranchLocalTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) militaryBranchLocalAutoText.getAdapter();
                        MilitaryBranchLocalID = militaryBranchLocalTeam_adapter.getItem(position).getTeamID();
                        MilitaryBranchID = militaryBranchLocalTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+MilitaryBranchLocalID );
                        Log.e("dsfdsfsd", "onItemClick: "+MilitaryBranchID );

                    }
                });


                militaryBranchLocalAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(militaryBranchLocalAutoText.getText().toString())){

                                                            String words = militaryBranchLocalAutoText.getText().toString();

                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < militaryBranchLocalTeamsArray.size())
                                                            {
                                                                if(militaryBranchLocalTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(militaryBranchAutoText.getText().toString()) && TextUtils.isEmpty(militaryBranchLocalAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (militarySelected.equals("militarySelected") && TextUtils.isEmpty(militaryBranchLocalAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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

                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "MilitaryBranch";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, MilitaryBranchID, MilitaryBranchLocalID);
                                }
                            });
                        }
                    }
                });

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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(militaryBranchAutoText.getText().toString())){

                                                    String words = militaryBranchAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < militaryBranchTeamsArray.size())
                                                    {
                                                        if(militaryBranchTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        militarySelected = "militaryUnSelected";
                                                        militaryBranchLocalTeamsArray.clear();
                                                        militaryBranchLocalAutoText.setText("");
                                                        militaryBranchLocalAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    militarySelected = "militaryUnSelected";
                                                    militaryBranchLocalTeamsArray.clear();
                                                    militaryBranchLocalAutoText.setText("");
                                                    militaryBranchLocalAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void Occupation(){

        new BackgroundTaskForOccupation().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_occupation_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        occupationTeamsArray.clear();

        occupationTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, occupationTeamsArray);
        occupationTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.occupationTeamNameAutoText);
        occupationTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.occupationLocalTeamNameAutoText);
        occupationTeamNameAutoText.setThreshold(1);
        occupationTeamNameAutoText.setAdapter(occupationTeam_adapter);

        occupationTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                occupationTeamLocalNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                occupationSelected = "occupationSelected";


                occupationTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) occupationTeamNameAutoText.getAdapter();
                OccupationID = occupationTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+OccupationID );

                new BackgroundTaskForAllOccupationLocalTeams().execute();

                occupationLocalTeamsArray.clear();

                occupationLocalTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, occupationLocalTeamsArray);
//        occupationTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.occupationLocalTeamNameAutoText);
                occupationTeamLocalNameAutoText.setThreshold(1);
                occupationTeamLocalNameAutoText.setAdapter(occupationLocalTeam_adapter);



                occupationTeamLocalNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);


                        occupationLocalTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) occupationTeamLocalNameAutoText.getAdapter();
                        OccupationLocalID = occupationLocalTeam_adapter.getItem(position).getTeamID();
                        OccupationID = occupationLocalTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+OccupationLocalID );
                        Log.e("dsfdsfsd", "onItemClick: "+OccupationID );

                    }
                });

                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                occupationTeamLocalNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(occupationTeamLocalNameAutoText.getText().toString())){

                                                            String words = occupationTeamLocalNameAutoText.getText().toString();


                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < occupationLocalTeamsArray.size())
                                                            {
                                                                if(occupationLocalTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(occupationTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(occupationTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (occupationSelected.equals("occupationSelected") && TextUtils.isEmpty(occupationTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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


                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "OccupationGroup";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, OccupationID, OccupationLocalID);
                                }
                            });
                        }
                    }
                });

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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(occupationTeamNameAutoText.getText().toString())){

                                                    String words = occupationTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < occupationTeamsArray.size())
                                                    {
                                                        if(occupationTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        occupationSelected = "occupationUnSelected";
                                                        occupationLocalTeamsArray.clear();
                                                        occupationTeamLocalNameAutoText.setText("");
                                                        occupationTeamLocalNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    occupationSelected = "occupationUnSelected";
                                                    occupationLocalTeamsArray.clear();
                                                    occupationTeamLocalNameAutoText.setText("");
                                                    occupationTeamLocalNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void Company(){

        new BackgroundTaskForCompany().execute();



        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_company_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        companyTeamsArray.clear();

        companyTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, companyTeamsArray);
        companyTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.companyTeamNameAutoText);
        companyTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.companyLocalTeamNameAutoText);
        companyTeamNameAutoText.setThreshold(1);
        companyTeamNameAutoText.setAdapter(companyTeam_adapter);

        companyTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                companyTeamLocalNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                companySelected = "companySelected";


                companyTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) companyTeamNameAutoText.getAdapter();
                CompanyID = companyTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+CompanyID );

                new BackgroundTaskForAllCompanyLocalTeams().execute();

                companyLocalTeamsArray.clear();

                companyLocalTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, companyLocalTeamsArray);
//        companyTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.companyLocalTeamNameAutoText);
                companyTeamLocalNameAutoText.setThreshold(1);
                companyTeamLocalNameAutoText.setAdapter(companyLocalTeam_adapter);



                companyTeamLocalNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);

                        companyLocalTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) companyTeamLocalNameAutoText.getAdapter();
                        CompanyLocalID = companyLocalTeam_adapter.getItem(position).getTeamID();
                        CompanyID = companyLocalTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+CompanyLocalID );
                        Log.e("dsfdsfsd", "onItemClick: "+CompanyID );

                    }
                });

                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                companyTeamLocalNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(companyTeamLocalNameAutoText.getText().toString())){

                                                            String words = companyTeamLocalNameAutoText.getText().toString();


                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < companyLocalTeamsArray.size())
                                                            {
                                                                if(companyLocalTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(companyTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(companyTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (companySelected.equals("companySelected") && TextUtils.isEmpty(companyTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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


                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "CompanyGroup";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, CompanyID, CompanyLocalID);
                                }
                            });
                        }
                    }
                });

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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(companyTeamNameAutoText.getText().toString())){

                                                    String words = companyTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < companyTeamsArray.size())
                                                    {
                                                        if(companyTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        companySelected = "companyUnSelected";
                                                        companyLocalTeamsArray.clear();
                                                        companyTeamLocalNameAutoText.setText("");
                                                        companyTeamLocalNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    companySelected = "companyUnSelected";
                                                    companyLocalTeamsArray.clear();
                                                    companyTeamLocalNameAutoText.setText("");
                                                    companyTeamLocalNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void Faith(){

        new BackgroundTaskForFaithGroup().execute();



        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_faith_group_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);
        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


        faithTeamsArray.clear();

        faithTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, faithTeamsArray);
        faithTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.faithTeamNameAutoText);
        faithTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.faithLocalTeamNameAutoText);
        faithTeamNameAutoText.setThreshold(1);
        faithTeamNameAutoText.setAdapter(faithTeam_adapter);

        faithTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                faithTeamLocalNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                faithSelected = "faithSelected";


                faithTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) faithTeamNameAutoText.getAdapter();
                FaithGroupID = faithTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+FaithGroupID );

                new BackgroundTaskForAllFaithLocalTeams().execute();

                faithLocalTeamsArray.clear();

                faithLocalTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, faithLocalTeamsArray);
//        faithTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.faithLocalTeamNameAutoText);
                faithTeamLocalNameAutoText.setThreshold(1);
                faithTeamLocalNameAutoText.setAdapter(faithLocalTeam_adapter);



                faithTeamLocalNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);

                        faithLocalTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) faithTeamLocalNameAutoText.getAdapter();
                        FaithGroupLocalID = faithLocalTeam_adapter.getItem(position).getTeamID();
                        FaithGroupID = faithLocalTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+FaithGroupLocalID );
                        Log.e("dsfdsfsd", "onItemClick: "+FaithGroupID );

                    }
                });
                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                faithTeamLocalNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(faithTeamLocalNameAutoText.getText().toString())){

                                                            String words = faithTeamLocalNameAutoText.getText().toString();


                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < faithLocalTeamsArray.size())
                                                            {
                                                                if(faithLocalTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(faithTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(faithTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (faithSelected.equals("faithSelected") && TextUtils.isEmpty(faithTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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

                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "FaithGroup";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, FaithGroupID, FaithGroupLocalID);
                                }
                            });
                        }
                    }
                });
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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(faithTeamNameAutoText.getText().toString())){

                                                    String words = faithTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < faithTeamsArray.size())
                                                    {
                                                        if(faithTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        faithSelected = "faithUnSelected";
                                                        faithLocalTeamsArray.clear();
                                                        faithTeamLocalNameAutoText.setText("");
                                                        faithTeamLocalNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    faithSelected = "faithUnSelected";
                                                    faithLocalTeamsArray.clear();
                                                    faithTeamLocalNameAutoText.setText("");
                                                    faithTeamLocalNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void GymBrand(){

        new BackgroundTaskForGymBrand().execute();




        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_gym_subclass,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        gymBrandTeamsArray.clear();

        gymBrandTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, gymBrandTeamsArray);
        gymBrandTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.gymTeamNameAutoText);
        gymBrandTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.gymLocalTeamNameAutoText);
        gymBrandTeamNameAutoText.setThreshold(1);
        gymBrandTeamNameAutoText.setAdapter(gymBrandTeam_adapter);

        gymBrandTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {


                gymBrandTeamLocalNameAutoText.setFocusableInTouchMode(true);
                becomeVolunteerBtn.setVisibility(View.GONE);
                gymSelected = "gymSelected";


                gymBrandTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) gymBrandTeamNameAutoText.getAdapter();
                GymBrandID = gymBrandTeam_adapter.getItem(position).getTeamID();
                Log.e("dsfdsfsd", "onItemClick: "+GymBrandID );

                new BackgroundTaskForAllGymLocalTeams().execute();

                gymBrandLocalTeamsArray.clear();

                gymBrandLocalTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, gymBrandLocalTeamsArray);
//        gymBrandTeamLocalNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.gymLocalTeamNameAutoText);
                gymBrandTeamLocalNameAutoText.setThreshold(1);
                gymBrandTeamLocalNameAutoText.setAdapter(gymBrandLocalTeam_adapter);



                gymBrandTeamLocalNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                        addTeamButton.setVisibility(View.VISIBLE);
                        becomeVolunteerBtn.setVisibility(View.GONE);


                        gymBrandLocalTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) gymBrandTeamLocalNameAutoText.getAdapter();
                        GymBrandLocalID = gymBrandLocalTeam_adapter.getItem(position).getTeamID();
                        GymBrandID = gymBrandLocalTeam_adapter.getItem(position).getParentTeamID();
                        Log.e("dsfdsfsd", "onItemClick: "+GymBrandLocalID );
                        Log.e("dsfdsfsd", "onItemClick: "+GymBrandID );

                    }
                });

                Log.e("dsfdsf", "SelectHighSchool: "+highSchoolClassTeamsArray.toString());
                gymBrandTeamLocalNameAutoText.addTextChangedListener(
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

                                                runOnUiThread(new Runnable() {
                                                    public void run(){



                                                        if(!TextUtils.isEmpty(gymBrandTeamLocalNameAutoText.getText().toString())){

                                                            String words = gymBrandTeamLocalNameAutoText.getText().toString();

                                                            int counter = 0;

                                                            int count = 0;
                                                            while(count < gymBrandLocalTeamsArray.size())
                                                            {
                                                                if(gymBrandLocalTeamsArray.get(count).getTeamName().equals(words)){

                                                                    counter++;
                                                                }
//
                                                                count++;

                                                            }

                                                            if(counter > 0){
                                                                addTeamButton.setVisibility(View.VISIBLE);
                                                                becomeVolunteerBtn.setVisibility(View.GONE);
                                                            }else{
                                                                Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                confirmBtn.setVisibility(View.GONE);
                                                                addTeamButton.setVisibility(View.GONE);
                                                                becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                            }


                                                        }else if(TextUtils.isEmpty(gymBrandTeamNameAutoText.getText().toString()) && TextUtils.isEmpty(gymBrandTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
                                                        }else if (gymSelected.equals("gymSelected") && TextUtils.isEmpty(gymBrandTeamLocalNameAutoText.getText().toString())){
                                                            becomeVolunteerBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            confirmBtn.setVisibility(View.GONE);
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

                addTeamButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addTeamButton.getText().toString().equals("Add Team")){
                            confirmBtn.setVisibility(View.VISIBLE);
                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "joinTeams";
                                    String teamType = "GymBrand";

                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                                    backgroundTask.execute(method,currentUserID, teamType, GymBrandID, GymBrandLocalID);
                                }
                            });
                        }
                    }
                });
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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(gymBrandTeamNameAutoText.getText().toString())){

                                                    String words = gymBrandTeamNameAutoText.getText().toString();

                                                    int counter = 0;


                                                    int count = 0;
                                                    while(count < gymBrandTeamsArray.size())
                                                    {
                                                        if(gymBrandTeamsArray.get(count).getTeamName().equals(words)){
                                                            counter++;
                                                        }
//
                                                        count++;

                                                    }


                                                    if(counter > 0){
                                                        Log.e("dfjsdfhsdf", "run: " );
                                                    }else{
                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_SHORT).show();
                                                        confirmBtn.setVisibility(View.GONE);
                                                        addTeamButton.setVisibility(View.GONE);
                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);

                                                        gymSelected = "gymUnSelected";
                                                        gymBrandLocalTeamsArray.clear();
                                                        gymBrandTeamLocalNameAutoText.setText("");
                                                        gymBrandTeamLocalNameAutoText.setFocusableInTouchMode(false);
                                                    }

                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);

                                                    gymSelected = "gymUnSelected";
                                                    gymBrandLocalTeamsArray.clear();
                                                    gymBrandTeamLocalNameAutoText.setText("");
                                                    gymBrandTeamLocalNameAutoText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
    }

    public void FriendFamily(){

        new BackgroundTaskForFriendAndFamily().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_friend_family_team_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);
        confirmBtn = dialogView.findViewById(R.id.confirmTeamBtn);
        addTeamButton = dialogView.findViewById(R.id.addTeamButton);

        friendFamilyTeamsArray.clear();

        friendFamilyTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, friendFamilyTeamsArray);
        friendFamilyTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.friendFamilyTeamNameAutoText);
        friendFamilyTeamNameAutoText.setThreshold(1);
        friendFamilyTeamNameAutoText.setAdapter(friendFamilyTeam_adapter);

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        friendFamilyTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                addTeamButton.setVisibility(View.VISIBLE);
                becomeVolunteerBtn.setVisibility(View.GONE);


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

                                        runOnUiThread(new Runnable() {
                                            public void run(){



                                                if(!TextUtils.isEmpty(friendFamilyTeamNameAutoText.getText().toString())){

                                                    String words = friendFamilyTeamNameAutoText.getText().toString();

                                                    int count = 0;
                                                    while(count < friendFamilyTeamsArray.size())
                                                    {
                                                        if(friendFamilyTeamsArray.get(count).getTeamName().equals(words)){
                                                            addTeamButton.setVisibility(View.VISIBLE);
                                                            break;
                                                        }else{
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                            confirmBtn.setVisibility(View.GONE);
                                                            addTeamButton.setVisibility(View.GONE);
                                                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                        }
                                                        count++;

                                                    }



                                                }else{
                                                    becomeVolunteerBtn.setVisibility(View.GONE);
                                                    addTeamButton.setVisibility(View.GONE);
                                                    confirmBtn.setVisibility(View.GONE);
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


        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTeamButton.getText().toString().equals("Add Team")){
                    confirmBtn.setVisibility(View.VISIBLE);
                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(JoinTeamsActivity.this);
                            String currentUserID = preferences.getString("CurrentUserId", "");

                            String method = "joinTeams";
                            String teamType = "PersonalTeams";

                            BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(JoinTeamsActivity.this);
                            backgroundTask.execute(method,currentUserID, teamType, "0", FriendFamilyID);
                        }
                    });
                }
            }
        });

        becomeVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinTeamsActivity.this,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                startActivity(intent);
            }
        });
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
                    jsonArray = jsonObject.getJSONArray("nonSelectedTeam");


                    int count = 0;
                    String teamId,teamName;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        teamId = JO.getString("TeamID");
                        teamName = JO.getString("TeamName");


                        MyCrossCompAllTeamsMainModel mainModel = new MyCrossCompAllTeamsMainModel();
                        mainModel.setSelectedTeamOpenType("NewJoinTeam");
                        mainModel.setTeamType("Unselected");
                        mainModel.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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
                    model.setTeamCategory("MainClass");
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

    private class BackgroundTaskForAllHighSchoolClassTeams extends AsyncTask<String, Void, String>
    {
        String json_url;

        @Override
        protected String doInBackground(String... strings) {
            Log.e("jkskhsd", "doInBackground: " );

            try {
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                Log.e("djdjsdkkjds", "doInBackground: "+HighSchoolTeamID );

                String data = URLEncoder.encode("highSchoolID","UTF-8") + "=" + URLEncoder.encode(HighSchoolTeamID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_HighSchoolClass.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;


            Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


            if(json_string != null) {

                try {
                    highSchoolClassTeamsArray.clear();
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("All_HighSchoolClass_Teams");


                    int count = 0;
                    String teamId, parentTeamUD, teamName;
                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        teamId = JO.getString("HS_SubClassID");
                        parentTeamUD = JO.getString("HighSchoolID");
                        teamName = JO.getString("HS_SubClassName");

                        Log.e("dsfsfdsf", "onPostExecute: " + teamId);
                        Log.e("dsfsfdsf", "onPostExecute: " + teamName);


                        JoinNewTeamModel model = new JoinNewTeamModel();
                        model.setTeamCategory("SubClass");
                        model.setParentTeamID(parentTeamUD);
                        model.setTeamID(teamId);
                        model.setTeamName(teamName);
                        highSchoolClassTeamsArray.add(model);
                        count++;

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BackgroundTaskForAllCollegeClassTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("collegeUniversityID","UTF-8") + "=" + URLEncoder.encode(CollegeTeamID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_CollegeUniversityClass.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;


            Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


            if(json_string != null) {

                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("All_CollegeUniversityClass_Teams");


                    int count = 0;
                    String teamId, parentTeamUD, teamName;
                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        teamId = JO.getString("CU_SubClassID");
                        parentTeamUD = JO.getString("CollegeUniversityID");
                        teamName = JO.getString("CU_SubClassName");

                        Log.e("dsfsfdsf", "onPostExecute: " + teamId);
                        Log.e("dsfsfdsf", "onPostExecute: " + teamName);


                        JoinNewTeamModel model = new JoinNewTeamModel();
                        model.setTeamCategory("SubClass");
                        model.setParentTeamID(parentTeamUD);
                        model.setTeamID(teamId);
                        model.setTeamName(teamName);
                        collegeUniversityClassTeamsArray.add(model);
                        count++;

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BackgroundTaskForAllProfessionalSchoolClassTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("professionalSchoolID","UTF-8") + "=" + URLEncoder.encode(ProfessionalSchoolID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_ProfessionalSchoolClass.php";
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
                jsonArray = jsonObject.getJSONArray("All_ProfessionalSchoolClass_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("PS_SubClassID");
                    parentTeamUD = JO.getString("ProfessionalTeam_ID");
                    teamName = JO.getString("PS_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    professionalSchoolClassTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllMilitaryLocalTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("militaryID","UTF-8") + "=" + URLEncoder.encode(MilitaryBranchID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_MilitaryLocalGroup.php";
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
                jsonArray = jsonObject.getJSONArray("All_MilitaryLocalGroup_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("Military_SubClassID");
                    parentTeamUD = JO.getString("MilitaryGroupTeam_ID");
                    teamName = JO.getString("Military_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    militaryBranchLocalTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllOccupationLocalTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("occupationID","UTF-8") + "=" + URLEncoder.encode(OccupationID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_OccupationLocalGroup.php";
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
                jsonArray = jsonObject.getJSONArray("All_OccupationLocalGroup_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("Occupation_SubClassID");
                    parentTeamUD = JO.getString("OccupationTeam_ID");
                    teamName = JO.getString("Occupation_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    occupationLocalTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllCompanyLocalTeams  extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("companyID","UTF-8") + "=" + URLEncoder.encode(CompanyID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_CompanyLocalGroup.php";
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
                jsonArray = jsonObject.getJSONArray("All_CompanyLocalGroup_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("Company_SubClassID");
                    parentTeamUD = JO.getString("CompanyTeam_ID");
                    teamName = JO.getString("Comapny_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    companyLocalTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllFaithLocalTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("faithGroupID","UTF-8") + "=" + URLEncoder.encode(FaithGroupID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_FaithLocalGroup.php";
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
                jsonArray = jsonObject.getJSONArray("All_FaithLocalGroup_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("Faith_SubClassID");
                    parentTeamUD = JO.getString("FaithTeam_ID");
                    teamName = JO.getString("Faith_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    faithLocalTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllGymLocalTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("gymBrandID","UTF-8") + "=" + URLEncoder.encode(GymBrandID,"UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_GymLocalGroup.php";
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
                jsonArray = jsonObject.getJSONArray("All_GymLocalGroup_Teams");


                int count = 0;
                String teamId,parentTeamUD,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("Gym_SubClassID");
                    parentTeamUD = JO.getString("GymTeam_ID");
                    teamName = JO.getString("Gym_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setParentTeamID(parentTeamUD);
                    model.setTeamID(teamId);
                    model.setTeamName(teamName);
                    gymBrandLocalTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BackgroundTaskForAllCommunityTeams extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("highSchoolID","UTF-8") + "=" + URLEncoder.encode("HighSchoolTeamID","UTF-8");

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
            json_url = "http://edevz.com/cross_comp/get_all_Community.php";
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
                jsonArray = jsonObject.getJSONArray("All_Community_Teams");


                int count = 0;
                String CommunityTeam_ID,Country_ID,State_ID,City_ID,Postal_Code_ID,CommunityTeam_Name;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    CommunityTeam_ID = JO.getString("CommunityTeam_ID");
                    Country_ID = JO.getString("Country_ID");
                    State_ID = JO.getString("State_ID");
                    City_ID = JO.getString("City_ID");
                    Postal_Code_ID = JO.getString("Postal_Code_ID");
//                    CommunityTeam_Name = JO.getString("CommunityTeam_Name");
                    CommunityTeam_Name = JO.getString("PostalCode");


                    Log.e("fdsgsdfg", "onPostExecute: "+Country_ID );
                    Log.e("fdsgsdfg", "onPostExecute: "+State_ID );
                    Log.e("fdsgsdfg", "onPostExecute: "+City_ID );
                    Log.e("fdsgsdfg", "onPostExecute: "+Postal_Code_ID );
                    Log.e("fdsgsdfg", "onPostExecute: "+CommunityTeam_ID );
                    Log.e("fdsgsdfg", "onPostExecute: "+CommunityTeam_Name );


                    JoinNewTeamModel model = new JoinNewTeamModel();
                    model.setTeamCategory("SubClass");
                    model.setCountryTeamID(Country_ID);
                    model.setStateTeamID(State_ID);
                    model.setCityTeamID(City_ID);
                    model.setPostalCodeID(Postal_Code_ID);
                    model.setCommunityTeamID(CommunityTeam_ID);

                    model.setParentTeamID(City_ID);
                    model.setTeamID(CommunityTeam_ID);
                    model.setTeamName(CommunityTeam_Name);
                    communityTeamsArray.add(model);
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}