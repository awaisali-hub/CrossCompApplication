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
import android.widget.TextView;
import android.widget.TimePicker;

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

    RadioButton timeOne,timeTwo,timeThree;
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

        new BackgroundTasksLoadData().execute();
        MakeAppointment(Event_ID);

    }


    public void MakeAppointment(String serviceID) {

        Log.e("hfhdfhshowdhd", "MakeAppointment: "+serviceID );


        Button sendRequestBtn;


        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_event,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        timeOne = dialogView.findViewById(R.id.timeOne);
        timeTwo = dialogView.findViewById(R.id.timeTwo);
        timeThree = dialogView.findViewById(R.id.timeThree);
        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailActivity.this,EventCrossCompActivity.class);
                intent.putExtra("CurrentUserID","1");
                intent.putExtra("coordinatorID","1");
                intent.putExtra("coordinatorName","1");
                intent.putExtra("serviceID","1");
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


                String E_ID,EVENT_ID,Day,Start_Time,End_Time;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    E_ID = JO.getString("E_ID");
                    EVENT_ID = JO.getString("Event_ID");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");




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
                    }else if(count == 1){
                        timeTwo.setText(text);
                    }else if(count == 2){
                        timeThree.setText(text);
                    }


                    count++;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}