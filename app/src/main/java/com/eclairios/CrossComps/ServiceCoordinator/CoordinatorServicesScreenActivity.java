package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.eclairios.CrossComps.Adapter.AdapterHorizontal;
import com.eclairios.CrossComps.Adapter.RegisterServiceEventAdapter;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.Model.RegisterServiceEventModel;
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

public class CoordinatorServicesScreenActivity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1;

    RecyclerView ServiceRecyclerView;
    RegisterServiceEventAdapter registerServiceEventAdapter;
    ArrayList<RegisterServiceEventModel> registerServiceEventModels = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_services_screen);
        getSupportActionBar().hide();


        ServiceRecyclerView = findViewById(R.id.registerServiceList);
        registerServiceEventAdapter = new RegisterServiceEventAdapter(registerServiceEventModels, this );
        ServiceRecyclerView.setAdapter(registerServiceEventAdapter);



        new BackgroundTaskServiceEvent().execute();
    }

    public void CoordinatorServiceEventRegistration(View view) {
        startActivity(new Intent(CoordinatorServicesScreenActivity.this,CoordinatorServiceOrRegistration1_4Activity.class));
    }




    class BackgroundTaskServiceEvent extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicesScreenActivity.this);
                String currentUserID = preferences.getString("CurrentUserId", "");

                String data = URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")  ;
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
            json_url = "http://edevz.com/cross_comp/getServiceAndEventForAffiliate.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string != null){

                Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("event");
                    jsonArray1 = jsonObject.getJSONArray("services");


                    int count = 0;

                    String eventId,eventName;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        eventId = JO.getString("Event_ID");
                        eventName = JO.getString("Event_Name");

                            RegisterServiceEventModel registerServiceEventModel = new RegisterServiceEventModel();
                            registerServiceEventModel.setServiceID(eventId);
                            registerServiceEventModel.setServiceName(eventName);
                            registerServiceEventModels.add(registerServiceEventModel);

                        count++;

                    }



                    int count1 = 0;

                    String serviceId,serviceName;
                    while(count1 < jsonArray1.length())
                    {
                        JSONObject JO = jsonArray1.getJSONObject(count1);
                        serviceId = JO.getString("Service_ID");
                        serviceName = JO.getString("Service_Name");

                        RegisterServiceEventModel registerServiceEventModel = new RegisterServiceEventModel();
                        registerServiceEventModel.setServiceID(serviceId);
                        registerServiceEventModel.setServiceName(serviceName);
                        registerServiceEventModels.add(registerServiceEventModel);

                        count1++;

                    }

                    registerServiceEventAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }
    }
}