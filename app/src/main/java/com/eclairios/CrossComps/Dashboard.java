package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eclairios.CrossComps.Adapter.AdapterHorizontal;
import com.eclairios.CrossComps.Model.ModelHorizontal;

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

public class Dashboard extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String lat,lng;

    RecyclerView recyclerView;
    AdapterHorizontal adapterHorizontal;
    ArrayList<ModelHorizontal> chatitem = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.list);
        adapterHorizontal = new AdapterHorizontal( chatitem,Dashboard.this );
        recyclerView.setAdapter(adapterHorizontal);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appVersion","2");
        editor.apply();

    }

    class BackgroundTask extends AsyncTask<String, Void, String>
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



                Log.e("dhdhdhd", "doInBackground: "+lat);
                Log.e("dhdhdhd", "doInBackground: "+lng);


                String data = URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(lat,"UTF-8")+ "&"+
                        URLEncoder.encode("lon","UTF-8") + "=" + URLEncoder.encode(lng,"UTF-8") ;
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
            json_url = "http://edevz.com/cross_comp/get_all_coordinator.php";
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

                String firstName,lastName,coordinatorId,coordinatorName,address;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    coordinatorId = JO.getString("User_ID");
                    firstName = JO.getString("First_Name");
                    lastName = JO.getString("Last_Name");

                    coordinatorName = firstName+" "+lastName;
                    address = JO.getString("Address");

                    Log.e("jdjdud", "onPostExecute: "+ coordinatorName);
                    Log.e("jdjdud", "onPostExecute: "+ address);

                    ModelHorizontal users = new ModelHorizontal();
                    users.setCoordinatorID(coordinatorId);
                    users.setCoordinatorName(coordinatorName);
                    users.setCoordinatorAddress(address);


                    chatitem.add(users);
                    count++;

                }

                adapterHorizontal.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatitem.clear();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            lat = bundle.getString("lat");
            lng = bundle.getString("lng");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("lat",lat);
            editor.putString("lng",lng);
            editor.apply();

        }

        Log.e("hdhddh", "onStart: "+lat+lng );
        new BackgroundTask().execute();
    }
}