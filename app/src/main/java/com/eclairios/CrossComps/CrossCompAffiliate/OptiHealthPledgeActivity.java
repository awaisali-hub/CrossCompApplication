package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.Authentication.Registration;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OptiHealthPledgeActivity extends AppCompatActivity {

    EditText firstName,lastName,ET_date;
    final Calendar myCalendar = Calendar.getInstance();
    Button submitAffiliate;

    CheckBox three,four,five,six,seven,eight,nine,ten,eleven,t13,
            t14,t15,t16,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,
            t28,t29,t30,t31,t32,t33,t34;

    String lat,lng;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opti_health_pledge);
        getSupportActionBar().hide();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        ET_date = findViewById(R.id.date);
        submitAffiliate = findViewById(R.id.submitAffiliate);

        three = (CheckBox) findViewById(R.id.three);
        four = (CheckBox) findViewById(R.id.four);
        five = (CheckBox) findViewById(R.id.five);
        six = (CheckBox) findViewById(R.id.six);
        seven = (CheckBox) findViewById(R.id.seven);
        eight = (CheckBox) findViewById(R.id.eight);
        nine = (CheckBox) findViewById(R.id.nine);
        ten = (CheckBox) findViewById(R.id.ten);
        eleven = (CheckBox) findViewById(R.id.eleven);

        t13 = (CheckBox) findViewById(R.id.t13);
        t14 = (CheckBox) findViewById(R.id.t14);
        t15 = (CheckBox) findViewById(R.id.t15);
        t16 = (CheckBox) findViewById(R.id.t16);


        t18 = (CheckBox) findViewById(R.id.t18);
        t19 = (CheckBox) findViewById(R.id.t19);
        t20 = (CheckBox) findViewById(R.id.t20);
        t21 = (CheckBox) findViewById(R.id.t21);
        t22 = (CheckBox) findViewById(R.id.t22);
        t23 = (CheckBox) findViewById(R.id.t23);
        t24 = (CheckBox) findViewById(R.id.t24);
        t25 = (CheckBox) findViewById(R.id.t25);
        t26 = (CheckBox) findViewById(R.id.t26);
        t27 = (CheckBox) findViewById(R.id.t27);
        t28 = (CheckBox) findViewById(R.id.t28);
        t29 = (CheckBox) findViewById(R.id.t29);
        t30 = (CheckBox) findViewById(R.id.t30);
        t31 = (CheckBox) findViewById(R.id.t31);
        t32 = (CheckBox) findViewById(R.id.t32);
        t33 = (CheckBox) findViewById(R.id.t33);
        t34 = (CheckBox) findViewById(R.id.t34);


        submitAffiliate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!three.isChecked() && !four.isChecked() && !five.isChecked() && !six.isChecked() && !seven.isChecked() && !eight.isChecked() && !nine.isChecked() && !ten.isChecked() && !eleven.isChecked()){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Please Select One Item from first Nine", Toast.LENGTH_SHORT).show();
                }else if(!t13.isChecked() || !t14.isChecked() || !t15.isChecked() || !t16.isChecked()){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Please Accept Agreement", Toast.LENGTH_SHORT).show();
                }else if(!t18.isChecked() && !t19.isChecked() && !t20.isChecked() && !t21.isChecked() && !t22.isChecked() && !t23.isChecked() && !t24.isChecked() &&
                        !t25.isChecked() && !t26.isChecked() && !t27.isChecked() && !t28.isChecked() && !t29.isChecked() && !t30.isChecked() && !t31.isChecked() &&
                        !t32.isChecked() && !t33.isChecked() && !t34.isChecked()){

                    Toast.makeText(OptiHealthPledgeActivity.this, "Some Information required!!!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(firstName.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "First Name required!!!", Toast.LENGTH_SHORT).show();
                }else if( TextUtils.isEmpty(lastName.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Last Name required!!!", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(ET_date.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Date required!!!", Toast.LENGTH_SHORT).show();
                }else{

                    String method = "AffiliatesRegister";
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OptiHealthPledgeActivity.this);
                    String currentUserID = preferences.getString("CurrentUserId", "");
                    lat = preferences.getString("lat", null);
                    lng = preferences.getString("lng", null);

                    String AffiliateType = "Coordinator";

                    BackgroundTask backgroundTask = new BackgroundTask(OptiHealthPledgeActivity.this);
                    backgroundTask.execute(method, currentUserID, firstName.getText().toString(), lastName.getText().toString(), ET_date.getText().toString(), lat, lng, AffiliateType,"0","0");

              //      MoveToAffiliateDashboard(v);
                }
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };


        ET_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (ET_date.getRight() - ET_date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        new DatePickerDialog(OptiHealthPledgeActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        return true;
                    }
                }
                return false;
            }
        });
    }

//    public void MoveToAffiliateDashboard(View view) {
//        startActivity(new Intent(OptiHealthPledgeActivity.this,AffiliateDashboardActivity.class));
//    }

    public void MoveToMainHomePage(View view) {
        startActivity(new Intent(OptiHealthPledgeActivity.this, Dashboard.class));
    }



    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ET_date.setText(sdf.format(myCalendar.getTime()));
    }

}