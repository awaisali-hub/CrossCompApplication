package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCrossCompActivity extends AppCompatActivity {
    Toolbar appBarLayout;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1;

    String Send_Event_Time_ID;
    String eventName;
    String eventAddress;


    TextView event_name_txt,event_address_txt,current_username_txt,event_day_txt,event_date_txt,event_time_txt,instruction_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_cross_comp);

        appBarLayout = findViewById(R.id.toolbar);
        appBarLayout.inflateMenu(R.menu.menu);

        try{
            WaitDialog.showDialog(EventCrossCompActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }




        event_name_txt = findViewById(R.id.eventNAME);
        event_address_txt = findViewById(R.id.eventADDRESS);
        current_username_txt = findViewById(R.id.CurrentUserName);
        event_day_txt = findViewById(R.id.event_day);
        event_date_txt = findViewById(R.id.event_date);
        event_time_txt = findViewById(R.id.event_time);
        instruction_txt = findViewById(R.id.instructionsText);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventCrossCompActivity.this);

        String firstName = preferences.getString("First_Name", "");
        String lastName = preferences.getString("Last_Name", "");
        current_username_txt.setText(firstName + " "+lastName);






        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            Send_Event_Time_ID = bundle.getString("Send_Event_Time_ID");
            eventName = bundle.getString("eventName");
            eventAddress = bundle.getString("eventAddress");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Send_Event_Time_ID",Send_Event_Time_ID);
            editor.putString("eventName",eventName);
            editor.putString("eventAddress",eventAddress);
            editor.apply();
        }else{

            Send_Event_Time_ID = preferences.getString("Send_Event_Time_ID", "");
            eventName = preferences.getString("eventName", "");
            eventAddress = preferences.getString("eventAddress", "");
        }

        Log.e("testingssssss", "onCreate: "+ firstName +" "+lastName);
        Log.e("testingssssss", "onCreate: "+Send_Event_Time_ID);
        Log.e("testingssssss", "onCreate: "+ eventName);
        Log.e("testingssssss", "onCreate: "+ eventAddress);


        event_name_txt.setText(eventName);
        event_address_txt.setText(eventAddress);



        new BackgroundTasksLoadData().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item2:
                Log.e("testssssssss", "onOptionsItemSelected: "+"2" );
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    class BackgroundTasksLoadData extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventCrossCompActivity.this);
                String currentUserID = preferences.getString("CurrentUserId", "");


                String data = URLEncoder.encode("event_time_ID","UTF-8") + "=" + URLEncoder.encode(Send_Event_Time_ID,"UTF-8") +"&"+
                        URLEncoder.encode("user_ID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_event_reservation_details.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("bcjknjkksdjc123 ", "onCreate: "+json_string );




            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response_time");
                jsonArray1 = jsonObject.getJSONArray("server_response_day");

                Log.e("test", "onPostExecute: ");


                int count = 0;

                String EVENT_ID,Start_Time,End_Time;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    EVENT_ID = JO.getString("E_ID");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");

                    Log.e("testingssssss", "onPostExecute: "+ EVENT_ID);
                    Log.e("testingssssss", "onPostExecute: "+ Start_Time);
                    Log.e("testingssssss", "onPostExecute: "+ End_Time);

                    final String start_time = Start_Time;
                    final String end_time = End_Time;

                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(start_time);
                    final Date dateObj1 = sdf.parse(end_time);



                    event_time_txt.setText(new SimpleDateFormat("K:mm a").format(dateObj).toLowerCase() +"-"+new SimpleDateFormat("K:mm a").format(dateObj1).toLowerCase());
                    count++;

                }

                int count1 = 0;
                String Day,Date,Instruction;
                while(count1 < jsonArray1.length())
                {
                    JSONObject JO = jsonArray1.getJSONObject(count1);
                    Day = JO.getString("Day");
                    Date = JO.getString("Date");
                    Instruction = JO.getString("Instructions");

                    event_day_txt.setText(Day);
                    instruction_txt.setText(Instruction);

                    String deliveryDate=Date;
                    SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = dateFormatprev.parse(deliveryDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String eventDate = dateFormat.format(d);

                    event_date_txt.setText(eventDate);

                    count1++;

                }
                try{
                    WaitDialog.hideDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }
    }
}