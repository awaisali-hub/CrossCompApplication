package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.eclairios.CrossComps.R;

public class CoordinatorCheck_InScreen1_7Activity extends AppCompatActivity {

    EditText phone;
    private int keyDel = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_check__in_screen1_7);
        getSupportActionBar().hide();

        phone = findViewById(R.id.phoneNumber);
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
    }

    public void MedicalClearance(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen1_7Activity.this,CoordinatorMedicalClearanceScreen1a_7Activity.class));
    }

    public void InformedConsent(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen1_7Activity.this,CoordinatorInformedConsentScreen1b_7Activity.class));
    }

    public void CoordinatorCheckInScreen2_7(View view) {
        startActivity(new Intent(CoordinatorCheck_InScreen1_7Activity.this,CoordinatorCheck_InScreen2_7Activity.class));
    }
}