package com.eclairios.CrossComps.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.Authentication.Registration;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.R;

public class UpdateProfileActivity extends AppCompatActivity {

  private   EditText first_Name,last_Name,phone_number,email,postal_code,address,gender,age,promoter;
   private String str_first_Name,str_last_Name,str_phone_number,str_email,str_postal_code,str_address,str_gender,str_age,str_promoter;
    String currentUserID;
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


        str_first_Name = getIntent().getStringExtra("str_first_Name");
        str_last_Name = getIntent().getStringExtra("str_last_Name");
        str_phone_number = getIntent().getStringExtra("str_phone_number");
        str_email = getIntent().getStringExtra("str_email");
        str_postal_code = getIntent().getStringExtra("str_postal_code");
        str_address = getIntent().getStringExtra("str_address");
        str_gender = getIntent().getStringExtra("str_gender");
        str_age = getIntent().getStringExtra("str_age");
        str_promoter = getIntent().getStringExtra("str_promoter");



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UpdateProfileActivity.this);
        currentUserID = preferences.getString("CurrentUserId", "");

        first_Name.setText(str_first_Name);
        last_Name.setText(str_last_Name);
        phone_number.setText(str_phone_number);
        email.setText(str_email);
        postal_code.setText(str_postal_code);
        address.setText(str_address);
        gender.setText(str_gender);
        age.setText(str_age);


    }

    public void UpdateProfileDetail(View view) {
        String method = "updateUserProfile";

        BackgroundTask backgroundTask = new BackgroundTask(UpdateProfileActivity.this);
        backgroundTask.execute(method, currentUserID, first_Name.getText().toString(), last_Name.getText().toString(), phone_number.getText().toString(), email.getText().toString(), postal_code.getText().toString(), address.getText().toString(), gender.getText().toString(), age.getText().toString());
   //     startActivity(new Intent(UpdateProfileActivity.this,Profile.class));
    }


}