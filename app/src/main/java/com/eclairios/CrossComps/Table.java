package com.eclairios.CrossComps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eclairios.CrossComps.Adapter.AdapterTable;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.MainScoreDashboard.Participent;
import com.eclairios.CrossComps.Model.ModelTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Table extends Fragment {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView recycleTable,recycleTable2;
    AdapterTable adapterTable;
    AdapterTable adapterTable2;
    ArrayList<ModelTable> tableList = new ArrayList<>() ;
    ArrayList<ModelTable> tableList2 = new ArrayList<>();
    TextView schedulenextCrossComp;
    TextView scheduleService;
    FloatingActionButton home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_table, container, false);

        recycleTable2 = v.findViewById(R.id.tableRecyleView2);



        scheduleService = v.findViewById(R.id.schedule);
        home = v.findViewById(R.id.home);
        recycleTable =(RecyclerView) v.findViewById(R.id.tableRecyleView);
        adapterTable = new AdapterTable( tableList,getContext() );
        adapterTable2 = new AdapterTable(tableList2,getContext());
        recycleTable.setAdapter(adapterTable);

        recycleTable2.setAdapter(adapterTable2);

        recycleTable.smoothScrollToPosition(adapterTable.getItemCount());

        scheduleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                Intent intent = new Intent(getContext(), Dashboard.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Participent.class));
            }
        });
        return v;
    }


    class BackgroundTasks extends AsyncTask<String, Void, String>
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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String CurrentUserID = preferences.getString("CurrentUserId", "");



                String data = URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_participants_last_three_score.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");



                int count = 0;
                int age = 0;
                double age1 = 0;

                String scoreID,userID,Date,Age,cardio, legs,abs,arms,totalScore;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    scoreID = JO.getString("Score_ID");
                    userID = JO.getString("User_ID");
                    Date = JO.getString("Date");
                    Age = JO.getString("Age");
                    cardio = JO.getString("Cardio");
                    legs = JO.getString("Legs");
                    abs = JO.getString("Abs");
                    arms = JO.getString("Arms");
                    totalScore = JO.getString("Score");


                    float myFloat = Float.parseFloat(Age);

                    String formattedAgeString = String.format("%.02f", myFloat);

                    String date = Date;
                    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date newDate = spf.parse(date);
                    spf = new SimpleDateFormat("MMM/yyyy");
                    String newDateString = spf.format(newDate);

                    Log.e("hdhjashdashdjsahjddhdh", "onPostExecute: "+age1);

                    ModelTable score = new ModelTable();
                    score.setMonthYear(newDateString);
                    score.setAge("("+formattedAgeString+")");
                    score.setA(cardio);
                    score.setB(legs);
                    score.setC(abs);
                    score.setD(arms);
                    score.setTotal(totalScore);

                    tableList.add(score);


                    count++;

                }
                adapterTable = new AdapterTable( tableList,getContext() );
                recycleTable.setAdapter(adapterTable);


                JSONObject lastObj = jsonArray.getJSONObject(jsonArray.length()-1);
                String d = lastObj.getString("Date");
                String a = lastObj.getString("Age");

                Log.e("ageanddate", "onPostExecute: "+a+"\n"+d );




                    float nextAge = Float.parseFloat(a);
                    nextAge = nextAge*365;

                    Log.e("countAge", "onPostExecute: "+nextAge );



                String dateInString = d;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(dateInString));

                Log.e("loop", "onPostExecute: " );
                int i = 0 ;

                while (i<4){
                    c.add(Calendar.DATE, 90);
                    sdf = new SimpleDateFormat("MMM/yyyy");
                    Date resultDate = new Date(c.getTimeInMillis());
                    dateInString = sdf.format(resultDate);

                    d = dateInString;

                    Log.e("loop", "onPostExecute: |"+dateInString);
                    nextAge = nextAge+90;
                    float fAge = nextAge/365;
                    a = String.valueOf(fAge);

                    Log.e("agesdsss", "onPostExecute: "+fAge );

                    Log.e("dateInString", "onPostExecute: "+dateInString );

                    float myFloat = fAge;
                    String formattedString = String.format("%.02f", myFloat);

                    ModelTable score = new ModelTable();
                    score.setMonthYear(dateInString);
                    score.setAge("("+formattedString+")");
                    score.setA("?");
                    score.setB("?");
                    score.setC("?");
                    score.setD("?");
                    score.setTotal("?");

                    tableList2.add(score);
                    i++;
                }


                adapterTable2 = new AdapterTable( tableList2,getContext() );
                recycleTable2.setAdapter(adapterTable2);



            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        tableList.clear();
        tableList2.clear();
        new BackgroundTasks().execute();
    }


}
