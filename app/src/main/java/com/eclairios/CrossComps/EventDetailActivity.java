package com.eclairios.CrossComps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Model.ModelCoordinaterServicePage;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventDetailActivity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView event_name,event_address,eventDetails;
    String Event_ID,EventName,EventAddress,EventDay,EventDate;
    String Event_Time_ID1,Event_Time_ID2,Event_Time_ID3,E_ID,Send_Event_Time_ID,currentUserID;

    RadioButton timeOne,timeTwo,timeThree;

    RadioGroup radioGroup;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        event_name = findViewById(R.id.event_name);
        event_address = findViewById(R.id.event_address);
        eventDetails = findViewById(R.id.eventDetails);



        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            Event_ID = bundle.getString("coordinatorID");
            EventName = bundle.getString("coordinatorName");
            EventAddress = bundle.getString("coordinatorAddress");
            EventDay = bundle.getString("EventDay");
            EventDate = bundle.getString("EventDate");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("coordinatorName",EventName);
            editor.putString("coordinatorAddress",EventAddress);
            editor.putString("EventDay",EventDay);
            editor.putString("EventDate",EventDate);
            editor.apply();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventDetailActivity.this);
            Event_ID = preferences.getString("coordinatorId", "");
            EventName = preferences.getString("coordinatorName", "");
            EventAddress = preferences.getString("coordinatorAddress", "");
            EventDay = preferences.getString("EventDay", "");
            EventDate = preferences.getString("EventDate", "");
        }


        String deliveryDate = EventDate;
        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormatprev.parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String changedDate = dateFormat.format(d);

        event_name.setText(EventName);
        event_address.setText(EventAddress);
        eventDetails.setText(EventDay+" , "+changedDate);


    }

    public void EventActivityDetails(View view) {
        MakeAppointment(Event_ID);
        new BackgroundTasksLoadData().execute();
    }


    public void MakeAppointment(String serviceID) {

        Log.e("hfhdfhshowdhd", "MakeAppointment: "+serviceID );


        Button sendRequestBtn;


        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_event,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        radioGroup = (RadioGroup)dialogView.findViewById(R.id.radio);
        timeOne = dialogView.findViewById(R.id.timeOne);
        timeTwo = dialogView.findViewById(R.id.timeTwo);
        timeThree = dialogView.findViewById(R.id.timeThree);
        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        try{
            WaitDialog.showDialog(EventDetailActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
                public void onCheckedChanged(RadioGroup group, int checkedId) {



                    switch(checkedId){
                        case R.id.timeOne:
                            Send_Event_Time_ID = Event_Time_ID1;

                            Log.e("IDTest", "onCheckedChanged: "+ timeOne.getText() );
                            Log.e("IDTest", "onCheckedChanged: "+Event_Time_ID1 );
                            break;
                        case R.id.timeTwo:
                            Send_Event_Time_ID = Event_Time_ID2;
                            Log.e("IDTest", "onCheckedChanged: "+ timeTwo.getText() );
                            Log.e("IDTest", "onCheckedChanged: "+Event_Time_ID2 );
                            break;
                        case R.id.timeThree:
                            Send_Event_Time_ID = Event_Time_ID3;
                            Log.e("IDTest", "onCheckedChanged: "+ timeThree.getText() );
                            Log.e("IDTest", "onCheckedChanged: "+Event_Time_ID3 );
                            break;
                    }



                }
            });




        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EventDetailActivity.this);
                currentUserID = preferences.getString("CurrentUserId", "");

                String firstName = preferences.getString("First_Name", "");
                String lastName = preferences.getString("Last_Name", "");


                Log.e("Namesssssss", "onClick: "+firstName );
                Log.e("Namesssssss", "onClick: "+lastName );

                Log.e("Send_ID", "onCheckedChanged: "+Send_Event_Time_ID );
                Log.e("Send_ID", "onCheckedChanged: "+currentUserID );

                String method = "Event_Reservation";

                BackgroundTask backgroundTask = new BackgroundTask(EventDetailActivity.this);
                backgroundTask.execute(method,Send_Event_Time_ID,currentUserID);

                Intent intent = new Intent(EventDetailActivity.this,EventCrossCompActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Send_Event_Time_ID",Send_Event_Time_ID);
                intent.putExtra("eventName",event_name.getText());
                intent.putExtra("eventAddress",event_address.getText());
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


                String data = URLEncoder.encode("eventID","UTF-8") + "=" + URLEncoder.encode(Event_ID,"UTF-8");
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


                String EVENT_ID,Day,Start_Time,End_Time;
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