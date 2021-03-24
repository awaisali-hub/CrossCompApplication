package com.eclairios.CrossComps.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Registration extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private EditText firstName, lastName, phone, email, password;
    private ProgressDialog progressDialog;
    private String lat, lng, MainAddress;
    private Button signUpButton, signInBtn;
    private int keyDel = 0;
    private CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        signInBtn = findViewById(R.id.signInBtn);
        rememberMe = findViewById(R.id.rememberMe);


        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = phone.getText().length();
                    if(len == 3) {
                        phone.setText(phone.getText() + "-");
                        phone.setSelection(phone.getText().length());
                    }else if(len == 8){
                        phone.setText(phone.getText() + "-");
                        phone.setSelection(phone.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });


        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION_PERMISSION);
                return;
            }
        }
        getCurrentLocation();



//        signInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Registration.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//            }
//        });


//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog = new ProgressDialog(Registration.this);
//                progressDialog.setMessage("Please wait...");
//                progressDialog.show();
//
//                registration();
//            }
//        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String rememberEmail = preferences.getString("Email","");
        String rememberPassword = preferences.getString("Password","");

        if(rememberEmail != null && rememberPassword!=null){
            email.setText(rememberEmail);
            password.setText(rememberPassword);

        }

    }


    private void registration() {

        String strFirstName = firstName.getText().toString();
        String strLastName = lastName.getText().toString();
        String strPhone = phone.getText().toString();
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();

        String method = "register";

        if (TextUtils.isEmpty(strFirstName) || TextUtils.isEmpty(strLastName) || TextUtils.isEmpty(strPhone) ||
                TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword)) {
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "All fields are required!", Toast.LENGTH_SHORT).show();

        } else if (strPassword.length() < 6) {
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
        } else {


            SharedPreferences Preferences = PreferenceManager.getDefaultSharedPreferences(Registration.this);
            SharedPreferences.Editor Editor = Preferences.edit();
            if(rememberMe.isChecked()){
                Editor.putString("Email",strEmail);
                Editor.putString("Password",strPassword);
                Editor.putString("Email123",strEmail);
                Editor.putString("Password123",strPassword);

            }else{
                Editor.putString("Email","");
                Editor.putString("Password","");
            }
            Editor.apply();

            BackgroundTask backgroundTask = new BackgroundTask(Registration.this);
            backgroundTask.execute(method, strFirstName, strLastName, strPhone, strEmail, strPassword, lat, lng, MainAddress);

        }

    }

    private void getCurrentLocation() {
        Log.e("simpleTest", "getCurrentLocation: " );

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Registration.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Registration.this)
                                .removeLocationUpdates(this);


                        Log.e("simpleTest", "getCurrentLocation: "+locationResult);

                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;

                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            lat = String.valueOf(latitude);
                            lng = String.valueOf(longitude);

                            Log.e("latlngssssss", "onLocationResult: "+lat+"\n"+lng );




                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(Registration.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL



                                MainAddress = city;

                                Log.e("addressssssss", "onLocationResult: "+ state);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                Looper.getMainLooper());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Permission Denied!" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void MoveToSignInPage(View view) {
        Intent intent = new Intent(Registration.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Registration(View view) {
        progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        registration();
    }
}

