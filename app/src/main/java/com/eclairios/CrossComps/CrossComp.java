package com.eclairios.CrossComps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrossComp extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1,jsonArray2;


    TextView serviceName,serviceAddress,currentUserName,serviceDay,serviceDate,serviceTime,serviceInstruction;
    TextView selectDateDay;
    TextView selectTime;
    FloatingActionButton floatingActionButton;
    Toolbar appBarLayout;

    String serviceNameStr,serviceAddressStr,FacilityIDStr,formatServiceDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_comp);

        try{
            WaitDialog.showDialog(CrossComp.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appBarLayout = findViewById(R.id.toolbar);
        appBarLayout.inflateMenu(R.menu.menu);

        serviceName = findViewById(R.id.serviceNAME);
        serviceAddress = findViewById(R.id.serviceADDRESS);
        currentUserName = findViewById(R.id.CurrentUserNAME);
        serviceDay = findViewById(R.id.service_day);
        serviceDate = findViewById(R.id.service_date);
        serviceTime = findViewById(R.id.service_time);
        serviceInstruction = findViewById(R.id.instructionText);


        floatingActionButton = findViewById(R.id.fab);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossComp.this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            serviceNameStr = bundle.getString("serviceName");
            serviceAddressStr = bundle.getString("serviceAddress");
            FacilityIDStr = bundle.getString("FacilityID");
            formatServiceDate = bundle.getString("formatServiceDate");


            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("serviceName",serviceNameStr);
            editor.putString("serviceAddress",serviceAddressStr);
            editor.putString("FacilityID",FacilityIDStr);
            editor.putString("formatServiceDate",formatServiceDate);
            editor.apply();
        }else{

            serviceNameStr = preferences.getString("serviceName", "");
            serviceAddressStr = preferences.getString("serviceAddress", "");
            FacilityIDStr = preferences.getString("FacilityID", "");
            formatServiceDate = preferences.getString("formatServiceDate","");
        }

        serviceName.setText(serviceNameStr);
        serviceAddress.setText(serviceAddressStr);

        String firstName = preferences.getString("First_Name", "");
        String lastName = preferences.getString("Last_Name", "");
        currentUserName.setText(firstName + " "+lastName);

        new BackgroundTasksForLoad().execute();

    }

    public void RescheduleAppointment(View view) {
        ImageButton selectDateDayBtn,selectTimeBtn;
        Button sendRequestBtn;
        TextView makeAppointment;

        AlertDialog.Builder builder = new AlertDialog.Builder(CrossComp.this);
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

        makeAppointment.setText("Reschedule Appointment");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossComp.this);
        String updateDay = preferences.getString("updateDate", "");
        String updateTime = preferences.getString("updateTime", "");
        selectDateDay.setText(updateDay);
        selectTime.setText(updateTime);


        AlertDialog pickFileImage = builder.create();
        pickFileImage.show();


    }

    public void Home(View view) {
        startActivity(new Intent(CrossComp.this,Participent.class));
    }


    class BackgroundTasksForLoad extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossComp.this);
                String currentUserID = preferences.getString("CurrentUserId", "");


                Log.e("jhgg", "doInBackground: "+currentUserID );

                String data = URLEncoder.encode("facility_ID","UTF-8") + "=" + URLEncoder.encode(FacilityIDStr,"UTF-8") + "&"+
                        URLEncoder.encode("reservationDate","UTF-8") + "=" + URLEncoder.encode(formatServiceDate,"UTF-8") + "&"+
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
            json_url = "http://edevz.com/cross_comp/get_service_reservation_details.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("testservice", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                jsonArray1 = jsonObject.getJSONArray("facility_details");
                jsonArray2 = jsonObject.getJSONArray("service_details");




                int count = 0;
                String Reservation_Date;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    Reservation_Date = JO.getString("Reservation_Date");


                    String deliveryDate=Reservation_Date;
                    SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = dateFormatprev.parse(deliveryDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String serviceDATE = dateFormat.format(d);

                    serviceDate.setText(serviceDATE);

                    count++;
                }

                int count1 = 0;
                String Day,Start_Time,End_Time;
                while(count1 < jsonArray1.length())
                {
                    JSONObject JO = jsonArray1.getJSONObject(count1);
                    Day = JO.getString("Day");
                    Start_Time = JO.getString("Start_Time");
                    End_Time = JO.getString("End_Time");

                    serviceDay.setText(Day);


                    final String start_time = Start_Time;
                    final String end_time = End_Time;

                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(start_time);
                    final Date dateObj1 = sdf.parse(end_time);

                    serviceTime.setText(new SimpleDateFormat("K:mm a").format(dateObj).toLowerCase() +"-"+new SimpleDateFormat("K:mm a").format(dateObj1).toLowerCase());


                    count1++;
                }

                int count2 = 0;
                String Instructions;
                while(count2 < jsonArray2.length())
                {
                    JSONObject JO = jsonArray2.getJSONObject(count2);
                    Instructions = JO.getString("Instructions");
                    serviceInstruction.setText(Instructions);

                    count2++;
                }



            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }


            try{
                WaitDialog.hideDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}