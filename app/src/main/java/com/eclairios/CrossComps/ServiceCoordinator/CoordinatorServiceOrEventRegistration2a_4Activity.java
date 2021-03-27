package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.eclairios.CrossComps.MainFragments.TeamsFragment;
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

public class CoordinatorServiceOrEventRegistration2a_4Activity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    ArrayList services = new ArrayList();
    AutoCompleteTextView acTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_or_event_registration2a_4);
        getSupportActionBar().hide();

        new BackgroundTaskForUniqueServices().execute();

        Log.e("sadsadsadsad", "onCreate: "+services);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, services);
        //Find TextView control
        acTextView = (AutoCompleteTextView) findViewById(R.id.service_name);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);


        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                acTextView.setText("");
                Toast.makeText(CoordinatorServiceOrEventRegistration2a_4Activity.this, "Enter Unique Service", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Registration3_4(View view) {
        if(TextUtils.isEmpty(acTextView.getText().toString())){
            Toast.makeText(this, "Service Name Required", Toast.LENGTH_LONG).show();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("serviceName",acTextView.getText().toString());
            editor.apply();
            startActivity(new Intent(CoordinatorServiceOrEventRegistration2a_4Activity.this,CoordinatorRegistration3_4Activity.class));
        }

    }

    class BackgroundTaskForUniqueServices extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/getAllServices.php";
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
                    jsonArray = jsonObject.getJSONArray("services");


                    int count = 0;
                    String serviceId,serviceName;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        serviceId = JO.getString("Service_ID");
                        serviceName = JO.getString("Service_Name");

                            services.add(serviceName);

                        count++;

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}