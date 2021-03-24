package com.eclairios.CrossComps.Judge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.CrossCompAffiliate.AffiliateDashboardActivity;
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

public class JudgeHomePageParticipantRegistrationActivity extends AppCompatActivity {

    int keyDel;
    EditText participant_phone1,participant_phone2;
    Button judge_StrBtn;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String selectedUserPhone,User1ID,User2ID;
    TextView participant1Name,participant2Name;
    int textViewCheck = 0;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_home_page_participant_registration);
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        participant_phone1 = findViewById(R.id.participant_phone1);
        participant_phone2 = findViewById(R.id.participant_phone2);
        judge_StrBtn = findViewById(R.id.judge_StrBtn);


        participant1Name = findViewById(R.id.participant1Name);
        participant2Name = findViewById(R.id.participant2Name);


        judge_StrBtn.setClickable(false);

        participant_phone1.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant_phone1.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = participant_phone1.getText().length();
                    if(len == 3) {
                        participant_phone1.setText(participant_phone1.getText() + "-");
                        participant_phone1.setSelection(participant_phone1.getText().length());
                    }else if(len == 8){
                        participant_phone1.setText(participant_phone1.getText() + "-");
                        participant_phone1.setSelection(participant_phone1.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

                if(participant_phone1.getText().toString().length()==13&&participant_phone2.getText().toString().isEmpty())     //size as per your requirement
                {
                    textViewCheck = 0;
                    selectedUserPhone = participant_phone1.getText().toString();
                    new BackgroundTaskGetUserNameAccordingToPhoneNumber().execute();
                    participant_phone2.requestFocus();

                }else if(participant_phone2.getText().toString().length()==13 && participant_phone1.getText().toString().length()==13){
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    judge_StrBtn.setClickable(true);
                    judge_StrBtn.setEnabled(true);
                } else if (participant_phone1.getText().toString().length()<13){
                    judge_StrBtn.setEnabled(false);
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));

                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        participant_phone2.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant_phone2.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = participant_phone2.getText().length();
                    if(len == 3) {
                        participant_phone2.setText(participant_phone2.getText() + "-");
                        participant_phone2.setSelection(participant_phone2.getText().length());
                    }else if(len == 8){
                        participant_phone2.setText(participant_phone2.getText() + "-");
                        participant_phone2.setSelection(participant_phone2.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
                if(participant_phone2.getText().toString().length()==13 && participant_phone1.getText().toString().length()==13)     //size as per your requirement
                {
                    textViewCheck = 1;
                    selectedUserPhone = participant_phone2.getText().toString();

                    if(selectedUserPhone.equals("000-0000-0000")) {
                        participant2Name.setText("None");
                    }else{
                        new BackgroundTaskGetUserNameAccordingToPhoneNumber().execute();
                    }

                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    judge_StrBtn.setClickable(true);
                    judge_StrBtn.setEnabled(true);
                }
                else if (participant_phone2.getText().toString().length()<13){
                    judge_StrBtn.setEnabled(false);
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });


                            judge_StrBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(participant1Name.getText().toString().equals("Name of Participant #1")){
                                Toast.makeText(JudgeHomePageParticipantRegistrationActivity.this, "Participant One Name Required!!!", Toast.LENGTH_LONG).show();
                            }else{

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("meters",null);
                                editor.putString("meterGrade",null);
                                editor.putString("meters1",null);
                                editor.putString("meterGrade1",null);
                                editor.putInt("check_user",0);
                                editor.putInt("check_user1",0);
                                editor.apply();

                                Intent intent = new Intent(JudgeHomePageParticipantRegistrationActivity.this,Judge2aRun_Walk_TestActivity.class);
                                intent.putExtra("User1Name",participant1Name.getText().toString());
                                intent.putExtra("User2Name",participant2Name.getText().toString());
                                startActivity(intent);
                            }

                        }
                    });


    }

    public void Dashboard(View view) {
        startActivity(new Intent(JudgeHomePageParticipantRegistrationActivity.this, AffiliateDashboardActivity.class));
    }


    class BackgroundTaskGetUserNameAccordingToPhoneNumber extends AsyncTask<String, Void, String>
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



                String data = URLEncoder.encode("user_phone_number","UTF-8") + "=" + URLEncoder.encode(selectedUserPhone,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_user_according_phone_number.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;


            Log.e("bcjknjkksdjc ", "onCreate: " + json_string);

            if (json_string != null) {


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");


                ////////////////////////////////////////

                int count = 0;

                String userID, firstName, lastName;
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    userID = JO.getString("User_ID");
                    firstName = JO.getString("First_Name");
                    lastName = JO.getString("Last_Name");


                    if (textViewCheck == 0) {
                        participant1Name.setText(firstName + " " + lastName);
                        User1ID = userID;

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("User1ID", userID);
                        editor.apply();
                    } else {
                        participant2Name.setText(firstName + " " + lastName);
                        User2ID = userID;

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("User2ID", userID);
                        editor.apply();
                    }

                    count++;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        }
    }
}