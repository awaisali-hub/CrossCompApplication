package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class EventDetailActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
    }

    public void EventActivityDetails(View view) {
      //  startActivity(new Intent(EventDetailActivity.this,EventCrossCompActivity.class));
        MakeAppointment("1");
    }


    public void MakeAppointment(String serviceID) {

        Log.e("hfhdfhshowdhd", "MakeAppointment: "+serviceID );

        ImageButton selectDateDayBtn,selectTimeBtn;
        Button sendRequestBtn;
        TextView makeAppointment;

        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_for_event,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        selectDateDay = dialogView.findViewById(R.id.datePicker);
        selectTime = dialogView.findViewById(R.id.timePicker);

        selectDateDayBtn = dialogView.findViewById(R.id.selectDate);
        selectTimeBtn = dialogView.findViewById(R.id.selectTime);
        sendRequestBtn = dialogView.findViewById(R.id.sendRequest);
        makeAppointment = dialogView.findViewById(R.id.ShowAppointment);

     //   makeAppointment.setText("Make Reservation");


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

//        selectDateDayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleDateButton();
//            }
//        });
//        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleTimeButton();
//            }
//        });
//
//        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(EventDetailActivity.this,ShowTimeRangeForEventActivity.class));
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
//
//
//            }
//        });



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

}