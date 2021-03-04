package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterCurrentTeams;
import com.eclairios.CrossComps.Model.ModelCurrentTeams;

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

public class Profile extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView first_Name,last_Name,phone_number,email,postal_code,address,gender,age,promoter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        first_Name = findViewById(R.id.first_Name);
        last_Name = findViewById(R.id.last_Name);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        postal_code = findViewById(R.id.postal_code);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        promoter = findViewById(R.id.promoter);
    }

    public void ToHome(View view) {
        startActivity(new Intent(Profile.this,Participent.class));
    }

    public void UpdateProfile(View view) {

        Toast.makeText(this, "Update Profile", Toast.LENGTH_SHORT).show();


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


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
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
            json_url = "http://edevz.com/cross_comp/profile_get_data.php";
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
                String str_first_Name,str_last_Name,str_phone_number,str_email,str_postal_code,str_address,str_gender,str_age,str_promoter;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    str_first_Name = JO.getString("First_Name");
                    str_last_Name = JO.getString("Last_Name");
                    str_phone_number = JO.getString("Phone");
                    str_email = JO.getString("Email");
                    str_postal_code = JO.getString("Postal_Code");
                    str_address = JO.getString("Address");
                    str_gender = JO.getString("Gender");
                    str_age = JO.getString("Age");
                    str_promoter = JO.getString("Name_Promoter");

                    Log.e("profileTest", "onPostExecute: "+ str_first_Name);


                    first_Name.setText(str_first_Name);
                    last_Name.setText(str_last_Name);
                    phone_number.setText(str_phone_number);
                    email.setText(str_email);
                    postal_code.setText(str_postal_code);
                    address.setText(str_address);
                    gender.setText(str_gender);
                    age.setText(str_age);
                    promoter.setText(str_promoter);

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