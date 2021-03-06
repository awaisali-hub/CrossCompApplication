package com.eclairios.CrossComps.CrossCompAffiliate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTask;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OptiHealthPledgeActivity extends AppCompatActivity {

    EditText firstName,lastName,ET_date;
    final Calendar myCalendar = Calendar.getInstance();
    Button submitAffiliate;

    CheckBox three,four,five,six,seven,eight,nine,ten,eleven,t13,
            t14,t15,t16,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,
            t28,t29,t30,t31,t32,t33,t34;

    String lat,lng;


    JSONObject agreementObject = new JSONObject();
    JSONArray agreementArray = new JSONArray();
    String currentUserID;
    String response;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opti_health_pledge);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OptiHealthPledgeActivity.this);
         currentUserID = preferences.getString("CurrentUserId", "");

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        ET_date = findViewById(R.id.date);
        submitAffiliate = findViewById(R.id.submitAffiliate);

        three = (CheckBox) findViewById(R.id.three);
        four = (CheckBox) findViewById(R.id.four);
        five = (CheckBox) findViewById(R.id.five);
        six = (CheckBox) findViewById(R.id.six);
        seven = (CheckBox) findViewById(R.id.seven);
        eight = (CheckBox) findViewById(R.id.eight);
        nine = (CheckBox) findViewById(R.id.nine);
        ten = (CheckBox) findViewById(R.id.ten);
        eleven = (CheckBox) findViewById(R.id.eleven);

        t13 = (CheckBox) findViewById(R.id.t13);
        t14 = (CheckBox) findViewById(R.id.t14);
        t15 = (CheckBox) findViewById(R.id.t15);
        t16 = (CheckBox) findViewById(R.id.t16);


        t18 = (CheckBox) findViewById(R.id.t18);
        t19 = (CheckBox) findViewById(R.id.t19);
        t20 = (CheckBox) findViewById(R.id.t20);
        t21 = (CheckBox) findViewById(R.id.t21);
        t22 = (CheckBox) findViewById(R.id.t22);
        t23 = (CheckBox) findViewById(R.id.t23);
        t24 = (CheckBox) findViewById(R.id.t24);
        t25 = (CheckBox) findViewById(R.id.t25);
        t26 = (CheckBox) findViewById(R.id.t26);
        t27 = (CheckBox) findViewById(R.id.t27);
        t28 = (CheckBox) findViewById(R.id.t28);
        t29 = (CheckBox) findViewById(R.id.t29);
        t30 = (CheckBox) findViewById(R.id.t30);
        t31 = (CheckBox) findViewById(R.id.t31);
        t32 = (CheckBox) findViewById(R.id.t32);
        t33 = (CheckBox) findViewById(R.id.t33);
        t34 = (CheckBox) findViewById(R.id.t34);


        submitAffiliate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!three.isChecked() && !four.isChecked() && !five.isChecked() && !six.isChecked() && !seven.isChecked() && !eight.isChecked() && !nine.isChecked() && !ten.isChecked() && !eleven.isChecked()){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Please Select One Item from first Nine", Toast.LENGTH_SHORT).show();
                }else if(!t13.isChecked() || !t14.isChecked() || !t15.isChecked() || !t16.isChecked()){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Please Accept Agreement", Toast.LENGTH_SHORT).show();
                }else if(!t18.isChecked() && !t19.isChecked() && !t20.isChecked() && !t21.isChecked() && !t22.isChecked() && !t23.isChecked() && !t24.isChecked() &&
                        !t25.isChecked() && !t26.isChecked() && !t27.isChecked() && !t28.isChecked() && !t29.isChecked() && !t30.isChecked() && !t31.isChecked() &&
                        !t32.isChecked() && !t33.isChecked() && !t34.isChecked()){

                    Toast.makeText(OptiHealthPledgeActivity.this, "Some Information required!!!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(firstName.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "First Name required!!!", Toast.LENGTH_SHORT).show();
                }else if( TextUtils.isEmpty(lastName.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Last Name required!!!", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(ET_date.getText().toString())){
                    Toast.makeText(OptiHealthPledgeActivity.this, "Date required!!!", Toast.LENGTH_SHORT).show();
                }else{


                try {
                    agreementObject.put("CurrentUserID",currentUserID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i<30;i++){

                        if(i==0){
                            if(three.isChecked()){
                                try {
                                    agreementObject.put("Parent",three.getText().toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==1){
                            if(four.isChecked()){
                                try {
                                    agreementObject.put("Teacher",four.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==2){
                            if(five.isChecked()){
                                try {
                                    agreementObject.put("Coach",five.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==3){
                            if(six.isChecked()){
                                try {
                                    agreementObject.put("Pastor",six.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==4){
                            if(seven.isChecked()){
                                try {
                                    agreementObject.put("Employer",seven.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==5){
                            if(eight.isChecked()){
                                try {
                                    agreementObject.put("Neighbor",eight.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==6){
                            if(nine.isChecked()){
                                try {
                                    agreementObject.put("Friend",nine.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==7){
                            if(ten.isChecked()){
                                try {
                                    agreementObject.put("Health Professional",ten.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==8){
                            if(eleven.isChecked()){
                                try {
                                    agreementObject.put("Citizen",eleven.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==9){
                            if(t13.isChecked()){
                                try {
                                    agreementObject.put("Rule 1",t13.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==10){
                            if(t14.isChecked()){
                                try {
                                    agreementObject.put("Rule 2",t14.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==11){
                            if(t15.isChecked()){
                                try {
                                    agreementObject.put("Rule 3",t15.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==12){
                            if(t16.isChecked()){
                                try {
                                    agreementObject.put("Rule 4",t16.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==13){
                            if(t18.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 1",t18.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==14){
                            if(t19.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 2",t19.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==15){
                            if(t20.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 3",t20.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==16){
                            if(t21.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 4",t21.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==17){
                            if(t22.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 5",t22.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==18){
                            if(t23.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 6",t23.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==19){
                            if(t24.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 7",t24.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==20){
                            if(t25.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 8",t25.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==21){
                            if(t26.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 9",t26.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==22){
                            if(t27.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 10",t27.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==23){
                            if(t28.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 11",t28.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==24){
                            if(t29.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 12",t29.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==25){
                            if(t30.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 13",t30.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==26){
                            if(t31.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 14",t31.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==27){
                            if(t32.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 15",t32.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==28){
                            if(t33.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 16",t33.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if(i==29){
                            if(t34.isChecked()){
                                try {
                                    agreementObject.put("lifeStyle idea 17",t34.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                    agreementArray.put(agreementObject);
                    String agreements = agreementArray.toString();
                    Log.e("sdjhghgfdgs", "onClick: "+agreements );



                            new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream os = null;
                InputStream is = null;
                HttpURLConnection conn = null;
                try {
                    //constants
                    URL url = new URL("http://edevz.com/cross_comp/affiliate_agreement.php");

                    String message = agreementArray.toString();

                    Log.e("fddfhjhh", "run: "+message);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();

                    //do somehting with response
                    is = conn.getInputStream();
                    //String contentAsString = readIt(is,len);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

                    response = "";
                    String line = "";
                    while( (line = bufferedReader.readLine()) != null)
                    {
                        response += line;
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {

                            if(response.equals("Insert your JSON record successfully")){
                                String method = "AffiliatesRegister";

                                lat = preferences.getString("lat", null);
                                lng = preferences.getString("lng", null);

                                String AffiliateType = preferences.getString("affiliateType", "");
                                String email = preferences.getString("Email123","");

                                Log.e("emailtest", "run: "+email);

                                BackgroundTask backgroundTask = new BackgroundTask(OptiHealthPledgeActivity.this);
                                backgroundTask.execute(method, currentUserID, firstName.getText().toString(), lastName.getText().toString(),email, ET_date.getText().toString(), lat, lng, AffiliateType,"0","0");


                            }else{
                                Toast.makeText(OptiHealthPledgeActivity.this, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("hsadshjd", "run: "+response );
                        }
                    });




                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //clean up
                    try {
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                }
            }
        }).start();





//                    MoveToAffiliateDashboard(v);





                }
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };


        ET_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (ET_date.getRight() - ET_date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        new DatePickerDialog(OptiHealthPledgeActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        return true;
                    }
                }
                return false;
            }
        });
    }

//    public void MoveToAffiliateDashboard(View view) {
//        startActivity(new Intent(OptiHealthPledgeActivity.this,AffiliateDashboardActivity.class));
//    }

    public void MoveToMainHomePage(View view) {
        startActivity(new Intent(OptiHealthPledgeActivity.this, Dashboard.class));
    }



    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ET_date.setText(sdf.format(myCalendar.getTime()));
    }

}