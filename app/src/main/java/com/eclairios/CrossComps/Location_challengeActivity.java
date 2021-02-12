package com.eclairios.CrossComps;

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

import com.eclairios.CrossComps.Adapter.AdapterAllUser;
import com.eclairios.CrossComps.Interface.IndividualChallengeInterface;
import com.eclairios.CrossComps.Model.ModelAllUser;

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

public class Location_challengeActivity extends AppCompatActivity implements IndividualChallengeInterface {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView userListRecycler;
    AdapterAllUser adapterAllUser;
    ArrayList<ModelAllUser> modelAllUsers = new ArrayList<>();
    String challengeType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_challenge);

        userListRecycler = findViewById(R.id.userList);
        adapterAllUser= new AdapterAllUser( modelAllUsers,Location_challengeActivity.this,this );
        userListRecycler.setAdapter(adapterAllUser);

        Bundle bundle = getIntent().getExtras();
        challengeType = bundle.getString("challengeType");

    }

    class BackgroundTasksAllUser extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Location_challengeActivity.this);
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                String serviceID = preferences.getString("service_ID_forChallenge", "");


                Log.e("ServicessssIDIIDDIDI", "doInBackground: "+serviceID );




                Log.e("latlng", "doInBackground: "+lat+"\n"+lng );


                String data = URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(lat,"UTF-8")+ "&"+
                        URLEncoder.encode("lon","UTF-8") + "=" + URLEncoder.encode(lng,"UTF-8")  +"&"+
                        URLEncoder.encode("service_ID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/getAllUserNewLocation.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("locationbase", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");


                int count = 0;

                String userID,firstName,lastName,FullName,UserScore,gender,age,address;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    userID = JO.getString("User_ID");
                    firstName = JO.getString("First_Name");
                    lastName = JO.getString("Last_Name");
                    gender = JO.getString("Gender");
                    age = JO.getString("Age");
                    address = JO.getString("Address");

                    FullName = firstName + " "+ lastName;
                    UserScore = JO.getString("score");

                    Log.e("testingMessage", "onPostExecute: "+FullName);
                    Log.e("testingMessage", "onPostExecute: "+UserScore);

                    ModelAllUser allUser = new ModelAllUser();
                    allUser.setUserID(userID);
                    allUser.setUserName(FullName);
                    allUser.setUserScore(UserScore);
                    allUser.setGender(gender);
                    allUser.setAge(age);
                    allUser.setAddress(address);

                    modelAllUsers.add(allUser);

                    count++;

                }

                adapterAllUser= new AdapterAllUser( modelAllUsers,Location_challengeActivity.this,Location_challengeActivity.this);
                userListRecycler.setAdapter(adapterAllUser);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        modelAllUsers.clear();
        new BackgroundTasksAllUser().execute();
    }


    public void MakeIndividualChallenge(String UserID,String score, String gender, String age, String address){

        EditText challengeName;
        Button sendChallenge;
        TextView showChallengeType;

        AlertDialog.Builder builder = new AlertDialog.Builder(Location_challengeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_individual_user_challenge,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        challengeName = dialogView.findViewById(R.id.insertChallengeName);
        sendChallenge = dialogView.findViewById(R.id.sendChallenge);
        showChallengeType = dialogView.findViewById(R.id.ShowChallenge);

        showChallengeType.setText("Challenge To an Individual Participant at Your Near Location");

        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();



        String method = "individualUserChallenge";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Location_challengeActivity.this);
        String CurrentUserID = preferences.getString("CurrentUserId", "");



        sendChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_challengeName = challengeName.getText().toString();
                Log.e("challengeName", "MakeIndividualChallenge: "+str_challengeName );
                if(TextUtils.isEmpty(str_challengeName)){
                    Toast.makeText(Location_challengeActivity.this, "Challenge Name Required", Toast.LENGTH_SHORT).show();
                }else{
                    BackgroundTask backgroundTask = new BackgroundTask(Location_challengeActivity.this);
                    backgroundTask.execute(method,CurrentUserID,UserID,challengeType,str_challengeName,score,gender,age,address,"NearLocationUser");
                    pickFileImage.dismiss();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Location_challengeActivity.this,Challenge.class));
    }
}