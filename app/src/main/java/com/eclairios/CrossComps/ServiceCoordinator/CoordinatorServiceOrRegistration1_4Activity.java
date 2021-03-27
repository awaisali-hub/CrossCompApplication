package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eclairios.CrossComps.R;

public class CoordinatorServiceOrRegistration1_4Activity extends AppCompatActivity {


    Button service,event,submit;


    String value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_service_or_registration1_4);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("serviceOrEvent",null);
        editor.apply();

        service = findViewById(R.id.facilityBtn);
        event = findViewById(R.id.eventBtn);
        submit = findViewById(R.id.submitBTN);
        submit.setClickable(false);

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(value==null){
                    service.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    submit.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    value = "service";
                    submit.setClickable(true);
                }else if(value.equals("service")){
                    service.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
                    submit.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
                    value = null;
                    submit.setClickable(false);
                }

            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(value==null){
                    event.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    submit.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    value = "event";
                    submit.setClickable(true);
                }else if(value.equals("event")){
                    event.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
                    submit.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));
                    value = null;
                    submit.setClickable(false);
                }
            }
        });
    }

    public void ServiceRegistration2a_4(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        if(value.equals("service")){
            editor.putString("serviceOrEvent","service");
            editor.apply();
            startActivity(new Intent(CoordinatorServiceOrRegistration1_4Activity.this,CoordinatorServiceOrEventRegistration2a_4Activity.class));
        }else if(value.equals("event")){
            editor.putString("serviceOrEvent","event");
            editor.apply();
            startActivity(new Intent(CoordinatorServiceOrRegistration1_4Activity.this,CoordinatorRegistration2b_4EventActivity.class));
        }


    }
}