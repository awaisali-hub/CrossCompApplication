package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.R;

public class CoordinatorRegistration3_4Activity extends AppCompatActivity {

    EditText street,city,state,postalCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration3_4);
        getSupportActionBar().hide();

        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        postalCode = findViewById(R.id.postalCode);

    }

    public void Registration4_4(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String EventOrService = preferences.getString("serviceOrEvent", null);

        if(TextUtils.isEmpty(street.getText().toString())){
            Toast.makeText(this, "Street Name Required", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(city.getText().toString())){
            Toast.makeText(this, "City Name Required", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(state.getText().toString())){
            Toast.makeText(this, "State Name Required", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(postalCode.getText().toString())){
            Toast.makeText(this, "PostalCode Required", Toast.LENGTH_SHORT).show();

        }else{

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("streetName",street.getText().toString());
            editor.putString("cityName",city.getText().toString());
            editor.putString("stateName",state.getText().toString());
            editor.putString("postalCode",postalCode.getText().toString());
            editor.apply();

            if(EventOrService.equals("service")){
                startActivity(new Intent(CoordinatorRegistration3_4Activity.this,CoordinatorRegistration4a_4FacilityActivity.class));
            }else{
                startActivity(new Intent(CoordinatorRegistration3_4Activity.this,CoordinatorRegistration4b_4EventActivity.class));
            }
        }



    }
}