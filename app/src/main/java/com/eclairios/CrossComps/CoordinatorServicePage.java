package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterCoordinaterServicePage;
import com.eclairios.CrossComps.Adapter.AdapterWeekDays;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Interface.MyInterface;
import com.eclairios.CrossComps.Model.ModelCoordinaterServicePage;
import com.eclairios.CrossComps.Model.ModelWeekDays;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CoordinatorServicePage extends AppCompatActivity implements MyInterface {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView coordinator_name;

    String CoordinatorID,CoordinatorName;

    String selectDay;
    TextView selectDateDay;
    String selectDate;
    TextView selectTime;
    ProgressDialog progressDialog;
    String  CurrentUserID;
    String ServiceID;

    RecyclerView recyclerCoordinaterService;
    AdapterCoordinaterServicePage adapterCoordinaterServicePage;
    ArrayList<ModelCoordinaterServicePage> listCoordinareService = new ArrayList<>() ;
    ModelCoordinaterServicePage modelCoordinaterServicePage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_page);

        new WaitDialog(this).hide();

        // gridView and linear layout

        recyclerCoordinaterService = findViewById(R.id.recycleCoordinater);
        adapterCoordinaterServicePage = new AdapterCoordinaterServicePage( listCoordinareService,CoordinatorServicePage.this,this);
        recyclerCoordinaterService.setAdapter(adapterCoordinaterServicePage);





        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            CoordinatorID = bundle.getString("coordinatorID");
            CoordinatorName = bundle.getString("coordinatorName");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("coordinatorName",CoordinatorName);
            editor.apply();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
            CoordinatorID = preferences.getString("coordinatorId", "");
            CoordinatorName = preferences.getString("coordinatorName", "");
        }


        Log.e("gddhdhdh", "onCreate: "+CoordinatorID);
        Log.e("gddhdhdh", "onCreate: "+CoordinatorName);


        coordinator_name = findViewById(R.id.CoordinatorName);
        coordinator_name.setText(CoordinatorName);


    }



    public void MakeAppointment(String serviceID) {

        Log.e("hfhdfhshowdhd", "MakeAppointment: "+serviceID );

        ImageButton selectDateDayBtn,selectTimeBtn;
        Button sendRequestBtn;
        TextView makeAppointment;

        AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorServicePage.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_appointment,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        selectDateDay = dialogView.findViewById(R.id.datePicker);
        selectTime = dialogView.findViewById(R.id.timePicker);

        selectDateDayBtn = dialogView.findViewById(R.id.selectDate);
        selectTimeBtn = dialogView.findViewById(R.id.selectTime);
        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);
        makeAppointment = dialogView.findViewById(R.id.ShowAppointment);

        makeAppointment.setText("Make Reservation");


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        selectDateDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CoordinatorServicePage.this,ShowDateForReservationActivity.class));
//                progressDialog = new ProgressDialog(CoordinatorServicePage.this);
//                progressDialog.setMessage("Please wait...");
//                progressDialog.show();
//
//                String method = "makeAppointment";
//                String currentTime = selectTime.getText().toString();
//
//
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
//                CurrentUserID = preferences.getString("CurrentUserId", "");
//
//
//                Log.e("dhdhdh", "onClick: "+selectDay );
//                Log.e("dhdhdh", "onClick: "+selectDate );
//                Log.e("dhdhddh", "onClick: "+selectTime.getText().toString() );
//                Log.e("jhfjhfhfh", "MakeAppointment: "+CurrentUserID );
//                Log.e("jhfjhfhfh", "MakeAppointment: "+CoordinatorID );
//
//                String time = currentTime;
//
//                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
//
//                SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
//
//                try {
//                    currentTime = date24Format.format(date12Format.parse(time));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                if(selectDay != null && selectDate != null && !TextUtils.isEmpty(currentTime)){
//                    BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
//                    backgroundTask.execute(method,selectDay,selectDate,currentTime,CurrentUserID,CoordinatorID,serviceID);
//
//                    progressDialog.dismiss();
//                    pickFileImage.dismiss();
//                }else{
//
//                    Toast.makeText(CoordinatorServicePage.this, "Please Select Appointment Date and Time", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }


            }
        });



    }


    public void ShowAppointment(String serviceID) {

        ServiceID = serviceID;

        Log.e("fhfhfhdjsk", "ShowAppointment: "+ServiceID );
        new BackgroundTasksLoadData().execute();

    }

    public void SelectNextThreeDays(View view) {


        ImageButton selectDateDayBtn, selectTimeBtn;
        Button sendRequestBtn;
        TextView makeAppointment;

        AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorServicePage.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_reservation_facility, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

//            selectDateDay = dialogView.findViewById(R.id.datePicker);
//            selectTime = dialogView.findViewById(R.id.timePicker);
//
//            selectDateDayBtn = dialogView.findViewById(R.id.selectDate);
//            selectTimeBtn = dialogView.findViewById(R.id.selectTime);
            sendRequestBtn = dialogView.findViewById(R.id.sendRequest);
//            makeAppointment = dialogView.findViewById(R.id.ShowAppointment);

//            makeAppointment.setText("Make Reservation");


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoordinatorServicePage.this,CrossComp.class);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorServicePage.this);
                String CurrentUserID = preferences.getString("CurrentUserId", "");


                Log.e("dghdgdgdg", "doInBackground: "+CoordinatorID);
                Log.e("dghdgdgdg", "doInBackground: "+ServiceID);

                String data = URLEncoder.encode("Coordinator_ID","UTF-8") + "=" + URLEncoder.encode(CoordinatorID,"UTF-8") + "&"+
                        URLEncoder.encode("CurrentUser_ID","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(ServiceID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_appointment_1st.php";
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

                if(jsonArray.length() == 0){
                    Toast.makeText(CoordinatorServicePage.this, "Your Request is not accepted Yet!!!", Toast.LENGTH_SHORT).show();
                }

                int count = 0;

                String serviceID,coordinatorId,currentUserID,address,postalCode,appointmentDay,appointmentDate,appointmentTime,notes;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    serviceID = JO.getString("Coordinator_Service_ID");
                    coordinatorId = JO.getString("Coordinator_ID");
                    currentUserID = JO.getString("User_ID");
                    address = JO.getString("Address");
                    postalCode = JO.getString("Postal_Code");
                    appointmentDay = JO.getString("Appointment_Day");
                    appointmentDate = JO.getString("Appointment_Date");
                    appointmentTime = JO.getString("Appointment_Time");
                    notes = JO.getString("Note");

                    Log.e("jdjdjdjd", "onPostExecute: "+address );
                    Log.e("jdjdjdjd", "onPostExecute: "+postalCode );
                    Log.e("jdjdjdjd", "onPostExecute: "+appointmentDay );
                    Log.e("jdjdjdjd", "onPostExecute: "+appointmentDate );
                    Log.e("jdjdjdjd", "onPostExecute: "+appointmentTime );
                    Log.e("jdjdjdjd", "onPostExecute: "+notes );

                    String appointmentTimes = (appointmentTime.substring(0, appointmentTime.length() - 10));
                    String address1 = address + ", " + postalCode;

                    String _24HourTime = appointmentTime;
                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                    Date _24HourDt = null;

                    _24HourDt = _24HourSDF.parse(_24HourTime);
                    Log.e("dgdgddgh", "onPostExecute: "+_24HourDt );
                    Log.e("dgdgddgh", "onPostExecute: "+_12HourSDF.format(_24HourDt) );

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("coordinatorId",coordinatorId);
                    editor.putString("currentUserID",currentUserID);
                    String updateDayDate = appointmentDay +","+appointmentDate;
                    editor.putString("updateDate",updateDayDate);
                    editor.putString("updateTime",_12HourSDF.format(_24HourDt));
                    editor.putString("serviceID",serviceID);
                    editor.apply();

                    if(jsonArray.length() != 0){

                        Intent intent = new Intent(CoordinatorServicePage.this,CrossComp.class);
                        intent.putExtra("coordinatorAddress",address1);
                        intent.putExtra("day",appointmentDay);
                        intent.putExtra("date",appointmentDate);
                        intent.putExtra("time",_12HourSDF.format(_24HourDt));
                        intent.putExtra("coordinatorNotes",notes);
                        intent.putExtra("notesHeader","Notes");
                        intent.putExtra("coordinatorName",CoordinatorName);
                        intent.putExtra("coordinatorID",CoordinatorID);
                        intent.putExtra("CurrentUserID",CurrentUserID);
                        intent.putExtra("serviceID",serviceID);

                        Log.e("servcIDddd", "onPostExecute: "+ serviceID);

                        startActivity(intent);

                    }

//                    coordinatorAddress.setText(address1);
//                    day.setText(appointmentDay);
//                    date.setText(appointmentDate);
//                    time.setText(_12HourSDF.format(_24HourDt));
//                    coordinatorNotes.setText(notes);
//                    notesHeader.setText("Notes");

                    count++;

                }

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }
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

                String data = URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(CoordinatorID,"UTF-8") ;
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
            json_url = "http://edevz.com/cross_comp/get_coordinator_services.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("serviceData ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");


                int count = 0;

                String coordinatorId,serviceName,timingFrom,timingTo,weekDays;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    ServiceID = JO.getString("Service_ID");
                    coordinatorId = JO.getString("Coordinator_ID");
                    serviceName = JO.getString("Name");
                    timingFrom = JO.getString("Timing_From");
                    timingTo = JO.getString("Timing_To");
                    weekDays = JO.getString("Week_Days");


                    Log.e("dataset", "onPostExecute: "+weekDays );


