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
    JSONArray jsonArray;

      TextView day,date,time,coordinatorName,coordinatorAddress,coordinatorNotes,appointmentRequest,notesHeader;
      String serviceID,currentUserID,CoordinatorID,CoordinatorName;

    String selectDay;
    TextView selectDateDay;
    String selectDate;
    TextView selectTime;
    ProgressDialog progressDialog;
    String  CurrentUserID;
    FloatingActionButton floatingActionButton;
    CardView cardView1,cardView2;

    String str_userScoreRemarks;

    CircleImageView homeCircleImage;


    Toolbar appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_comp);

        appBarLayout = findViewById(R.id.toolbar);
        appBarLayout.inflateMenu(R.menu.menu);

        coordinatorName = findViewById(R.id.coordinatorNAME);
        coordinatorAddress = findViewById(R.id.coordinatorADDRESS);
        coordinatorNotes = findViewById(R.id.notesText);
        appointmentRequest = findViewById(R.id.appoint_message);
        day = findViewById(R.id.day);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        floatingActionButton = findViewById(R.id.fab);
        notesHeader = findViewById(R.id.notesHeader);
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);

        homeCircleImage = findViewById(R.id.homeCircleImage);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            currentUserID = bundle.getString("CurrentUserID");
            CoordinatorID = bundle.getString("coordinatorID");
            CoordinatorName = bundle.getString("coordinatorName");
            serviceID = bundle.getString("serviceID");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("coordinatorName",CoordinatorName);
            editor.apply();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossComp.this);
            CoordinatorID = preferences.getString("coordinatorId", "");
            CoordinatorName = preferences.getString("coordinatorName", "");
            currentUserID = preferences.getString("currentUserID", "");
            serviceID = preferences.getString("serviceID","");
        }


        coordinatorName.setText("Gym");

        Log.e("fhfhfhf", "onCreate: "+currentUserID );
        Log.e("fhfhfhf", "onCreate: "+CoordinatorID );
        Log.e("serviceIDDDDDDD", "onCreate: "+serviceID );


        String  address1= bundle.getString("coordinatorAddress");
        String  appointmentDay= bundle.getString("day");
        String  appointmentDate= bundle.getString("date");
        String  times= bundle.getString("time");
        String  notes= bundle.getString("coordinatorNotes");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//            Log.e("DateCheck", "onCreate: "+formatter.format(parser.parse( appointmentDate)) );
//            date.setText(formatter.format(parser.parse( appointmentDate)));
            date.setText("02-18-2021");

        }

        coordinatorAddress.setText("London, 11223");
        day.setText("Thursday");
        time.setText("11:00 am - 2:00 pm");
        coordinatorNotes.setText(R.string.notes_usert_string);
        notesHeader.setText("Instructions");

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

        selectDateDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("updateDate","");
                editor.putString("updateTime","");
                editor.apply();
            }
        });
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("updateDate","");
                editor.putString("updateTime","");
                editor.apply();
            }
        });

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(CrossComp.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String method = "updateAppointment";
                String currentTime = selectTime.getText().toString();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CrossComp.this);
                CurrentUserID = preferences.getString("CurrentUserId", "");


                Log.e("dhdhdh", "onClick: "+selectDay );
                Log.e("dhdhdh", "onClick: "+selectDate );
                Log.e("dhdhddh", "onClick: "+selectTime.getText().toString() );
                Log.e("jhfjhfhfh", "MakeAppointment: "+CurrentUserID );
                Log.e("jhfjhfhfh", "MakeAppointment: "+CoordinatorID );

                String time = currentTime;

                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

                SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

                try {
                    currentTime = date24Format.format(date12Format.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if(selectDay != null && selectDate != null && !TextUtils.isEmpty(currentTime)){
                    BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                    backgroundTask.execute(method,selectDay,selectDate,currentTime,CurrentUserID,CoordinatorID,serviceID);
                }else{

                    Toast.makeText(CrossComp.this, "Please Select Appointment Date and Time", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        });




    }

    public void Home(View view) {
        new BackgroundTasksForLoad().execute();
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
                String dateText = android.text.format.DateFormat.format("EEEE, yyyy-MM-dd", calendar1).toString();

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
        boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(this);

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

                Bundle bundle = getIntent().getExtras();
                String serviceID = bundle.getString("serviceID");

                Log.e("jhgg", "doInBackground: "+currentUserID );

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_participants_complete_one_crossComps.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("bcjknjkksdjhwlloc ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");

                if(jsonArray.length() == 0){
                    Toast.makeText(CrossComp.this, "You need to complete your appointment", Toast.LENGTH_SHORT).show();
                }



                int count = 0;

                String firstName;
                String lastName;
                String age;
                String gender;
                String str_userName;
                String str_userAgeGender;
                String str_userAddress;
                String str_userScore;

                String str_userScoreDate;
                String str_scoreExpireDate;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    firstName = JO.getString("First_Name");
                    lastName = JO.getString("Last_Name");
                    str_userName = firstName +" "+lastName;

                    gender = JO.getString("Gender");
                    age = JO.getString("Age");
                    str_userAgeGender = gender + " - "+age;


                    str_userAddress =JO.getString("Address");
                    str_userScore = JO.getString("Score");

                    float score = Float.parseFloat(str_userScore);

                    if(score < 19){
                        str_userScoreRemarks = "Critical";

                    }else if(score > 20 && score <39){
                        str_userScoreRemarks = "Below Average";

                    }else if(score > 40 && score <59){
                        str_userScoreRemarks = "Average";

                    }else if(score > 60 && score <79){
                        str_userScoreRemarks = "Above Average";

                    }else if(score > 80 && score <99){
                        str_userScoreRemarks = "Excellent";

                    }else if(score > 100){
                        str_userScoreRemarks = "Champion";

                    }

                    str_userScoreDate = JO.getString("Date");

                    String date = str_userScoreDate;
                    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate = spf.parse(date);
                    spf = new SimpleDateFormat("MMM yyyy");
                    String newDateString = spf.format(newDate);



                    String dateInString = str_userScoreDate;  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(dateInString));
                    c.add(Calendar.DATE, 90);
                    sdf = new SimpleDateFormat("MMM/yyyy");
                    Date resultDate = new Date(c.getTimeInMillis());
                    dateInString = sdf.format(resultDate);
                    str_scoreExpireDate = dateInString;


                    if(jsonArray.length() != 0){

//
//                        Intent intent = new Intent(CrossComp.this,Participent.class);
//                        intent.putExtra("userName",str_userName);
//                        intent.putExtra("userAgeGender",str_userAgeGender);
//                        intent.putExtra("userAddress",str_userAddress);
//                        intent.putExtra("userScore",str_userScore);
//                        intent.putExtra("userScoreRemarks",str_userScoreRemarks);
//                        intent.putExtra("userScoreDate",newDateString);
//                        intent.putExtra("scoreExpireDate",str_scoreExpireDate);
//                        intent.putExtra("serviceID",serviceID);
//                        startActivity(intent);


                        Intent intent = new Intent(CrossComp.this,Participent.class);
                        intent.putExtra("userName","Gym");
                        intent.putExtra("userAgeGender","26");
                        intent.putExtra("userAddress","london");
                        intent.putExtra("userScore","180");
                        intent.putExtra("userScoreRemarks","Excelent");
                        intent.putExtra("userScoreDate","21");
                        intent.putExtra("scoreExpireDate","45");
                        intent.putExtra("serviceID","1");
                        startActivity(intent);
                    }
                    count++;

                }

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CrossComp.this,Dashboard.class));
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
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}