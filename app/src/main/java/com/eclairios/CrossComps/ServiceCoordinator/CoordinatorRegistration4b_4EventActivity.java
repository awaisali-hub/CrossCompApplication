package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eclairios.CrossComps.R;

public class CoordinatorRegistration4b_4EventActivity extends AppCompatActivity {
    int count = 0;
    EditText event1_session1StartAm,event1_session1EndAm,event1_session2Startpm,event1_session2Endpm,
            event2_session1StartAm, event2_session1EndAm,event2_session2Startpm,event2_session2Endpm,
            event3_session1StartAm,event3_session1EndAm,event3_session2Startpm,event3_session2Endpm,
            event4_session1Startam,event4_session1Endam,event4_session2Startpm,event4_session2Endpm;
    private int keyDel=0;
    LinearLayout layout1,layout2,layout3,layout4,layout5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration4b_4_event);
        getSupportActionBar().hide();


        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        layout3 = (LinearLayout)findViewById(R.id.layout3);
        layout4 = (LinearLayout)findViewById(R.id.layout4);
        layout5 = (LinearLayout)findViewById(R.id.layout5);
        layout1.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        layout2.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        layout3.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        layout4.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        layout5.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        layout5.setVisibility(View.INVISIBLE);


        event1_session1StartAm = findViewById(R.id.event1_session1StartAm);
        event1_session1EndAm = findViewById(R.id.event1_session1EndAm);
        event1_session2Startpm = findViewById(R.id.event1_session2Startpm);
        event1_session2Endpm = findViewById(R.id.event1_session2Endpm);

        event2_session1StartAm = findViewById(R.id.event2_session1StartAm);
        event2_session1EndAm = findViewById(R.id.event2_session1EndAm);
        event2_session2Startpm = findViewById(R.id.event2_session2Startpm);
        event2_session2Endpm = findViewById(R.id.event2_session2Endpm);

        event3_session1StartAm = findViewById(R.id.event3_session1StartAm);
        event3_session1EndAm = findViewById(R.id.event3_session1EndAm);
        event3_session2Startpm = findViewById(R.id.event3_session2Startpm);
        event3_session2Endpm = findViewById(R.id.event3_session2Endpm);

        event4_session1Startam = findViewById(R.id.event3_session2Endpm);
        event4_session1Endam = findViewById(R.id.event4_session1Endam);
        event4_session2Startpm = findViewById(R.id.event4_session2Startpm);
        event4_session2Endpm = findViewById(R.id.event4_session2Endpm);


        event1_session1StartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event1_session1StartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event1_session1StartAm.getText().length();
                    if(len == 2) {
                        event1_session1StartAm.setText(event1_session1StartAm.getText() + ":");
                        event1_session1StartAm.setSelection(event1_session1StartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event1_session1EndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event1_session1EndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event1_session1EndAm.getText().length();
                    if(len == 2) {
                        event1_session1EndAm.setText(event1_session1EndAm.getText() + ":");
                        event1_session1EndAm.setSelection(event1_session1EndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event1_session2Startpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event1_session2Startpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event1_session2Startpm.getText().length();
                    if(len == 2) {
                        event1_session2Startpm.setText(event1_session2Startpm.getText() + ":");
                        event1_session2Startpm.setSelection(event1_session2Startpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event1_session2Endpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event1_session2Endpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event1_session2Endpm.getText().length();
                    if(len == 2) {
                        event1_session2Endpm.setText(event1_session2Endpm.getText() + ":");
                        event1_session2Endpm.setSelection(event1_session2Endpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        event2_session1StartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event2_session1StartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event2_session1StartAm.getText().length();
                    if(len == 2) {
                        event2_session1StartAm.setText(event2_session1StartAm.getText() + ":");
                        event2_session1StartAm.setSelection(event2_session1StartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event2_session1EndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event2_session1EndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event2_session1EndAm.getText().length();
                    if(len == 2) {
                        event2_session1EndAm.setText(event2_session1EndAm.getText() + ":");
                        event2_session1EndAm.setSelection(event2_session1EndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event2_session2Startpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event2_session2Startpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event2_session2Startpm.getText().length();
                    if(len == 2) {
                        event2_session2Startpm.setText(event2_session2Startpm.getText() + ":");
                        event2_session2Startpm.setSelection(event2_session2Startpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event2_session2Endpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event2_session2Endpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event2_session2Endpm.getText().length();
                    if(len == 2) {
                        event2_session2Endpm.setText(event2_session2Endpm.getText() + ":");
                        event2_session2Endpm.setSelection(event2_session2Endpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        event3_session1StartAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event3_session1StartAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event3_session1StartAm.getText().length();
                    if(len == 2) {
                        event3_session1StartAm.setText(event3_session1StartAm.getText() + ":");
                        event3_session1StartAm.setSelection(event3_session1StartAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event3_session1EndAm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event3_session1EndAm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event3_session1EndAm.getText().length();
                    if(len == 2) {
                        event3_session1EndAm.setText(event3_session1EndAm.getText() + ":");
                        event3_session1EndAm.setSelection(event3_session1EndAm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event3_session2Startpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event3_session2Startpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event3_session2Startpm.getText().length();
                    if(len == 2) {
                        event3_session2Startpm.setText(event3_session2Startpm.getText() + ":");
                        event3_session2Startpm.setSelection(event3_session2Startpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event3_session2Endpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event3_session2Endpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event3_session2Endpm.getText().length();
                    if(len == 2) {
                        event3_session2Endpm.setText(event3_session2Endpm.getText() + ":");
                        event3_session2Endpm.setSelection(event3_session2Endpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        event4_session1Startam.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event4_session1Startam.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event4_session1Startam.getText().length();
                    if(len == 2) {
                        event4_session1Startam.setText(event4_session1Startam.getText() + ":");
                        event4_session1Startam.setSelection(event4_session1Startam.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event4_session1Endam.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event4_session1Endam.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event4_session1Endam.getText().length();
                    if(len == 2) {
                        event4_session1Endam.setText(event4_session1Endam.getText() + ":");
                        event4_session1Endam.setSelection(event4_session1Endam.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event4_session2Startpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event4_session2Startpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event4_session2Startpm.getText().length();
                    if(len == 2) {
                        event4_session2Startpm.setText(event4_session2Startpm.getText() + ":");
                        event4_session2Startpm.setSelection(event4_session2Startpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        event4_session2Endpm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                event4_session2Endpm.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = event4_session2Endpm.getText().length();
                    if(len == 2) {
                        event4_session2Endpm.setText(event4_session2Endpm.getText() + ":");
                        event4_session2Endpm.setSelection(event4_session2Endpm.getText().length());
                    }
                } else {
                    keyDel = 0;
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


    }

    public void addNewEvent(View view) {
        count++;
        setcount(count);


    }

    public void setcount(int count){

        if(count==1){
           // layout1.setLayoutParams(new LinearLayout.LayoutParams(1030, 350));
            layout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout1.setVisibility(View.VISIBLE);
    }else if(count==2){
            layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            layout2.setVisibility(View.VISIBLE);
    }
        else if(count==3){
            layout3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            layout3.setVisibility(View.VISIBLE);
        }
        else if(count==4){
            layout4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            layout4.setVisibility(View.VISIBLE);
        }else if(count==5){
            layout5.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            layout5.setVisibility(View.VISIBLE);
        }
}

    public void moverToSummery(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String EventOrService = preferences.getString("serviceOrEvent", null);
        String organizationName = preferences.getString("organizationName", null);
        String street = preferences.getString("street", null);
        String city = preferences.getString("city", null);
        String state = preferences.getString("state", null);
        String postalCode = preferences.getString("postalCode", null);
        Log.d("TAGG", "moverToSummery: ");
        Log.d("TAGG", "moverToSummery: "+postalCode);

        //Log.d("TAGGG", "moverToSummery: "+organizationName);
//        preferences.edit().putString("street",street.getText().toString()).apply();
//        preferences.edit().putString("street",city.getText().toString()).apply();
//        preferences.edit().putString("street",state.getText().toString()).apply();
//        preferences.edit().putString("street",postalCode.getText().toString()).apply();


        startActivity(new Intent(CoordinatorRegistration4b_4EventActivity.this,CoordinatorServiceSummaryScreenActivity.class));
    }
}