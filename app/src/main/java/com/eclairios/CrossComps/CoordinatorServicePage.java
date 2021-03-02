package com.eclairios.CrossComps;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eclairios.CrossComps.Adapter.AdapterCoordinaterServicePage;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Interface.MyInterface;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CoordinatorServicePage extends AppCompatActivity implements MyInterface {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView coordinator_name,coordinator_address;
    String CoordinatorID,CoordinatorName,CoordinatorAddress;
    String facilityID,Service_ID;

    RecyclerView recyclerCoordinaterService;
    AdapterCoordinaterServicePage adapterCoordinaterServicePage;
    ArrayList<ModelCoordinaterServicePage> listCoordinareService = new ArrayList<>() ;

    Date date;
    Calendar calendar;
    String CurrentDay;

    RadioButton date1,date2,date3;
    RadioGroup radioGroup;
    String serviceDATE;
    String formatServiceDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_page);

        getSupportActionBar().hide();

        try{
            WaitDialog.showDialog(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // gridView and linear layout



        recyclerCoordinaterService = findViewById(R.id.recycleCoordinator);
        adapterCoordinaterServicePage = new AdapterCoordinaterServicePage( listCoordinareService,CoordinatorServicePage.this,this);
        recyclerCoordinaterService.setAdapter(adapterCoordinaterServicePage);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            CoordinatorID = bundle.getString("coordinatorID");
            CoordinatorName = bundle.getString("coordinatorName");
            CoordinatorAddress = bundle.getString("coordinatorAddress");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("coordinatorName",CoordinatorName);
            editor.putString("coordinatorAddress",CoordinatorAddress);
            editor.apply();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
            CoordinatorID = preferences.getString("coordinatorId", "");
            CoordinatorName = preferences.getString("coordinatorName", "");
            CoordinatorAddress = preferences.getString("coordinatorAddress", "");
        }


        coordinator_name = findViewById(R.id.service_facility_name);
        coordinator_address = findViewById(R.id.service_facility_address);

        coordinator_name.setText(CoordinatorName);
        coordinator_address.setText(CoordinatorAddress);

        listCoordinareService.clear();
        new BackgroundTasksLoadData().execute();


        calendar = Calendar.getInstance();
        date = calendar.getTime();
        CurrentDay = new SimpleDateFormat("EEEE MM-dd-yyyy", Locale.ENGLISH).format(date.getTime());
        Log.e("loggggsgsgsg", "onCreate: "+CurrentDay );



    }



    public void MakeAppointment(String serviceDay,String Facility_ID) {

        Button sendRequestBtn;
        TextView showReservationDay;


        AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorServicePage.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_reservation_facility,null);
        builder.setCancelable(true);
        builder.setView(dialogView);


        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);
        showReservationDay = dialogView.findViewById(R.id.ShowReservationDay);
        radioGroup = (RadioGroup)dialogView.findViewById(R.id.date_radio_group);
        date1 = dialogView.findViewById(R.id.date1);
        date2 = dialogView.findViewById(R.id.date2);
        date3 = dialogView.findViewById(R.id.date3);


        String currentString = serviceDay;
        String[] separated = currentString.split(",");

        NextThreeDates(separated[0]);

        showReservationDay.setText("Which "+separated[0]+"?");


        AlertDialog pickFileImage = builder.create();

        pickFileImage.show();



        pickFileImage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                calendar = Calendar.getInstance();
                date = calendar.getTime();
                CurrentDay = new SimpleDateFormat("EEEE MM-dd-yyyy", Locale.ENGLISH).format(date.getTime());
                Log.e("loggggsgsgsg", "onCreate: "+CurrentDay );

            }
        });


            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {



                    switch(checkedId){
                        case R.id.date1:
                            String Date1 = date1.getText().toString();
                            serviceDATE = Date1;
                            Log.e("IDTest", "onCheckedChanged: "+ date1.getText() );
                            Log.e("facilityIDs", "MakeAppointment: "+serviceDATE);
                            break;
                        case R.id.date2:
                            String Date2 = date2.getText().toString();
                            serviceDATE = Date2;
                            Log.e("IDTest", "onCheckedChanged: "+ date2.getText() );
                            Log.e("facilityIDs", "MakeAppointment: "+serviceDATE);

                            break;
                        case R.id.date3:
                            String Date3 = date3.getText().toString();
                            serviceDATE = Date3;
                            Log.e("IDTest", "onCheckedChanged: "+ date3.getText() );
                            Log.e("facilityIDs", "MakeAppointment: "+serviceDATE);
                            break;
                    }



                }
            });


        if(date1.isChecked()) {

            String Date1 = date1.getText().toString();
            serviceDATE = Date1;

            Log.e("IDTest", "onCheckedChanged: " + date1.getText());
            Log.e("facilityIDs", "MakeAppointment: "+serviceDATE);

        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
        String currentUserID = preferences.getString("CurrentUserId", "");
        Log.e("facilityIDs", "MakeAppointment: "+Facility_ID);
        Log.e("facilityIDs", "MakeAppointment: "+currentUserID);


        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String method = "Service_Reservation";


                String strCurrentDate = serviceDATE;

                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                Date newDate = null;
                try {
                    newDate = format.parse(strCurrentDate);
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    formatServiceDate = format.format(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                BackgroundTask backgroundTask = new BackgroundTask(CoordinatorServicePage.this);
                backgroundTask.execute(method,Facility_ID,formatServiceDate,currentUserID);

                Intent intent = new Intent(CoordinatorServicePage.this,CrossComp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                coordinator_name.setText(CoordinatorName);
                coordinator_address.setText(CoordinatorAddress);
                intent.putExtra("serviceName",coordinator_name.getText().toString());
                intent.putExtra("serviceAddress",coordinator_address.getText().toString());
                intent.putExtra("FacilityID",Facility_ID);
                intent.putExtra("formatServiceDate",formatServiceDate);
                startActivity(intent);
            }
        });
    }

    public void NextThreeDates(String serviceDay){

        Date today = calendar.getTime();

        if (CurrentDay.toLowerCase().contains(serviceDay.toLowerCase())) {
            Log.e("nvbmxzczcxzzv", "onCreate:" + CurrentDay);

            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date tomorrow = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("EEEE, MM-dd-yyyy");

            String todayAsString = dateFormat.format(today);
            String tomorrowAsString = dateFormat.format(tomorrow);

            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date tomorrow1 = calendar.getTime();
            DateFormat dateFormat1 = new SimpleDateFormat("EEEE, MM-dd-yyyy");

            String tomorrowAsString1 = dateFormat1.format(tomorrow1);

            Log.e("datesssss", "NextThreeDates: "+todayAsString );
            Log.e("datesssss", "NextThreeDates: "+tomorrowAsString );
            Log.e("datesssss", "NextThreeDates: "+tomorrowAsString1 );


            String Day1 = todayAsString;
            String[] day1 = Day1.split(",");

            String Day2 = tomorrowAsString;
            String[] day2 = Day2.split(",");

            String Day3 = tomorrowAsString1;
            String[] day3 = Day3.split(",");



            date1.setText(day1[1]);
            date2.setText(day2[1]);
            date3.setText(day3[1]);




        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = calendar.getTime();
            CurrentDay = new SimpleDateFormat("EEEE, MM-dd-yyyy", Locale.ENGLISH).format(date.getTime());
            NextThreeDates(serviceDay);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
                String CurrentUserID = preferences.getString("CurrentUserId", "");


                Log.e("dghdgdgdg", "doInBackground: "+CoordinatorID);

                String data = URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(CoordinatorID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_service_provider_facility.php";
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
                    facilityID = JO.getString("Facility_ID");
                    Service_ID = JO.getString("Service_ID");
                    Day = JO.getString("Day");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");

                    Log.e("testtttttt", "onPostExecute: "+facilityID);



                        String _24HourTime = Start_Time;
                        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                        Date _24HourDt = _24HourSDF.parse(_24HourTime);



                    String _24HourTime1 = End_Time;
                    SimpleDateFormat _24HourSDF1 = new SimpleDateFormat("HH:mm");
                    Date _24HourDt1 = _24HourSDF1.parse(_24HourTime1);


                    String newStartTime = _12HourSDF.format(_24HourDt);
                    String newEndTime = _12HourSDF.format(_24HourDt1);


                    ModelCoordinaterServicePage servicePage = new ModelCoordinaterServicePage();
                    servicePage.setService_facility_ID(facilityID);
                    servicePage.setService_facility_name(Day+", "+newStartTime.toLowerCase()+ "-" +newEndTime.toLowerCase());
                    listCoordinareService.add(servicePage);

                    count++;

                }
                adapterCoordinaterServicePage.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onBackPressed() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                Intent intent = new Intent(CoordinatorServicePage.this,Dashboard.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
    }


}