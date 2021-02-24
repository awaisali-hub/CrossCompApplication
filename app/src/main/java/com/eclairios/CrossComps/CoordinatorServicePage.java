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
import android.os.Build;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CoordinatorServicePage extends AppCompatActivity implements MyInterface {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    TextView coordinator_name,coordinator_address;

    String CoordinatorID,CoordinatorName,CoordinatorAddress;

    TextView selectDateDay;
    TextView selectTime;
    String ServiceID;

    RecyclerView recyclerCoordinaterService;
    AdapterCoordinaterServicePage adapterCoordinaterServicePage;
    ArrayList<ModelCoordinaterServicePage> listCoordinareService = new ArrayList<>() ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_page);

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


        //////////////////////////////////////////////////////////////////////////////////////////////
//        android.icu.text.SimpleDateFormat dateFormat = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dateFormat = new android.icu.text.SimpleDateFormat("MMMM dd, yyyy");
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        Date today = calendar.getTime();
//
//        calendar.add(Calendar.DAY_OF_YEAR, 7);
//        Date tomorrow = calendar.getTime();
//
//        String todayAsString = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            todayAsString = dateFormat.format(today);
//        }
//        String tomorrowAsString = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tomorrowAsString = dateFormat.format(tomorrow);
//        }
//
//   //     Log.e("datesChecking", "onCreate: "+todayAsString );
//   //     Log.e("datesChecking", "onCreate: "+tomorrowAsString );
//
//
//        calendar.add(Calendar.DAY_OF_YEAR, 7);
//        Date tomorrow1 = calendar.getTime();
//
//        String tomorrowAsString1 = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tomorrowAsString1 = dateFormat.format(tomorrow1);
//        }
//
//
//    //    Log.e("datesChecking", "onCreate: "+tomorrowAsString1 );
//
//
//        calendar.add(Calendar.DAY_OF_YEAR, 7);
//        Date tomorrow2 = calendar.getTime();
//
//        String tomorrowAsString2 = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tomorrowAsString2 = dateFormat.format(tomorrow2);
//        }
//
//
//   //     Log.e("datesChecking", "onCreate: "+tomorrowAsString2 );




        /////////////////////////////////////////////////////////////////////////////////////////


        NextThreeDates();








        //////////////////////////////////////////////////////////////////////////////////////////
    }

    public void NextThreeDates(){

        String userDay = "tuesday";

        android.icu.text.SimpleDateFormat dateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormat = new android.icu.text.SimpleDateFormat("MMMM dd, yyyy");
        }

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date tomorrow = calendar.getTime();

        String todayAsString = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            todayAsString = dateFormat.format(today);
        }
        String tomorrowAsString = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tomorrowAsString = dateFormat.format(tomorrow);
        }

        //     Log.e("datesChecking", "onCreate: "+todayAsString );
        //     Log.e("datesChecking", "onCreate: "+tomorrowAsString );


        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date tomorrow1 = calendar.getTime();

        String tomorrowAsString1 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tomorrowAsString1 = dateFormat.format(tomorrow1);
        }


        //    Log.e("datesChecking", "onCreate: "+tomorrowAsString1 );


        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date tomorrow2 = calendar.getTime();

        String tomorrowAsString2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tomorrowAsString2 = dateFormat.format(tomorrow2);
        }


        //     Log.e("datesChecking", "onCreate: "+tomorrowAsString2 );







        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("dayssssssssss", "onCreate: "+LocalDate.now().getDayOfWeek().name().toLowerCase());

            String finalDay;

            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

            Log.e("currentDay", "NextThreeDates: "+thisDate);

            String input_date = thisDate;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt1= null;
            try {
                dt1 = format1.parse(input_date);
                SimpleDateFormat format2=new SimpleDateFormat("EEEE");
                finalDay=format2.format(dt1);
                Log.e("dayssssss", "NextThreeDates: "+finalDay );

                if(finalDay.equals(userDay) ){
                    Log.e("tuessssdsdsdasdasdsa", "onCreate: "+userDay);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        dateFormat = new android.icu.text.SimpleDateFormat("MMMM dd, yyyy");
                        calendar.add(Calendar.DAY_OF_YEAR, 7);
                        todayAsString = dateFormat.format(today);
                        tomorrowAsString = dateFormat.format(tomorrow);
                        Log.e("datesChecking", "onCreate: "+todayAsString );
                        Log.e("datesChecking", "onCreate: "+tomorrowAsString );

                        calendar.add(Calendar.DAY_OF_YEAR, 7);
                        tomorrowAsString1 = dateFormat.format(tomorrow1);
                        Log.e("datesChecking", "onCreate: "+tomorrowAsString1 );
                    }

                }else{

                 //   NextThreeDates();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }



        }
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
//                handleDateButton();
            }
        });
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handleTimeButton();
            }
        });

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CoordinatorServicePage.this,ShowDateForReservationActivity.class));
            }
        });



    }


    public void ShowAppointment(String serviceID) {

        ServiceID = serviceID;

        Log.e("fhfhfhdjsk", "ShowAppointment: "+ServiceID );


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

                String facilityID,Service_ID,Day,Start_Time,End_Time;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    facilityID = JO.getString("Facility_ID");
                    Service_ID = JO.getString("Service_ID");
                    Day = JO.getString("Day");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");

                    Log.e("testtttttt", "onPostExecute: "+facilityID+Service_ID+Day+ Start_Time+End_Time);



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