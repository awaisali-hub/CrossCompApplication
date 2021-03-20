package com.eclairios.CrossComps.Judge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eclairios.CrossComps.R;

import static android.view.View.VISIBLE;
import static androidx.constraintlayout.solver.widgets.ConstraintWidget.INVISIBLE;
import static com.eclairios.CrossComps.R.color.colorRed;
import static com.eclairios.CrossComps.R.color.green;

public class JudgeHomePageParticipantRegistrationActivity extends AppCompatActivity {

    int keyDel;
    EditText participant_phone1,participant_phone2;
    Button judge_StrBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_home_page_participant_registration);
        getSupportActionBar().hide();

        participant_phone1 = findViewById(R.id.participant_phone1);
        participant_phone2 = findViewById(R.id.participant_phone2);
        judge_StrBtn = findViewById(R.id.judge_StrBtn);


judge_StrBtn.setClickable(false);

        participant_phone1.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant_phone1.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = participant_phone1.getText().length();
                    if(len == 3) {
                        participant_phone1.setText(participant_phone1.getText() + "-");
                        participant_phone1.setSelection(participant_phone1.getText().length());
                    }else if(len == 8){
                        participant_phone1.setText(participant_phone1.getText() + "-");
                        participant_phone1.setSelection(participant_phone1.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
                if(participant_phone1.getText().toString().length()==13&&participant_phone2.getText().toString().isEmpty())     //size as per your requirement
                {
                    participant_phone2.requestFocus();
                }else if(participant_phone2.getText().toString().length()==13 && participant_phone1.getText().toString().length()==13){
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    judge_StrBtn.setClickable(true);
                    judge_StrBtn.setEnabled(true);
                }
                else if (participant_phone1.getText().toString().length()<13){
                    judge_StrBtn.setEnabled(false);
                  //  judge_StrBtn.getBackground().setColorFilter(ContextCompat.getColor(JudgeHomePageParticipantRegistrationActivity.this, R.color.colorRedd), PorterDuff.Mode.MULTIPLY);
                  //  judge_StrBtn.setCompoundDrawableTintMode(colorRed);
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));

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

        participant_phone2.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant_phone2.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = participant_phone2.getText().length();
                    if(len == 3) {
                        participant_phone2.setText(participant_phone2.getText() + "-");
                        participant_phone2.setSelection(participant_phone2.getText().length());
                    }else if(len == 8){
                        participant_phone2.setText(participant_phone2.getText() + "-");
                        participant_phone2.setSelection(participant_phone2.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
                if(participant_phone2.getText().toString().length()==13 && participant_phone1.getText().toString().length()==13)     //size as per your requirement

                {

                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    judge_StrBtn.setClickable(true);
                    judge_StrBtn.setEnabled(true);

                }
                else if (participant_phone2.getText().toString().length()<13){
                    judge_StrBtn.setEnabled(false);
                    judge_StrBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRed));



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


                            judge_StrBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(JudgeHomePageParticipantRegistrationActivity.this,Judge2aRun_Walk_TestActivity.class));
                        }
                    });


    }



//    public void RunWalkScreen(View view) {
//        Log.e("vxcdsfvxc", "RunWalkScreen: " );
//        judge_StrBtn.getBackgroundTintList();
//        Log.e("dfsdfsddf", "RunWalkScreen: "+judge_StrBtn.getBackgroundTintList());
//        //if (( judge_StrBtn.getBackgroundTintList()) == judge_StgetBackgroundTintList(ContextCompat.getColor(getApplicationContext(),1))){
//            startActivity(new Intent(JudgeHomePageParticipantRegistrationActivity.this,Judge2aRun_Walk_TestActivity.class));
//      //  }
//
//    }
}