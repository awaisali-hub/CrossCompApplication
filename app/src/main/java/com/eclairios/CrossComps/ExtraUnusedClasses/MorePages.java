package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MorePages extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView OptiHealthNetwork,Med_FitClinics,OptiHealthSports,OptiHealthInstitute,OptiHealth_Pledge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_pages);

        OptiHealthNetwork = findViewById(R.id.OptiHealthNetwork);
        Med_FitClinics = findViewById(R.id.Med_FitClinics);
        OptiHealthSports = findViewById(R.id.OptiHealthSports);
        OptiHealthInstitute = findViewById(R.id.OptiHealthInstitute);
        OptiHealth_Pledge = findViewById(R.id.OptiHealth_Pledge);

    }

    public void TooHome(View view) {
        startActivity(new Intent(MorePages.this, Participent.class));

    }

    class BackgroundTasks extends AsyncTask<String, Void, String>
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


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MorePages.this);
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
            json_url = "http://edevz.com/cross_comp/getOptiHelathNetwork.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("abcd", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");

                int count =0;
                String str_OptiHealthNetwork,str_Med_FitClinics,str_OptiHealthSports,str_OptiHealthInstitute,str_OptiHealth_Pledge;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    str_OptiHealthNetwork = JO.getString("OptiHealth_Network");
                    str_Med_FitClinics = JO.getString("med_fit_clinics");
                    str_OptiHealthSports = JO.getString("OptiHealth_Sports");
                    str_OptiHealthInstitute = JO.getString("OptiHealth_Institute");
                    str_OptiHealth_Pledge = JO.getString("OptiHealth_Pledge");



//                    OptiHealthNetwork.setText(str_OptiHealthNetwork);
//                    Med_FitClinics.setText(str_Med_FitClinics);
//                    OptiHealthSports.setText(str_OptiHealthSports);
//                    OptiHealthInstitute.setText(str_OptiHealthInstitute);
//                    OptiHealth_Pledge.setText(str_OptiHealth_Pledge);
                    count++;

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new BackgroundTasks().execute();
    }
}