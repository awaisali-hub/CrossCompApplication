package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String rememberEmail = preferences.getString("Email123","");
        String rememberPassword = preferences.getString("Password123","");
        String lat = preferences.getString("lat","");
        String lng = preferences.getString("lng","");


        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(this);
        sharedEditor = sharedPreferences.edit();

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();



        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isItFirestTime()) {
                    Log.e("text", "onCreate: First Time" );
                    startActivity(new Intent(SplashActivity.this,Registration.class));
                } else {
                    Log.e("text", "onCreate: Not a First Time" );
                    if(rememberEmail.isEmpty() && rememberPassword.isEmpty()){
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }else if(rememberEmail != null && rememberPassword !=null && lat != null && lng != null){

                        Intent intent = new Intent(SplashActivity.this,Dashboard.class);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);
                        startActivity(intent);

                        //   startActivity(new Intent(SplashActivity.this,Dashboard.class));
                    }
                }



                finish();
            }
        },2000);
    }
    public boolean isItFirestTime() {
        if (sharedPreferences.getBoolean("firstTime", true)) {
            sharedEditor.putBoolean("firstTime", false);
            sharedEditor.commit();
            sharedEditor.apply();
            return true;
        } else {
            return false;
        }
    }
}