package com.eclairios.CrossComps.Teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.MyCrossCompUserSelectedTeamAdapter;
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
import java.util.Timer;
import java.util.TimerTask;

public class JoinTeamsActivity extends AppCompatActivity implements InterfaceForSetTeams {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView myCrossCompTeamList1;
    MyCrossCompUserSelectedTeamAdapter myCrossCompUserSelectedTeamAdapter;
    ArrayList<MyCrossCompAllTeamsMainModel> myCrossCompAllTeamsMainModels2 = new ArrayList<>();

    String currentUserID;

    EditText postal_code_for_team,city_team,county_team,conference_team,state_team,union_team,country_team,division_team,world_team;

    ArrayList postalCodeForTeams = new ArrayList();

    String SelectedPostalCode;
    String SelectedTeamGeneralID;
    Button joinTeamBtn;



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

    ArrayList<JoinNewTeamModel> militaryBranchTeamsArray = new ArrayList();
    private ArrayAdapter<JoinNewTeamModel> militaryBranchTeam_adapter;

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



    AutoCompleteTextView highSchoolTeamNameAutoText,highSchoolClassTeamNameAutoText,collegeTeamNameAutoText,collegeClassTeamNameAutoText,
            professionalSchoolTeamNameAutoText,professionalSchoolClassTeamNameAutoText,militaryBranchAutoText,militaryBranchLocalAutoText,
            occupationTeamNameAutoText,occupationTeamLocalNameAutoText,companyTeamNameAutoText,companyTeamLocalNameAutoText,
            faithTeamNameAutoText,faithTeamLocalNameAutoText,gymBrandTeamNameAutoText,gymBrandTeamLocalNameAutoText,friendFamilyTeamNameAutoText;


    Button saveHighSchoolTeamBtn,becomeVolunteerBtn;

    String HighSchoolTeamID,HighSchoolClassTeamID,CollegeTeamID,CollegeClassTeamID,ProfessionalSchoolID,ProfessionalSchoolClassID,MilitaryBranchID,MilitaryBranchLocalID,
            OccupationID,OccupationLocalID,CompanyID,CompanyLocalID,FaithGroupID,FaithGroupLocalID,GymBrandID,GymBrandLocalID,FriendFamilyID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_teams);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentUserID = preferences.getString("CurrentUserId", "");


        myCrossCompTeamList1 = findViewById(R.id.myCrossCompTeamList1);
        myCrossCompUserSelectedTeamAdapter = new MyCrossCompUserSelectedTeamAdapter(this,myCrossCompAllTeamsMainModels2, JoinTeamsActivity.this);
        myCrossCompTeamList1.setAdapter(myCrossCompUserSelectedTeamAdapter);

        new BackgroundTaskForCustomTeamSelect().execute();
    }

    @Override
    public void TeamsPostalCode(String SelectedGeneralTeamID) {

    }

    @Override
    public void TeamsChurch(String SelectedTeamID) {

    }

    public void SelectHighSchool(){

        new BackgroundTaskForAllHighSchoolTeams().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_high_school_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        saveHighSchoolTeamBtn = dialogView.findViewById(R.id.saveHighSchoolTeam);
        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);


        highSchoolTeamsArray.clear();

        highSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, highSchoolTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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

                    new BackgroundTaskForAllHighSchoolClassTeams().execute();

                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_for_high_school_subclass,null);
                    builder.setCancelable(true);
                    builder.setView(dialogView);

                    becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

                    highSchoolClassTeamsArray.clear();

                    highSchoolClassTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, highSchoolClassTeamsArray);
                    highSchoolClassTeamNameAutoText = (AutoCompleteTextView) dialogView.findViewById(R.id.highSchoolClassTeamNameAutoText);
                    highSchoolClassTeamNameAutoText.setThreshold(1);
                    highSchoolClassTeamNameAutoText.setAdapter(highSchoolClassTeam_adapter);

                    AlertDialog pickFileImage = builder.create();
                    pickFileImage.show();

                    highSchoolClassTeamNameAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                            becomeVolunteerBtn.setVisibility(View.VISIBLE);
                            becomeVolunteerBtn.getLayoutParams().width=500;
                            becomeVolunteerBtn.setText("Confirmation");


                            highSchoolClassTeam_adapter = (ArrayAdapter<JoinNewTeamModel>) highSchoolClassTeamNameAutoText.getAdapter();
                            HighSchoolClassTeamID = highSchoolClassTeam_adapter.getItem(position).getTeamID();
                            Log.e("dsfdsfsd", "onItemClick: "+HighSchoolClassTeamID );


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

                                                                int count = 0;
                                                                while(count < highSchoolClassTeamsArray.size())
                                                                {
                                                                    if(highSchoolClassTeamsArray.get(count).getTeamName().equals(words)){
                                                                        becomeVolunteerBtn.setText("Confirmation");
                                                                        becomeVolunteerBtn.getLayoutParams().width=500;
                                                                        break;
                                                                    }else{
                                                                        Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
                                                                        becomeVolunteerBtn.setVisibility(View.VISIBLE);
                                                                        becomeVolunteerBtn.getLayoutParams().width=1200;
                                                                        becomeVolunteerBtn.setText("Become a CrossComp Volunteer to create a Team for your High School Class");
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
            }
        });


    }

    public void CollegeUniversity(){

        new BackgroundTaskForAllCollegeClass().execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_college_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        collegeUniversityTeamsArray.clear();

        collegeUniversityTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, collegeUniversityTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_professional_school_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);



        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        professionalSchoolTeamsArray.clear();

        professionalSchoolTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, professionalSchoolTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_military_branch_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        militaryBranchTeamsArray.clear();

        militaryBranchTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, militaryBranchTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialg_for_occupation_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        occupationTeamsArray.clear();

        occupationTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, occupationTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_company_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        companyTeamsArray.clear();

        companyTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, companyTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_faith_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        faithTeamsArray.clear();

        faithTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, faithTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_gym_brand_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

        gymBrandTeamsArray.clear();

        gymBrandTeam_adapter = new ArrayAdapter<JoinNewTeamModel>(JoinTeamsActivity.this,android.R.layout.simple_list_item_1, gymBrandTeamsArray);
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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(JoinTeamsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_friend_family_team_selection,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        becomeVolunteerBtn = dialogView.findViewById(R.id.becomeVolunteer);

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

                                        runOnUiThread(new Runnable() {
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
                                                            Toast.makeText(JoinTeamsActivity.this, "NOT LISTED - INVALID ENTRY", Toast.LENGTH_LONG).show();
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

    private class BackgroundTaskForAllHighSchoolClassTeams extends AsyncTask<String, Void, String>
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


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("All_HighSchoolClass_Teams");


                int count = 0;
                String teamId,teamName;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    teamId = JO.getString("HS_SubClassID");
                    teamName = JO.getString("HS_SubClassName");

                    Log.e("dsfsfdsf", "onPostExecute: "+teamId);
                    Log.e("dsfsfdsf", "onPostExecute: "+teamName);


                    JoinNewTeamModel model = new JoinNewTeamModel();
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