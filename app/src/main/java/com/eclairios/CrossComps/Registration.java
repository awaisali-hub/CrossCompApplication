package com.eclairios.CrossComps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class Registration extends AppCompatActivity{


    private FusedLocationProviderClient fusedLocationProviderClient;

    EditText firstName, lastName, phone, email, password, postalCode, promoterName, address, age;
    Spinner userType;
    Button signUp;
    TextView signInText;
    RadioGroup gender;

    ProgressDialog progressDialog;
    String lat;
    String lng;
    String GENDER;

    Button signUpButton, signInBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    Registration.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    11
            );
        } else {
            getCurrentLocation();
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        postalCode = findViewById(R.id.postalCode);
        promoterName = findViewById(R.id.promoterName);
        userType = (Spinner) findViewById(R.id.userType);
        address = findViewById(R.id.address);
        //  gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);

        signUp = findViewById(R.id.signUpButton);
        signInText = findViewById(R.id.signInText);

        gender = (RadioGroup) findViewById(R.id.radioGender);
        signUpButton = findViewById(R.id.signUpButton);
        signInBtn = findViewById(R.id.signInBtn);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();
                String gender = null;
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);


                    if (btn.getId() == R.id.radioMale) {
                        btn.setText("Male");
                    } else {
                        btn.setText("Female");
                    }
                    if (btn.getId() == checkedId) {

                        gender = btn.getText().toString();// here gender will contain M or F.

                    }

                }
                GENDER = gender;
                Log.e("Gender", gender);
            }
        });

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        userType.setAdapter(adapter);




        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String strAddress = address.getText().toString();


                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(strAddress, getApplicationContext(), new GeoHandler());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registration();
                    }
                }, 1500);
            }
        });




        if (ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            GetLocation();
        } else {
            ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void GetLocation() {

        Log.e("LocationTest", "GetLocation: ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermission();
        }else{

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.e("LocationTest", "GetLocation: "+location);
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(Registration.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    Log.e("LatLNG", "onComplete: "+addresses.get(0).getLatitude());
                                    Log.e("LatLNG", "onComplete: "+addresses.get(0).getLongitude());
                                    Log.e("LatLNG", "onComplete: "+addresses.get(0).getCountryName());
                                    Log.e("LatLNG", "onComplete: "+addresses.get(0).getLocality());
                                    Log.e("LatLNG", "onComplete: "+addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    Location location = task.getResult();
//                    Log.e("LocationTest", "GetLocation: "+location);
//
//                    if(location != null){
//
//                        try {
//                            Geocoder geocoder = new Geocoder(Registration.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                            Log.e("LatLNG", "onComplete: "+addresses.get(0).getLatitude());
//                            Log.e("LatLNG", "onComplete: "+addresses.get(0).getLongitude());
//                            Log.e("LatLNG", "onComplete: "+addresses.get(0).getCountryName());
//                            Log.e("LatLNG", "onComplete: "+addresses.get(0).getLocality());
//                            Log.e("LatLNG", "onComplete: "+addresses.get(0).getAddressLine(0));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
        }



    }

    private void getLatLng() {
    }

    private class GeoHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            String address;
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    address = bundle.getString("address");
                    break;
                default:
                    address = null;
            }

            if(address!=null){
                String[] result = address.split(",");
                lat = result[0];
                lng =result[1];

                Log.e("jjudffdu", "handleMessage: "+address );
                Log.e("jdsdjdj", "handleMessage: "+lat );
                Log.e("ddhdhdh", "handleMessage: "+lng );
            }else{
             //   Toast.makeText(Registration.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void registration() {

              String strFirstName = firstName.getText().toString();
              String strLastName = lastName.getText().toString();
              String strPhone = phone.getText().toString();
              String strEmail = email.getText().toString();
              String strPassword = password.getText().toString();
              String strPostalCode = postalCode.getText().toString();
              String strPromoterName = promoterName.getText().toString();

     //         String strUserType = userType.getSelectedItem().toString();
              String strAddress = address.getText().toString();
              String strGender = GENDER;
              String strAge = age.getText().toString();


              String method = "register";

        if(TextUtils.isEmpty(strFirstName) && TextUtils.isEmpty(strLastName) && TextUtils.isEmpty(strPhone) &&
                TextUtils.isEmpty(strEmail) && TextUtils.isEmpty(strPassword) && TextUtils.isEmpty(strPostalCode) &&
                TextUtils.isEmpty(strPromoterName)){
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "All fields are required!", Toast.LENGTH_SHORT).show();

        }
//        else if(strUserType.contains("Select user type")){
//            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
//
//        }
        else if(strPassword.length() < 6){
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
        }else{


            Log.e("hffhhfh", "registration: "+lat);
            Log.e("hffhhfh", "registration: "+lng);

            BackgroundTask backgroundTask = new BackgroundTask(Registration.this);
            backgroundTask.execute(method,strFirstName,strLastName,strPhone,strEmail,strPassword,strPostalCode,strPromoterName,"Participant",
                                          lat,lng,strAddress,"","");


//            backgroundTask.execute(method,strFirstName,strLastName,strPhone,strEmail,strPassword,strPostalCode,strPromoterName,strUserType,
//                    lat,lng,strAddress,strGender,strAge);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getLatLng();


    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Give Access", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getCurrentLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Registration.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Registration.this)
                                .removeLocationUpdates(this);
                        if(locationRequest!=null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double Longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            Log.e("Location", "onLocationResult: "+latitude  +"\n" +Longitude );
                        }

                    }
                }, Looper.getMainLooper());
    }

}

