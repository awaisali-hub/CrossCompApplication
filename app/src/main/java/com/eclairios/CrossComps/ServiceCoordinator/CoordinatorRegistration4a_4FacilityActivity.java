package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.Model.NewServiceModel;
import com.eclairios.CrossComps.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CoordinatorRegistration4a_4FacilityActivity extends AppCompatActivity {
    String response;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String currentUserID,serviceName,lat,lng,street,city,state,postalCode,Address,type;


    JSONArray array = new JSONArray();

    TextView sundayCheck,mondayCheck,tuesdayCheck,wednesdayCheck,thursdayCheck,fridayCheck,saturdayCheck;
    EditText sundayStartAm,sundayEndAm,sundayStartPm,sundayEndPm,
            mondayStartAm,mondayEndAm,mondayStartPm,mondayEndPm,
            tuesdayStartAm,tuesdayEndAm,tuesdayStartPm,tuesdayEndPm,
            wednesdayStartAm,wednesdayEndAm,wednesdayStartPm,wednesdayEndPm,
            thursdayStartAm,thursdayEndAm,thursdayStartPm,thursdayEndPm,
            fridayStartAm,fridayEndAm,fridayStartPm,fridayEndPm,
            saturdayStartAm,saturdayEndAm,saturdayStartPm,saturdayEndPm;

    private int keyDel = 0;
    ArrayList<NewServiceModel> newServiceModels = new ArrayList<>();

    Message msg = new Message();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration4a_4_facility);
        getSupportActionBar().hide();

        newServiceModels.clear();

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
                sundayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = sundayStartAm.getText().length();
                    if(len == 2) {
                        sundayStartAm.setText(sundayStartAm.getText() + ":");
                        sundayStartAm.setSelection(sundayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

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
                sundayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = sundayEndAm.getText().length();
                    if(len == 2) {
                        sundayEndAm.setText(sundayEndAm.getText() + ":");
                        sundayEndAm.setSelection(sundayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                sundayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = sundayStartPm.getText().length();
                    if(len == 2) {
                        sundayStartPm.setText(sundayStartPm.getText() + ":");
                        sundayStartPm.setSelection(sundayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                sundayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = sundayEndPm.getText().length();
                    if(len == 2) {
                        sundayEndPm.setText(sundayEndPm.getText() + ":");
                        sundayEndPm.setSelection(sundayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                mondayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mondayStartAm.getText().length();
                    if(len == 2) {
                        mondayStartAm.setText(mondayStartAm.getText() + ":");
                        mondayStartAm.setSelection(mondayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                mondayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mondayEndAm.getText().length();
                    if(len == 2) {
                        mondayEndAm.setText(mondayEndAm.getText() + ":");
                        mondayEndAm.setSelection(mondayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                mondayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mondayStartPm.getText().length();
                    if(len == 2) {
                        mondayStartPm.setText(mondayStartPm.getText() + ":");
                        mondayStartPm.setSelection(mondayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                mondayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mondayEndPm.getText().length();
                    if(len == 2) {
                        mondayEndPm.setText(mondayEndPm.getText() + ":");
                        mondayEndPm.setSelection(mondayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                tuesdayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = tuesdayStartAm.getText().length();
                    if(len == 2) {
                        tuesdayStartAm.setText(tuesdayStartAm.getText() + ":");
                        tuesdayStartAm.setSelection(tuesdayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                tuesdayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = tuesdayEndAm.getText().length();
                    if(len == 2) {
                        tuesdayEndAm.setText(tuesdayEndAm.getText() + ":");
                        tuesdayEndAm.setSelection(tuesdayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                tuesdayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = tuesdayStartPm.getText().length();
                    if(len == 2) {
                        tuesdayStartPm.setText(tuesdayStartPm.getText() + ":");
                        tuesdayStartPm.setSelection(tuesdayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                tuesdayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = tuesdayEndPm.getText().length();
                    if(len == 2) {
                        tuesdayEndPm.setText(tuesdayEndPm.getText() + ":");
                        tuesdayEndPm.setSelection(tuesdayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                wednesdayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = wednesdayStartAm.getText().length();
                    if(len == 2) {
                        wednesdayStartAm.setText(wednesdayStartAm.getText() + ":");
                        wednesdayStartAm.setSelection(wednesdayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                wednesdayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = wednesdayEndAm.getText().length();
                    if(len == 2) {
                        wednesdayEndAm.setText(wednesdayEndAm.getText() + ":");
                        wednesdayEndAm.setSelection(wednesdayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                wednesdayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = wednesdayStartPm.getText().length();
                    if(len == 2) {
                        wednesdayStartPm.setText(wednesdayStartPm.getText() + ":");
                        wednesdayStartPm.setSelection(wednesdayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                wednesdayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = wednesdayEndPm.getText().length();
                    if(len == 2) {
                        wednesdayEndPm.setText(wednesdayEndPm.getText() + ":");
                        wednesdayEndPm.setSelection(wednesdayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                thursdayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = thursdayStartAm.getText().length();
                    if(len == 2) {
                        thursdayStartAm.setText(thursdayStartAm.getText() + ":");
                        thursdayStartAm.setSelection(thursdayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                thursdayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = thursdayEndAm.getText().length();
                    if(len == 2) {
                        thursdayEndAm.setText(thursdayEndAm.getText() + ":");
                        thursdayEndAm.setSelection(thursdayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                thursdayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = thursdayStartPm.getText().length();
                    if(len == 2) {
                        thursdayStartPm.setText(thursdayStartPm.getText() + ":");
                        thursdayStartPm.setSelection(thursdayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                thursdayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = thursdayEndPm.getText().length();
                    if(len == 2) {
                        thursdayEndPm.setText(thursdayEndPm.getText() + ":");
                        thursdayEndPm.setSelection(thursdayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                fridayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = fridayStartAm.getText().length();
                    if(len == 2) {
                        fridayStartAm.setText(fridayStartAm.getText() + ":");
                        fridayStartAm.setSelection(fridayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                fridayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = fridayEndAm.getText().length();
                    if(len == 2) {
                        fridayEndAm.setText(fridayEndAm.getText() + ":");
                        fridayEndAm.setSelection(fridayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                fridayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = fridayStartPm.getText().length();
                    if(len == 2) {
                        fridayStartPm.setText(fridayStartPm.getText() + ":");
                        fridayStartPm.setSelection(fridayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                fridayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = fridayEndPm.getText().length();
                    if(len == 2) {
                        fridayEndPm.setText(fridayEndPm.getText() + ":");
                        fridayEndPm.setSelection(fridayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                saturdayStartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = saturdayStartAm.getText().length();
                    if(len == 2) {
                        saturdayStartAm.setText(saturdayStartAm.getText() + ":");
                        saturdayStartAm.setSelection(saturdayStartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                saturdayEndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = saturdayEndAm.getText().length();
                    if(len == 2) {
                        saturdayEndAm.setText(saturdayEndAm.getText() + ":");
                        saturdayEndAm.setSelection(saturdayEndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                saturdayStartPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = saturdayStartPm.getText().length();
                    if(len == 2) {
                        saturdayStartPm.setText(saturdayStartPm.getText() + ":");
                        saturdayStartPm.setSelection(saturdayStartPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
                saturdayEndPm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = saturdayEndPm.getText().length();
                    if(len == 2) {
                        saturdayEndPm.setText(saturdayEndPm.getText() + ":");
                        saturdayEndPm.setSelection(saturdayEndPm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
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
        }else{

            int mondayAm = 0;
            int mondayPm = 0;
            int tuesdayAm = 0;
            int tuesdayPm = 0;
            int wednesdayAm = 0;
            int wednesdayPm = 0;
            int thursdayAm = 0;
            int thursdayPm = 0;
            int fridayAm = 0;
            int fridayPm = 0;
            int saturdayAm = 0;
            int saturdayPm = 0;
            int sundayAm = 0;
            int sundayPm = 0;



            for (int i = 0; i<14; i++){
                if(i == 0){
                    if(sundayAm == 0){
                        if(!TextUtils.isEmpty(sundayStartAm.getText().toString()) && !TextUtils.isEmpty(sundayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Sunday");
                            model.setStartTime(sundayStartAm.getText().toString());
                            model.setEndTime(sundayEndAm.getText().toString());
                            newServiceModels.add(model);
                            sundayAm++;
                        }
                    }

                }else if (i == 1){

                    if(sundayPm == 0){
                        if(!TextUtils.isEmpty(sundayStartPm.getText().toString()) && !TextUtils.isEmpty(sundayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Sunday");
                            model.setStartTime(sundayStartPm.getText().toString());
                            model.setEndTime(sundayEndPm.getText().toString());
                            newServiceModels.add(model);
                            sundayPm++;
                        }
                    }
                }else if (i == 2){

                    if(mondayAm == 0){
                        if(!TextUtils.isEmpty(mondayStartAm.getText().toString()) && !TextUtils.isEmpty(mondayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Monday");
                            model.setStartTime(mondayStartAm.getText().toString());
                            model.setEndTime(mondayEndAm.getText().toString());
                            newServiceModels.add(model);
                            mondayAm++;
                        }
                    }
                }else if (i == 3){

                    if(mondayPm == 0){
                        if(!TextUtils.isEmpty(mondayStartPm.getText().toString()) && !TextUtils.isEmpty(mondayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Monday");
                            model.setStartTime(mondayStartPm.getText().toString());
                            model.setEndTime(mondayEndPm.getText().toString());
                            newServiceModels.add(model);
                            mondayPm++;
                        }
                    }
                }else if (i == 4){

                    if(tuesdayAm == 0){
                        if(!TextUtils.isEmpty(tuesdayStartAm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Tuesday");
                            model.setStartTime(tuesdayStartAm.getText().toString());
                            model.setEndTime(tuesdayEndAm.getText().toString());
                            newServiceModels.add(model);
                            tuesdayAm++;
                        }
                    }
                }else if (i == 5){

                    if(tuesdayPm == 0){
                        if(!TextUtils.isEmpty(tuesdayStartPm.getText().toString()) && !TextUtils.isEmpty(tuesdayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Tuesday");
                            model.setStartTime(tuesdayStartPm.getText().toString());
                            model.setEndTime(tuesdayEndPm.getText().toString());
                            newServiceModels.add(model);
                            tuesdayPm++;
                        }
                    }

                }else if (i == 6){

                    if(wednesdayAm == 0){
                        if(!TextUtils.isEmpty(wednesdayStartAm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Wednesday");
                            model.setStartTime(wednesdayStartAm.getText().toString());
                            model.setEndTime(wednesdayEndAm.getText().toString());
                            newServiceModels.add(model);
                            wednesdayAm++;
                        }
                    }

                }else if (i == 7){

                    if(wednesdayPm == 0){
                        if(!TextUtils.isEmpty(wednesdayStartPm.getText().toString()) && !TextUtils.isEmpty(wednesdayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Wednesday");
                            model.setStartTime(wednesdayStartPm.getText().toString());
                            model.setEndTime(wednesdayEndPm.getText().toString());
                            newServiceModels.add(model);
                            wednesdayPm++;
                        }
                    }

                }else if (i == 8){

                    if(thursdayAm == 0){
                        if(!TextUtils.isEmpty(thursdayStartAm.getText().toString()) && !TextUtils.isEmpty(thursdayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Thursday");
                            model.setStartTime(thursdayStartAm.getText().toString());
                            model.setEndTime(thursdayEndAm.getText().toString());
                            newServiceModels.add(model);
                            thursdayAm++;
                        }
                    }

                }else if (i == 9){

                    if(thursdayPm == 0){
                        if(!TextUtils.isEmpty(thursdayStartPm.getText().toString()) && !TextUtils.isEmpty(thursdayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Thursday");
                            model.setStartTime(thursdayStartPm.getText().toString());
                            model.setEndTime(thursdayEndPm.getText().toString());
                            newServiceModels.add(model);
                            thursdayPm++;
                        }
                    }

                }else if (i == 10){

                    if(fridayAm == 0){
                        if(!TextUtils.isEmpty(fridayStartAm.getText().toString()) && !TextUtils.isEmpty(fridayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Friday");
                            model.setStartTime(fridayStartAm.getText().toString());
                            model.setEndTime(fridayEndAm.getText().toString());
                            newServiceModels.add(model);
                            fridayAm++;
                        }
                    }

                }else if (i == 11){

                    if(fridayPm == 0){
                        if(!TextUtils.isEmpty(fridayStartPm.getText().toString()) && !TextUtils.isEmpty(fridayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Friday");
                            model.setStartTime(fridayStartPm.getText().toString());
                            model.setEndTime(fridayEndPm.getText().toString());
                            newServiceModels.add(model);
                            fridayPm++;
                        }
                    }

                }else if (i == 12){

                    if(saturdayAm == 0){
                        if(!TextUtils.isEmpty(saturdayStartAm.getText().toString()) && !TextUtils.isEmpty(saturdayEndAm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Saturday");
                            model.setStartTime(saturdayStartAm.getText().toString());
                            model.setEndTime(saturdayEndAm.getText().toString());
                            newServiceModels.add(model);

                            saturdayAm++;
                        }
                    }

                }else if (i == 13){

                    if(saturdayPm == 0){
                        if(!TextUtils.isEmpty(saturdayStartPm.getText().toString()) && !TextUtils.isEmpty(saturdayEndPm.getText().toString())){
                            NewServiceModel model = new NewServiceModel();
                            model.setDay("Saturday");
                            model.setStartTime(saturdayStartPm.getText().toString());
                            model.setEndTime(saturdayEndPm.getText().toString());
                            newServiceModels.add(model);
                            saturdayPm++;
                        }
                    }
                }
            }
        }





        //        startActivity(new Intent(CoordinatorRegistration4a_4FacilityActivity.this,CoordinatorRegistration4b_4EventActivity.class));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoordinatorRegistration4a_4FacilityActivity.this);
        currentUserID = preferences.getString("CurrentUserId", "");
        serviceName = preferences.getString("serviceName", "");
        lat = preferences.getString("lat", "");
        lng = preferences.getString("lng", "");

        street = preferences.getString("streetName", "");
        city = preferences.getString("cityName", "");
        state = preferences.getString("stateName", "");
        postalCode = preferences.getString("postalCode", "");

        Address = street+", "+city+", "+state+", "+postalCode;
        type = "service";


        new BackgroundTaskServiceInsert().execute();



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OutputStream os = null;
//                InputStream is = null;
//                HttpURLConnection conn = null;
//                try {
//                    //constants
//                    URL url = new URL("http://edevz.com/cross_comp/insertAffiliateServices.php");
//
//                    String message = array.toString();
//
//                    Log.e("fddfhjhh", "run: "+message);
//
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setReadTimeout( 10000 /*milliseconds*/ );
//                    conn.setConnectTimeout( 15000 /* milliseconds */ );
//                    conn.setRequestMethod("POST");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//                    conn.setFixedLengthStreamingMode(message.getBytes().length);
//
//                    //make some HTTP header nicety
//                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//
//                    //open
//                    conn.connect();
//
//                    //setup send
//                    os = new BufferedOutputStream(conn.getOutputStream());
//                    os.write(message.getBytes());
//                    //clean up
//                    os.flush();
//
//                    //do somehting with response
//                    is = conn.getInputStream();
//                    //String contentAsString = readIt(is,len);
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
//
//                    String response = "";
//                    String line = "";
//                    while( (line = bufferedReader.readLine()) != null)
//                    {
//                        response += line;
//                    }
//
//                    Log.e("hsadshjd", "run: "+line );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //clean up
//                    try {
//                        os.close();
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    conn.disconnect();
//                }
//            }
//        }).start();
    }



    class BackgroundTaskServiceInsert extends AsyncTask<String, Void, String>
    {
        String json_url;

        @Override
        protected String doInBackground(String... strings) {
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("AffiliateUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") +"&"+
                        URLEncoder.encode("serviceName","UTF-8") + "=" + URLEncoder.encode(serviceName,"UTF-8") +"&"+
                        URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(lat,"UTF-8") +"&"+
                        URLEncoder.encode("lng","UTF-8") + "=" + URLEncoder.encode(lng,"UTF-8") +"&"+
                        URLEncoder.encode("Address","UTF-8") + "=" + URLEncoder.encode(Address,"UTF-8") +"&"+
                        URLEncoder.encode("serviceType","UTF-8") + "=" + URLEncoder.encode(type,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/insertAffiliateServices.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("dfdsfsd", "onPostExecute: "+result );

            if(result != null){

                new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream os = null;
                InputStream is = null;
                HttpURLConnection conn = null;
                try {
                    //constants
                    URL url = new URL("http://edevz.com/cross_comp/insertAffiliateServicesTime.php");

                    for (int a = 0; a<newServiceModels.size(); a++){

                        JSONObject object = new JSONObject();
                        try {
                            object.put("serviceID",result);
                            object.put("day", newServiceModels.get(a).getDay());
                            object.put("start_time" ,newServiceModels.get(a).getStartTime());
                            object.put("end_time" , newServiceModels.get(a).getEndTime());

                            array.put(object);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String message = array.toString();

                    Log.e("fddfhjhh", "run: "+message);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();

                    //do somehting with response
                    is = conn.getInputStream();
                    //String contentAsString = readIt(is,len);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

                    response = "";
                    String line = "";
                    while( (line = bufferedReader.readLine()) != null)
                    {
                        response += line;
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {

                            if(response.contains("Service Time inserted Success")){
                                Toast.makeText(CoordinatorRegistration4a_4FacilityActivity.this, "Service Inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CoordinatorRegistration4a_4FacilityActivity.this,CoordinatorServicesScreenActivity.class));
                                finish();
                            }else if(response.contains("Service Time inserted UnSuccess")){
                                Toast.makeText(CoordinatorRegistration4a_4FacilityActivity.this, "Something went Wrong !!! Please Try Again ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //clean up
                    try {
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                  conn.disconnect();
                }
            }
        }).start();

            }else{
                Toast.makeText(CoordinatorRegistration4a_4FacilityActivity.this, "Something went Wrong !!! Please Try Again ", Toast.LENGTH_SHORT).show();
            }




        }
    }
}