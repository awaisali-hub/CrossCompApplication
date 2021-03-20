package com.eclairios.CrossComps.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.AllTeamCategoryActivity;
import com.google.gson.JsonArray;

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

public class UpdateProfileActivity extends AppCompatActivity {

  private   EditText first_Name,last_Name,phone_number,email,postal_code,address,gender,age,promoter;
   private String str_first_Name,str_last_Name,str_phone_number,str_email,str_postal_code,str_address,str_gender,str_age,str_promoter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile2);
        getSupportActionBar().hide();

        first_Name = findViewById(R.id.first_Namee);
        last_Name = findViewById(R.id.last_Namee);
        phone_number = findViewById(R.id.phone_numberr);
        email = findViewById(R.id.emaill);
        postal_code = findViewById(R.id.postal_codee);
        address = findViewById(R.id.addresss);
        gender = findViewById(R.id.genderr);
        age = findViewById(R.id.agee);

//        intent.putExtra("str_first_Name", str_first_Name);
//        intent.putExtra("str_last_Name", str_last_Name);
//        intent.putExtra("str_email", str_email);
//        intent.putExtra("str_postal_code", str_postal_code);
//        intent.putExtra("str_address", str_address);
//        intent.putExtra("str_gender", str_gender);
//        intent.putExtra("str_age", str_age);
//        intent.putExtra("str_promoter", str_promoter);
//        startActivity(intent);


        String str_first_Name = getIntent().getStringExtra("str_first_Name");
        String str_last_Name = getIntent().getStringExtra("str_last_Name");
        String phone_numberr = getIntent().getStringExtra("str_phone_number");
        String emaill = getIntent().getStringExtra("str_email");
        String postal_codee = getIntent().getStringExtra("str_postal_code");
        String addresss = getIntent().getStringExtra("str_address");
        String genderr = getIntent().getStringExtra("str_gender");
        String agee = getIntent().getStringExtra("str_age");
        String str_promoter = getIntent().getStringExtra("str_promoter");

        Log.e("fdsfsd", "onCreate: "+str_first_Name);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UpdateProfileActivity.this);
        String currentUserID = preferences.getString("CurrentUserId", "");

        first_Name.setText(str_first_Name);
        last_Name.setText(str_last_Name);
        phone_number.setText(phone_numberr);
        email.setText(emaill);
        postal_code.setText(postal_codee);
        address.setText(addresss);
        gender.setText(genderr);
        age.setText(agee);


    }

    public void UpdateProfileDetail(View view) {
        Toast.makeText(this, "Profile updated", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UpdateProfileActivity.this,Profile.class));
    }


}