//                    String[] weekDayList = weekDays.split(",");
//                    for(int i =0 ; i<weekDayList.length; i++){
//                        Log.e("splitName", "onPostExecute: "+weekDayList[i]);
//                      ModelWeekDays servicePage = new ModelWeekDays();
//                      servicePage.setMonday(String.valueOf(i));
//                        weekDaysList.add(servicePage);
//                    }
//                    adapterWeekDays = new AdapterWeekDays( weekDaysList,CoordinatorServicePage.this);
//                    recyclerViewWeekDays.setAdapter(adapterWeekDays);

                    String _24HourTime = timingFrom;
                    String _24HourTime1 = timingTo;

                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

                    Date _24HourDt = null;
                    Date _24HourDt1 = null;

                    _24HourDt = _24HourSDF.parse(_24HourTime);
                    _24HourDt1 = _24HourSDF.parse(_24HourTime1);


                    Log.e("dgdgddgh", "onPostExecute: "+_24HourDt );
                    Log.e("dgdgddgh", "onPostExecute: "+_12HourSDF.format(_24HourDt) );


//                    service_name.setText(serviceName);
//                    timing_from.setText(_12HourSDF.format(_24HourDt));
//                    timing_to.setText(_12HourSDF.format(_24HourDt1));
//                    week_days.setText(weekDays);

                    ModelCoordinaterServicePage servicePage = new ModelCoordinaterServicePage();
                    servicePage.setCoordinatorServiceID(ServiceID);
                    servicePage.setCoordinaterName(CoordinatorName);
                    servicePage.setCoordinateService(serviceName);
                    servicePage.setStartTime(_12HourSDF.format(_24HourDt));
                    servicePage.setEndTime(_12HourSDF.format(_24HourDt1));
                    servicePage.setWeekDays(weekDays);



                    listCoordinareService.add(servicePage);

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("coordinatorId",coordinatorId);
                    editor.apply();
                    count++;

                }
                adapterCoordinaterServicePage.notifyDataSetChanged();

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        listCoordinareService.clear();
        new BackgroundTasks().execute();
    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, yyyy-MM-dd", calendar1).toString();

                Log.e("jkhhhg", "onDateSet: "+dateText);
                String[] result = dateText.split(",");
                selectDay = result[0];
                selectDate =result[1];

                selectDateDay.setText(dateText);


            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("HH:mm a", calendar1).toString();
                Log.e("hhhhh", "onTimeSet: "+dateText );
//                selectTime.setText(dateText);


                String AM_PM = " AM";
                String mm_precede = "";
                if ( hour >= 12) {
                    AM_PM = " PM";
                    if (hour >=13 && hour < 24) {
                        hour -= 12;
                    }
                    else {
                        hour = 12;
                    }
                } else if (hour == 0) {
                    hour = 12;
                }
                if (minute < 10) {
                    mm_precede = "0";
                }
                Log.e("dhdhhghhghhhjhgghhdhd", "onTimeSet: "+ hour + ":" + mm_precede + minute + AM_PM);
                selectTime.setText(""+hour + ":" + mm_precede + minute + AM_PM);


            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

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