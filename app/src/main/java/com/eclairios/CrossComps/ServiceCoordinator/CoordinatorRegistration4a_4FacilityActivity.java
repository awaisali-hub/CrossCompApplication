package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

public class CoordinatorRegistration4a_4FacilityActivity extends AppCompatActivity {

    TextView sundayCheck,mondayCheck,tuesdayCheck,wednesdayCheck,thursdayCheck,fridayCheck,saturdayCheck;
    EditText sundayStartAm,sundayEndAm,sundayStartPm,sundayEndPm,
            mondayStartAm,mondayEndAm,mondayStartPm,mondayEndPm,
            tuesdayStartAm,tuesdayEndAm,tuesdayStartPm,tuesdayEndPm,
            wednesdayStartAm,wednesdayEndAm,wednesdayStartPm,wednesdayEndPm,
            thursdayStartAm,thursdayEndAm,thursdayStartPm,thursdayEndPm,
            fridayStartAm,fridayEndAm,fridayStartPm,fridayEndPm,
            saturdayStartAm,saturdayEndAm,saturdayStartPm,saturdayEndPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration4a_4_facility);
        getSupportActionBar().hide();

        sundayCheck = findViewById(R.id.sundayCheck);
        mondayCheck = findViewById(R.id.mondayCheck);
        tuesdayCheck = findViewById(R.id.tuesdayCheck);
        wednesdayCheck = findViewById(R.id.wednesdayCheck);
        thursdayCheck = findViewById(R.id.thursdayCheck);
        fridayCheck = findViewById(R.id.fridayCheck);
        saturdayCheck = findViewById(R.id.saturdayCheck);

        sundayStartAm = findViewById(R.id.sundayStartAm);
        sundayEndAm = findViewById(R.id.sundayEndAM);
        sundayStartPm = findViewById(R.id.sundayStartPm);
        sundayEndPm = findViewById(R.id.sundayEndPm);

        mondayStartAm = findViewById(R.id.mondayStartAm);
        mondayEndAm = findViewById(R.id.mondayEndAm);
        mondayStartPm = findViewById(R.id.mondayStartPm);
        mondayEndPm = findViewById(R.id.mondayEndPm);

        tuesdayStartAm = findViewById(R.id.tuesdayStartAm);
        tuesdayEndAm = findViewById(R.id.tuesdayEndAm);
        tuesdayStartPm = findViewById(R.id.tuesdayStartPm);
        tuesdayEndPm = findViewById(R.id.tuedasyEndPm);

        wednesdayStartAm = findViewById(R.id.wednesdayStartAm);
        wednesdayEndAm = findViewById(R.id.wednesdayEndAm);
        wednesdayStartPm = findViewById(R.id.wednesdayStartPm);
        wednesdayEndPm = findViewById(R.id.wednesdayEndPm);

        thursdayStartAm = findViewById(R.id.thursdayStartAm);
        thursdayEndAm = findViewById(R.id.thursdayEndAm);
        thursdayStartPm = findViewById(R.id.thursdayStartPm);
        thursdayEndPm = findViewById(R.id.thursdayEndPm);

        fridayStartAm = findViewById(R.id.fridayStartAm);
        fridayEndAm = findViewById(R.id.fridayEndAm);
        fridayStartPm = findViewById(R.id.fridayStartPm);
        fridayEndPm = findViewById(R.id.fridayEndPm);

        saturdayStartAm = findViewById(R.id.saturdayStartAm);
        saturdayEndAm = findViewById(R.id.saturdayEndAm);
        saturdayStartPm = findViewById(R.id.saturdayStartPm);
        saturdayEndPm = findViewById(R.id.saturdayEndPm);



        sundayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(sundayStartAm.getText().toString()) && !TextUtils.isEmpty(sundayEndAm.getText().toString())){
                    sundayCheck.setText("X");
                }else{
                    sundayCheck.setText("");
                }
            }
        });
        sundayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(sundayStartAm.getText().toString()) && !TextUtils.isEmpty(sundayEndAm.getText().toString())){
                    sundayCheck.setText("X");
                }else{
                    sundayCheck.setText("");
                }
            }
        });

        sundayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(sundayStartPm.getText().toString()) && !TextUtils.isEmpty(sundayEndPm.getText().toString())){
                    sundayCheck.setText("X");
                }else{
                    sundayCheck.setText("");
                }
            }
        });
        sundayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(sundayStartPm.getText().toString()) && !TextUtils.isEmpty(sundayEndPm.getText().toString())){
                    sundayCheck.setText("X");
                }else{
                    sundayCheck.setText("");
                }
            }
        });

        mondayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(mondayStartAm.getText().toString()) && !TextUtils.isEmpty(mondayEndAm.getText().toString())){
                    mondayCheck.setText("X");
                }else{
                    mondayCheck.setText("");
                }
            }
        });
        mondayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(mondayStartAm.getText().toString()) && !TextUtils.isEmpty(mondayEndAm.getText().toString())){
                    mondayCheck.setText("X");
                }else{
                    mondayCheck.setText("");
                }
            }
        });

        mondayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(mondayStartPm.getText().toString()) && !TextUtils.isEmpty(mondayEndPm.getText().toString())){
                    mondayCheck.setText("X");
                }else{
                    mondayCheck.setText("");
                }
            }
        });
        mondayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(mondayStartAm.getText().toString()) && !TextUtils.isEmpty(mondayEndAm.getText().toString())){
                    mondayCheck.setText("X");
                }else{
                    mondayCheck.setText("");
                }
            }
        });

        tuesdayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndAm.getText().toString())){
                    tuesdayCheck.setText("X");
                }else{
                    tuesdayCheck.setText("");
                }
            }
        });
        tuesdayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndAm.getText().toString())){
                    tuesdayCheck.setText("X");
                }else{
                    tuesdayCheck.setText("");
                }
            }
        });

        tuesdayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndPm.getText().toString())){
                    tuesdayCheck.setText("X");
                }else{
                    tuesdayCheck.setText("");
                }
            }
        });
        tuesdayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndPm.getText().toString())){
                    tuesdayCheck.setText("X");
                }else{
                    tuesdayCheck.setText("");
                }
            }
        });

        wednesdayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndAm.getText().toString())){
                    wednesdayCheck.setText("X");
                }else{
                    wednesdayCheck.setText("");
                }
            }
        });
        wednesdayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndAm.getText().toString())){
                    wednesdayCheck.setText("X");
                }else{
                    wednesdayCheck.setText("");
                }
            }
        });

        wednesdayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndPm.getText().toString())){
                    wednesdayCheck.setText("X");
                }else{
                    wednesdayCheck.setText("");
                }
            }
        });
        wednesdayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndPm.getText().toString())){
                    wednesdayCheck.setText("X");
                }else{
                    wednesdayCheck.setText("");
                }
            }
        });

        thursdayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(thursdayStartAm.getText().toString()) && !TextUtils.isEmpty(thursdayEndAm.getText().toString())){
                    thursdayCheck.setText("X");
                }else{
                    thursdayCheck.setText("");
                }
            }
        });
        thursdayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(thursdayStartAm.getText().toString()) && !TextUtils.isEmpty(thursdayEndAm.getText().toString())){
                    thursdayCheck.setText("X");
                }else{
                    thursdayCheck.setText("");
                }
            }
        });

        thursdayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(thursdayStartPm.getText().toString()) && !TextUtils.isEmpty(thursdayEndPm.getText().toString())){
                    thursdayCheck.setText("X");
                }else{
                    thursdayCheck.setText("");
                }
            }
        });
        thursdayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(thursdayStartPm.getText().toString()) && !TextUtils.isEmpty(thursdayEndPm.getText().toString())){
                    thursdayCheck.setText("X");
                }else{
                    thursdayCheck.setText("");
                }
            }
        });

        fridayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(fridayStartAm.getText().toString()) && !TextUtils.isEmpty(fridayEndAm.getText().toString())){
                    fridayCheck.setText("X");
                }else{
                    fridayCheck.setText("");
                }
            }
        });
        fridayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(fridayStartAm.getText().toString()) && !TextUtils.isEmpty(fridayEndAm.getText().toString())){
                    fridayCheck.setText("X");
                }else{
                    fridayCheck.setText("");
                }
            }
        });

        fridayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(fridayStartPm.getText().toString()) && !TextUtils.isEmpty(fridayEndPm.getText().toString())){
                    fridayCheck.setText("X");
                }else{
                    fridayCheck.setText("");
                }
            }
        });
        fridayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(fridayStartPm.getText().toString()) && !TextUtils.isEmpty(fridayEndPm.getText().toString())){
                    fridayCheck.setText("X");
                }else{
                    fridayCheck.setText("");
                }
            }
        });

        saturdayStartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(saturdayStartAm.getText().toString()) && !TextUtils.isEmpty(saturdayEndAm.getText().toString())){
                    saturdayCheck.setText("X");
                }else{
                    saturdayCheck.setText("");
                }
            }
        });
        saturdayEndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(saturdayStartAm.getText().toString()) && !TextUtils.isEmpty(saturdayEndAm.getText().toString())){
                    saturdayCheck.setText("X");
                }else{
                    saturdayCheck.setText("");
                }
            }
        });

        saturdayStartPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(saturdayStartPm.getText().toString()) && !TextUtils.isEmpty(saturdayEndPm.getText().toString())){
                    saturdayCheck.setText("X");
                }else{
                    saturdayCheck.setText("");
                }
            }
        });
        saturdayEndPm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!TextUtils.isEmpty(saturdayStartPm.getText().toString()) && !TextUtils.isEmpty(saturdayEndPm.getText().toString())){
                    saturdayCheck.setText("X");
                }else{
                    saturdayCheck.setText("");
                }
            }
        });




    }

    public void Registration4b4(View view) {

        if(TextUtils.isEmpty(sundayStartAm.getText().toString()) && TextUtils.isEmpty(sundayEndAm.getText().toString()) && TextUtils.isEmpty(sundayStartPm.getText().toString()) && TextUtils.isEmpty(sundayEndPm.getText().toString())
                && TextUtils.isEmpty(mondayStartAm.getText().toString()) && TextUtils.isEmpty(mondayEndAm.getText().toString()) && TextUtils.isEmpty(mondayStartPm.getText().toString()) && TextUtils.isEmpty(mondayEndPm.getText().toString())
                && TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && TextUtils.isEmpty(tuesdayEndAm.getText().toString()) && TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && TextUtils.isEmpty(tuesdayEndPm.getText().toString())
                && TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && TextUtils.isEmpty(wednesdayEndAm.getText().toString()) && TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && TextUtils.isEmpty(wednesdayEndPm.getText().toString())
                && TextUtils.isEmpty(thursdayStartAm.getText().toString()) && TextUtils.isEmpty(thursdayEndAm.getText().toString()) && TextUtils.isEmpty(thursdayStartPm.getText().toString()) && TextUtils.isEmpty(thursdayEndPm.getText().toString())
                && TextUtils.isEmpty(fridayStartAm.getText().toString()) && TextUtils.isEmpty(fridayEndAm.getText().toString()) && TextUtils.isEmpty(fridayStartPm.getText().toString()) && TextUtils.isEmpty(fridayEndPm.getText().toString())
                && TextUtils.isEmpty(saturdayStartAm.getText().toString()) && TextUtils.isEmpty(saturdayEndAm.getText().toString()) && TextUtils.isEmpty(saturdayStartPm.getText().toString()) && TextUtils.isEmpty(saturdayEndPm.getText().toString())){

            sundayCheck.setText("");
            mondayCheck.setText("");
            tuesdayCheck.setText("");
            wednesdayCheck.setText("");
            thursdayCheck.setText("");
            fridayCheck.setText("");
            saturdayCheck.setText("");
            Toast.makeText(this, "Please Select Your Service Day With Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(sundayStartAm.getText().toString()) && TextUtils.isEmpty(sundayEndAm.getText().toString()) || TextUtils.isEmpty(sundayStartAm.getText().toString()) && !TextUtils.isEmpty(sundayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select Valid Sunday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(sundayStartPm.getText().toString()) && TextUtils.isEmpty(sundayEndPm.getText().toString()) || TextUtils.isEmpty(sundayStartPm.getText().toString()) && !TextUtils.isEmpty(sundayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Sunday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(mondayStartAm.getText().toString()) && TextUtils.isEmpty(mondayEndAm.getText().toString()) || TextUtils.isEmpty(mondayStartAm.getText().toString()) && !TextUtils.isEmpty(mondayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Monday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(mondayStartPm.getText().toString()) && TextUtils.isEmpty(mondayEndPm.getText().toString()) || TextUtils.isEmpty(mondayStartPm.getText().toString()) && !TextUtils.isEmpty(mondayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Monday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && TextUtils.isEmpty(tuesdayEndAm.getText().toString()) || TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Tuesday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && TextUtils.isEmpty(tuesdayEndPm.getText().toString()) || TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Tuesday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && TextUtils.isEmpty(wednesdayEndAm.getText().toString()) || TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Wednesday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && TextUtils.isEmpty(wednesdayEndPm.getText().toString()) || TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Wednesday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(thursdayStartAm.getText().toString()) && TextUtils.isEmpty(thursdayEndAm.getText().toString()) || TextUtils.isEmpty(thursdayStartAm.getText().toString()) && !TextUtils.isEmpty(thursdayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Thursay Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(thursdayStartPm.getText().toString()) && TextUtils.isEmpty(thursdayEndPm.getText().toString()) || TextUtils.isEmpty(thursdayStartPm.getText().toString()) && !TextUtils.isEmpty(thursdayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Thursday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(fridayStartAm.getText().toString()) && TextUtils.isEmpty(fridayEndAm.getText().toString()) || TextUtils.isEmpty(fridayStartAm.getText().toString()) && !TextUtils.isEmpty(fridayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Friday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(fridayStartPm.getText().toString()) && TextUtils.isEmpty(fridayEndPm.getText().toString()) || TextUtils.isEmpty(fridayStartPm.getText().toString()) && !TextUtils.isEmpty(fridayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Friday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(saturdayStartAm.getText().toString()) && TextUtils.isEmpty(saturdayEndAm.getText().toString()) || TextUtils.isEmpty(saturdayStartAm.getText().toString()) && !TextUtils.isEmpty(saturdayEndAm.getText().toString())){

            Toast.makeText(this, "Please Select valid Saturday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(saturdayStartPm.getText().toString()) && TextUtils.isEmpty(saturdayEndPm.getText().toString()) || TextUtils.isEmpty(saturdayStartPm.getText().toString()) && !TextUtils.isEmpty(saturdayEndPm.getText().toString())){

            Toast.makeText(this, "Please Select valid Saturday Start and Stop Time", Toast.LENGTH_SHORT).show();
        }

//        startActivity(new Intent(CoordinatorRegistration4a_4FacilityActivity.this,CoordinatorRegistration4b_4EventActivity.class));
    }
}