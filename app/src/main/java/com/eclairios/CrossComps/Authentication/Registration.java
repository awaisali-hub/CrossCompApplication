package com.eclairios.CrossComps.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.Model.CityModel;
import com.eclairios.CrossComps.Model.CountryModel;
import com.eclairios.CrossComps.Model.StateModel;
import com.eclairios.CrossComps.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Registration extends AppCompatActivity{

    private String json_string;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private EditText firstName, lastName, phone, email, password;
    private ProgressDialog progressDialog;
    private String lat, lng, MainAddress;
    private Button signUpButton, signInBtn;
    private int keyDel = 0;
    private CheckBox rememberMe;
    private Spinner country,state,city,postalCode;


    private ArrayList<CountryModel> country_arrayList = new ArrayList();
    private ArrayList<StateModel> state_arrayList = new ArrayList();
    private ArrayList<CityModel> city_arrayList = new ArrayList();


    private ArrayAdapter<CountryModel> country_adapter;
    private ArrayAdapter<StateModel> state_adapter;
    private ArrayAdapter<CityModel> city_adapter;

    private int item_check = 0;

    private String country_id,country_name;
    private String state_id,state_name;
    private String city_id,city_name;

    Boolean temp = false;
    Boolean check = false;

    //String country_name,state_name,city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        new BackgroundTasksForCountries().execute();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        signInBtn = findViewById(R.id.signInBtn);
        rememberMe = findViewById(R.id.rememberMe);

        country = (Spinner) findViewById(R.id.country_spinner);
        state = (Spinner) findViewById(R.id.state_spinner);
        city = (Spinner) findViewById(R.id.city_spinner);
        postalCode = findViewById(R.id.postalCode_spinner);



        country_adapter = new ArrayAdapter<CountryModel>(Registration.this, android.R.layout.simple_list_item_1, country_arrayList);
        country.setAdapter(country_adapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                    CountryModel selectedItem = (CountryModel) country.getSelectedItem(); // Object which was selected.
                    Log.e("countryId", "onItemSelected: "+selectedItem.getCountry_id() );

                    state_arrayList.clear();
                    city_arrayList.clear();

          //      String[] cities = getResources().getStringArray(R.array.Cities);

                state_adapter = new ArrayAdapter<StateModel>(Registration.this, android.R.layout.simple_list_item_1, state_arrayList);
                state.setAdapter(state_adapter);

                city_adapter = new ArrayAdapter<CityModel>(Registration.this, android.R.layout.simple_list_item_1, city_arrayList);
                city.setAdapter(city_adapter);


//                    if(country_id.equals("1")){
//                        new BackgroundTasksForStates().execute();
//                        state.setClickable(true);
//                    }


                if(temp){

                    if(!selectedItem.getCountry_name().equals("Select Country")) {
                        country_id = selectedItem.getCountry_id();
                        new BackgroundTasksForStates().execute();
                    }
                }

                temp = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });




        state_adapter = new ArrayAdapter<StateModel>(Registration.this, android.R.layout.simple_list_item_1, state_arrayList);
        state.setAdapter(state_adapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                StateModel selectedItem = (StateModel) state.getSelectedItem(); // Object which was selected.
                Log.e("stateId", "onItemSelected: "+selectedItem.getState_id() );
                Log.e("stateId", "onItemSelected: "+selectedItem.getState_name() );
                city_arrayList.clear();
                city_adapter = new ArrayAdapter<CityModel>(Registration.this, android.R.layout.simple_list_item_1, city_arrayList);
                city.setAdapter(city_adapter);

                if(temp){
                    city.setClickable(true);
                    if(!selectedItem.getState_name().equals("Select State")) {
                        state_id = selectedItem.getState_id();
                        new BackgroundTasksForCities().execute();
                    }
                }

                temp = true;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        city_adapter = new ArrayAdapter<CityModel>(Registration.this, android.R.layout.simple_list_item_1, city_arrayList);
        city.setAdapter(city_adapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                CityModel selectedItem = (CityModel) city.getSelectedItem(); // Object which was selected.
                city_id = selectedItem.getCity_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



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



        if(country.getSelectedItem() !=null){
            country_name = country.getSelectedItem().toString();
        }


        if(state.getSelectedItem() !=null){
           state_name = state.getSelectedItem().toString();
        }

        if(city.getSelectedItem() !=null){
           city_name = city.getSelectedItem().toString();
        }




        if (TextUtils.isEmpty(strFirstName) || TextUtils.isEmpty(strLastName) || TextUtils.isEmpty(strPhone) ||
                TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword)) {
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "All fields are required!", Toast.LENGTH_SHORT).show();

        } else if (strPassword.length() < 6) {
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
        }else if(country_name.equals("Select Country")){
            Toast.makeText(Registration.this, "Select Country", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(state_name.equals("Select State")){
            Toast.makeText(Registration.this, "Select State", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(city_name.equals("Select City")){
            Toast.makeText(Registration.this, "Select City", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
//        }else if(TextUtils.isEmpty(postalCode.getText().toString())){
//            Toast.makeText(Registration.this, "Enter Postal Code", Toast.LENGTH_SHORT).show();
//            progressDialog.dismiss();
        } else{


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
            backgroundTask.execute(method, strFirstName, strLastName, strPhone, strEmail, strPassword,country_id,state_id,city_id, String.valueOf(postalCode), lat, lng, MainAddress);

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


    class BackgroundTasksForCountries extends AsyncTask<String, Void, String>
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
            json_url = "http://edevz.com/cross_comp/getCountriesForRegistration.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string!=null){
                Log.e("abcd", "onCreate: "+json_string );

                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("countries");


                    int count = 0;
              //      String country_id,country_name;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);

                        country_id = JO.getString("Country_ID");
                        country_name = JO.getString("Country_Name");


                        CountryModel model = new CountryModel();
                        model.setCountry_id(country_id);
                        model.setCountry_name(country_name);
                        country_arrayList.add(model);
                        count++;
                    }

                    country_adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class BackgroundTasksForStates extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("country_id","UTF-8") + "=" + URLEncoder.encode(country_id,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/getStatesForRegistration.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string!=null){
                Log.e("abcd", "onCreate: "+json_string );

                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("states");


                    int count = -1;


                        //       String state_id,state_name;
                        while(count < jsonArray.length())
                        {
                            if(count == -1){
                                StateModel model = new StateModel();
                                model.setState_id("");
                                model.setState_name("Select State");
                                state_arrayList.add(model);
                                count++;
                            }else{
                            JSONObject JO = jsonArray.getJSONObject(count);

                            state_id = JO.getString("State_ID");
                            state_name = JO.getString("State_Name");

                            Log.e("dfgfg", "onPostExecute: "+state_id );
                            Log.e("dfgfg", "onPostExecute: "+state_name );

                            StateModel model = new StateModel();
                            model.setState_id(state_id);
                            model.setState_name(state_name);
                            state_arrayList.add(model);
                            count++;
                        }
                    }

                    state_adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    class BackgroundTasksForCities extends AsyncTask<String, Void, String>
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

                String data = URLEncoder.encode("state_id","UTF-8") + "=" + URLEncoder.encode(state_id,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/getCitiesForRegistration.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string!=null){
                Log.e("abcd", "onCreate: "+json_string );

                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("cities");


                    int count = -1;


                        //       String state_id,state_name;
                        while(count < jsonArray.length())
                        {

                            if(count == -1){
                                CityModel model = new CityModel();
                                model.setCity_id("");
                                model.setCity_name("Select City");
                                city_arrayList.add(model);
                                count++;
                            }else{

                            JSONObject JO = jsonArray.getJSONObject(count);

                            city_id = JO.getString("City_ID");
                            city_name = JO.getString("City_Name");



                            CityModel model = new CityModel();
                            model.setCity_id(city_id);
                            model.setCity_name(city_name);
                            city_arrayList.add(model);
                            count++;
                        }
                    }

                    city_adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}

