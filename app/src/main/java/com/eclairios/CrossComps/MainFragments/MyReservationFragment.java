package com.eclairios.CrossComps.MainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.eclairios.CrossComps.Adapter.AdapterHorizontal;
import com.eclairios.CrossComps.Adapter.MyReservationAdapter;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.R;
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
import java.util.ArrayList;

public class MyReservationFragment extends Fragment {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1,jsonArray2,jsonArray3;

    String lat,lng;

    RecyclerView recyclerView;
    MyReservationAdapter adapterHorizontal;
    ArrayList<ModelHorizontal> reservationModel = new ArrayList<>() ;

    SharedPreferences preferences;
    FloatingActionButton requestReservation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reservation, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        requestReservation = view.findViewById(R.id.fabAddReservation);

        recyclerView = view.findViewById(R.id.reservations_list);
        adapterHorizontal = new MyReservationAdapter( reservationModel, getContext() );
        recyclerView.setAdapter(adapterHorizontal);


        try{
            WaitDialog.showDialog( getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Dashboard.class);
                intent.putExtra("fragmentNumber",4);
                startActivity(intent);
            }
        });

        return view;
    }

    class BackgroundTask extends AsyncTask<String, Void, String>
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
                String currentUserID = preferences.getString("CurrentUserId", "");


                String data = URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_all_MyReservations.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string != null){




                try {

                    jsonObject = new JSONObject(json_string);
                    Log.e("bcjknjkksdjcghghhgfdf ", "onCreate: "+json_string );

                    jsonArray = jsonObject.getJSONArray("server_response");
                    jsonArray3 = jsonObject.getJSONArray("Send_Event_Time_ID");

                    String serviceId, serviceName, serviceAddress, serviceType, sendTimeId;
                    String serviceDate;

                    int count = 0;


                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        JSONObject JO1 = jsonArray3.getJSONObject(count);
                        serviceId = JO.getString("Event_ID");
                        serviceName = JO.getString("Event_Name");
                        serviceAddress = JO.getString("Address");
                        serviceType = JO.getString("Type");

                        sendTimeId = JO1.getString("Event_Time_ID");

                            Log.e("dfdfdfdsfhhkjk", "onPostExecute: " + serviceName);
                            Log.e("dfdfdfdsfhhkjk", "onPostExecute: " + serviceAddress);
                            ModelHorizontal users = new ModelHorizontal();
                            users.setEvent_Time_ID(sendTimeId);
                            users.setServiceID(serviceId);
                            users.setServiceName(serviceName);
                            users.setServiceAddress(serviceAddress);
                            users.setItem_type(serviceType);

                            reservationModel.add(users);
                            count++;

                    }

                    adapterHorizontal.notifyDataSetChanged();


                    jsonArray1 = jsonObject.getJSONArray("service");
                    jsonArray2 = jsonObject.getJSONArray("serviceReservationDate");

                    int count1 = 0;
                    while (count1 < jsonArray1.length()) {
                            JSONObject JO = jsonArray1.getJSONObject(count1);
                            JSONObject JO1 = jsonArray2.getJSONObject(count1);
                            serviceId = JO.getString("Service_ID");
                            serviceName = JO.getString("Service_Name");
                            serviceAddress = JO.getString("Address");
                            serviceType = JO.getString("Type");
                            serviceDate = JO1.getString("Reservation_Date");


                            Log.e("dfdfdfdsfhhkjk", "onPostExecute: " + serviceName);
                            Log.e("dfdfdfdsfhhkjk", "onPostExecute: " + serviceAddress);

                            ModelHorizontal users = new ModelHorizontal();
                            users.setServiceID(serviceId);
                            users.setServiceReservationDate(serviceDate);
                            users.setServiceName(serviceName);
                            users.setServiceAddress(serviceAddress);
                            users.setItem_type(serviceType);
                            reservationModel.add(users);
                            count1++;

                    }




                    adapterHorizontal.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            try{
                WaitDialog.hideDialog( );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        reservationModel.clear();
        Log.e("hdhddh", "onStart: "+lat+lng );
        new BackgroundTask().execute();
    }
}