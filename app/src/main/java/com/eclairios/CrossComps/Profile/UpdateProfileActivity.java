package com.eclairios.CrossComps.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.AllTeamCategoryActivity;
import com.google.gson.JsonArray;

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

public class UpdateProfileActivity extends AppCompatActivity {

  private   EditText first_Name,last_Name,phone_number,email,postal_code,address,gender,age,promoter;
   private String str_first_Name,str_last_Name,str_phone_number,str_email,str_postal_code,str_address,str_gender,str_age,str_promoter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile2);
        getSupportActionBar().hide();

        first_Name = findViewById(R.id.first_Namee);
        last_Name = findViewById(R.id.last_Namee);
        phone_number = findViewById(R.id.phone_numberr);
        email = findViewById(R.id.emaill);
        postal_code = findViewById(R.id.postal_codee);
        address = findViewById(R.id.addresss);
        gender = findViewById(R.id.genderr);
        age = findViewById(R.id.agee);

//        intent.putExtra("str_first_Name", str_first_Name);
//        intent.putExtra("str_last_Name", str_last_Name);
//        intent.putExtra("str_email", str_email);
//        intent.putExtra("str_postal_code", str_postal_code);
//        intent.putExtra("str_address", str_address);
//        intent.putExtra("str_gender", str_gender);
//        intent.putExtra("str_age", str_age);
//        intent.putExtra("str_promoter", str_promoter);
//        startActivity(intent);


        String str_first_Name = getIntent().getStringExtra("str_first_Name");
        String str_last_Name = getIntent().getStringExtra("str_last_Name");
        String phone_numberr = getIntent().getStringExtra("str_phone_number");
        String emaill = getIntent().getStringExtra("str_email");
        String postal_codee = getIntent().getStringExtra("str_postal_code");
        String addresss = getIntent().getStringExtra("str_address");
        String genderr = getIntent().getStringExtra("str_gender");
        String agee = getIntent().getStringExtra("str_age");
        String str_promoter = getIntent().getStringExtra("str_promoter");

        Log.e("fdsfsd", "onCreate: "+str_first_Name);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UpdateProfileActivity.this);
        String currentUserID = preferences.getString("CurrentUserId", "");

        first_Name.setText(str_first_Name);
        last_Name.setText(str_last_Name);
        phone_number.setText(phone_numberr);
        email.setText(emaill);
        postal_code.setText(postal_codee);
        address.setText(addresss);
        gender.setText(genderr);
        age.setText(agee);


    }

    public void UpdateProfileDetail(View view) {
        new BackgroundTasks1().execute();
    }


    class BackgroundTasks1 extends AsyncTask<String, Void, String>
    {
        String json_url;
        private String json_string;

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

                Log.e("dsfdsf", "doInBackground: " );
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UpdateProfileActivity.this);
                String currentUserID = preferences.getString("CurrentUserId", "");


                String data = URLEncoder.encode("User_id","UTF-8") + "=" + URLEncoder.encode("4","UTF-8") + "&"+
                        URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(first_Name.getText().toString(),"UTF-8") + "&"+
                        URLEncoder.encode("Phone_number","UTF-8") + "=" + URLEncoder.encode(phone_number.getText().toString(),"UTF-8");


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
            json_url = "http://edevz.com/cross_comp/update_profile.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("abfdsfsdcd", "onCreate: "+json_string );


            try {

            JSONObject    jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                int count =0;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

//                    str_first_Name = JO.getString("First_Name");
//                    str_last_Name = JO.getString("Last_Name");
//                    str_phone_number = JO.getString("Phone");
//                    str_email = JO.getString("Email");
//                    str_postal_code = JO.getString("Postal_Code");
//                    str_address = JO.getString("Address");
//                    str_gender = JO.getString("Gender");
//                    str_age = JO.getString("Age");
//                    str_promoter = JO.getString("Name_Promoter");
//
//                    Log.e("profileTest", "onPostExecute: "+ str_first_Name);
//
//
//
//
//                    first_Name.setText(str_first_Name);
//                    last_Name.setText(str_last_Name);
//                    phone_number.setText(str_phone_number);
//                    email.setText(str_email);
//                    postal_code.setText(str_postal_code);
//                    address.setText(str_address);
//                    gender.setText(str_gender);
//                    age.setText(str_age);
//                    promoter.setText(str_promoter);
//
//                    count++;

                }



            } catch (JSONException e) {
                Log.e("dfdsf", "onPostExecute: "+ e);
                e.printStackTrace();
            }

        }
    }

}