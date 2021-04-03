package com.eclairios.CrossComps.EventAndServices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Maps.ServiceProviderMapsActivity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCrossCompActivity extends AppCompatActivity {
    Toolbar appBarLayout;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1,jsonArray2;

    String Send_Event_Time_ID,EVENT_ID;
    String eventName;
    String eventAddress;


    TextView event_name_txt,event_address_txt,current_username_txt,event_day_txt,event_date_txt,event_time_txt,instruction_txt;

    String Event_Time_ID1,Event_Time_ID2,Event_Time_ID3,E_ID,currentUserID,E_Reservation_ID;

    RadioButton timeOne,timeTwo,timeThree;

    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_cross_comp);

        appBarLayout = findViewById(R.id.toolbar);
        appBarLayout.inflateMenu(R.menu.menu);

        setActionBar(appBarLayout);
        getSupportActionBar().hide();
        getActionBar().setTitle("");

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
            EVENT_ID = bundle.getString("EVENT_ID");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Send_Event_Time_ID",Send_Event_Time_ID);
            editor.putString("eventName",eventName);
            editor.putString("eventAddress",eventAddress);
            editor.putString("EVENT_ID",EVENT_ID);
            editor.apply();
        }else{

            Send_Event_Time_ID = preferences.getString("Send_Event_Time_ID", "");
            eventName = preferences.getString("eventName", "");
            eventAddress = preferences.getString("eventAddress", "");
            EVENT_ID = preferences.getString("EVENT_ID","");
        }

        Log.e("testingssssss", "onCreate: "+ firstName +" "+lastName);
        Log.e("testingssssssEventID", "onCreate: "+Send_Event_Time_ID);
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
            case R.id.map:
                startActivity(new Intent(EventCrossCompActivity.this, ServiceProviderMapsActivity.class));
                return true;
            case R.id.change:
                MakeAppointment(Send_Event_Time_ID);
                return true;
            case R.id.cancel:

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventCrossCompActivity.this);
                String currentUserID = preferences.getString("CurrentUserId", "");

                new AlertDialog.Builder(EventCrossCompActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm Cancel")
                        .setMessage("Are you sure you want to cancel CrossComps Reservation?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String method = "Cancel_event_reservation";
                                BackgroundTask backgroundTask = new BackgroundTask(EventCrossCompActivity.this);
                                backgroundTask.execute(method,Send_Event_Time_ID,currentUserID);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventCrossCompActivity.this,Dashboard.class);
        intent.putExtra("fragmentNumber",3);
        startActivity(intent);
    }

    public void MakeAppointment(String serviceID) {

        new BackgroundTasksLoadDatas().execute();
        Log.e("hfhdfhshowdhd", "MakeAppointment: " + serviceID);


        Button sendRequestBtn;


        AlertDialog.Builder builder = new AlertDialog.Builder(EventCrossCompActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_change_event, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        radioGroup = (RadioGroup) dialogView.findViewById(R.id.radio);
        timeOne = dialogView.findViewById(R.id.timeOne);
        timeTwo = dialogView.findViewById(R.id.timeTwo);
        timeThree = dialogView.findViewById(R.id.timeThree);
        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        try {
            WaitDialog.showDialog(EventCrossCompActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.timeOne:
                        Send_Event_Time_ID = Event_Time_ID1;

                        Log.e("IDTest", "onCheckedChanged: " + timeOne.getText());
                        Log.e("IDTest", "onCheckedChanged: " + Event_Time_ID1);
                        break;
                    case R.id.timeTwo:
                        Send_Event_Time_ID = Event_Time_ID2;
                        Log.e("IDTest", "onCheckedChanged: " + timeTwo.getText());
                        Log.e("IDTest", "onCheckedChanged: " + Event_Time_ID2);
                        break;
                    case R.id.timeThree:
                        Send_Event_Time_ID = Event_Time_ID3;
                        Log.e("IDTest", "onCheckedChanged: " + timeThree.getText());
                        Log.e("IDTest", "onCheckedChanged: " + Event_Time_ID3);
                        break;
                }


            }
        });


        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventCrossCompActivity.this);
                currentUserID = preferences.getString("CurrentUserId", "");

                String firstName = preferences.getString("First_Name", "");
                String lastName = preferences.getString("Last_Name", "");


                Log.e("Namesssssss", "onClick: " + firstName);
                Log.e("Namesssssss", "onClick: " + lastName);

                Log.e("Send_ID", "onCheckedChanged: " + Send_Event_Time_ID);
                Log.e("Send_ID", "onCheckedChanged: " + currentUserID);

                String method = "Change_event_reservation";

                BackgroundTask backgroundTask = new BackgroundTask(EventCrossCompActivity.this);
                backgroundTask.execute(method, Send_Event_Time_ID, currentUserID,E_Reservation_ID);

                Intent intent = new Intent(EventCrossCompActivity.this,EventCrossCompActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Send_Event_Time_ID",Send_Event_Time_ID);
                intent.putExtra("eventName",event_name_txt.getText());
                intent.putExtra("eventAddress",event_address_txt.getText());
                intent.putExtra("EVENT_ID",EVENT_ID);
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                startActivity(intent);

            }
        });


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
                jsonArray2 = jsonObject.getJSONArray("E_Reservation_ID");




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

                int count2 = 0;


                while(count2 < jsonArray2.length())
                {
                    JSONObject JO = jsonArray2.getJSONObject(count2);
                    E_Reservation_ID = JO.getString("E_Reservation_ID");
                    count2++;

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


    class BackgroundTasksLoadDatas extends AsyncTask<String, Void, String>
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


                String data = URLEncoder.encode("eventID","UTF-8") + "=" + URLEncoder.encode(EVENT_ID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_event_time.php";
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


                String Day,Start_Time,End_Time;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    E_ID = JO.getString("E_ID");
                    EVENT_ID = JO.getString("Event_ID");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");


                    //     Log.e("EID", "onPostExecute: "+E_ID );




                    String _24HourTime = Start_Time;
                    java.text.SimpleDateFormat _24HourSDF = new java.text.SimpleDateFormat("HH:mm");
                    java.text.SimpleDateFormat _12HourSDF = new java.text.SimpleDateFormat("hh:mm a");
                    Date _24HourDt = _24HourSDF.parse(_24HourTime);



                    String _24HourTime1 = End_Time;
                    java.text.SimpleDateFormat _24HourSDF1 = new java.text.SimpleDateFormat("HH:mm");
                    Date _24HourDt1 = _24HourSDF1.parse(_24HourTime1);


                    String newStartTime = _12HourSDF.format(_24HourDt);
                    String newEndTime = _12HourSDF.format(_24HourDt1);


                    final String text = newStartTime.toLowerCase() + " - " + newEndTime.toLowerCase();
                    if(count == 0){
                        timeOne.setText(text);
                        Event_Time_ID1 = E_ID;
                    }else if(count == 1){
                        timeTwo.setText(text);
                        Event_Time_ID2 = E_ID;
                    }else if(count == 2){
                        timeThree.setText(text);
                        Event_Time_ID3 = E_ID;
                    }


                    count++;

                }
                try{
                    WaitDialog.hideDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(timeOne.isChecked()) {
                    Send_Event_Time_ID = Event_Time_ID1;
                    Log.e("onesssss", "onCheckedChanged: " + timeOne.getText());
                    Log.e("onesssss", "onPostExecute: "+Event_Time_ID1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}