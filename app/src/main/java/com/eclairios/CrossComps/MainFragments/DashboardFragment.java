package com.eclairios.CrossComps.MainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eclairios.CrossComps.Adapter.AdapterHorizontal;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.R;

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


public class DashboardFragment extends Fragment {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray,jsonArray1;

    String lat,lng;

    RecyclerView recyclerView;
    AdapterHorizontal adapterHorizontal;
    ArrayList<ModelHorizontal> chatitem = new ArrayList<>() ;

    SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        recyclerView = view.findViewById(R.id.list);
        adapterHorizontal = new AdapterHorizontal( chatitem, getContext() );
        recyclerView.setAdapter(adapterHorizontal);


        try{
            WaitDialog.showDialog( getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

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



                Log.e("dhdhdhd", "doInBackground: "+lat);
                Log.e("dhdhdhd", "doInBackground: "+lng);


                String data = URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(lat,"UTF-8")+ "&"+
                        URLEncoder.encode("lon","UTF-8") + "=" + URLEncoder.encode(lng,"UTF-8") ;
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
            json_url = "http://edevz.com/cross_comp/get_all_coordinator.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string == null){
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("No internet connection, Check your network settings and try again.");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                   //     finish();
                    }
                });

                alertDialog.show();


            }else{
                Log.e("bcjknjkksdjc ", "onCreate: "+json_string );


                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    jsonArray1 = jsonObject.getJSONArray("event");

                    ////////////////////////////////////////
                    int count1 = 0;

                    String eventId,eventName,eventAddress,eventDay,eventDate,eventType;
                    while(count1 < jsonArray1.length())
                    {
                        JSONObject JO = jsonArray1.getJSONObject(count1);
                        eventId = JO.getString("Event_ID");
                        eventName = JO.getString("Event_Name");
                        eventAddress = JO.getString("Address");
                        eventDay = JO.getString("Day");
                        eventDate = JO.getString("Date");
                        eventType = JO.getString("Type");

                        Log.e("dateDay", "onPostExecute: "+eventDay+eventDate);


                        Log.e("jdjdud", "onPostExecute: "+ eventName);
                        Log.e("jdjdud", "onPostExecute: "+ eventAddress);

                        ModelHorizontal users = new ModelHorizontal();
                        users.setCoordinatorID(eventId);
                        users.setCoordinatorName(eventName);
                        users.setCoordinatorAddress(eventAddress);
                        users.setEvent_day(eventDay);
                        users.setEvent_date(eventDate);
                        users.setItem_type(eventType);


                        chatitem.add(users);
                        count1++;

                    }


                    //////////////////////////////////
                    int count = 0;

                    String serviceId,serviceName,serviceAddress,serviceType;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        serviceId = JO.getString("Service_ID");
                        serviceName = JO.getString("Service_Name");
                        serviceAddress = JO.getString("Address");
                        serviceType = JO.getString("Type");

                        Log.e("jdjdud", "onPostExecute: "+ serviceName);
                        Log.e("jdjdud", "onPostExecute: "+ serviceAddress);

                        ModelHorizontal users = new ModelHorizontal();
                        users.setCoordinatorID(serviceId);
                        users.setCoordinatorName(serviceName);
                        users.setCoordinatorAddress(serviceAddress);
                        users.setItem_type(serviceType);
                        chatitem.add(users);
                        count++;

                    }

                    adapterHorizontal.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }
    }


    @Override
    public void onStart() {
        super.onStart();
        chatitem.clear();

        lat = preferences.getString("lat", null);
        lng = preferences.getString("lng", null);

        Log.e("hdhddh", "onStart: "+lat+lng );
        new BackgroundTask().execute();
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button
                    new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm QUIT")
                .setMessage("Are you sure you want to quit CrossComps?")
                .setPositiveButton("Yes, QUIT NOW", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finishAffinity();
                        getActivity().finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
                    return true;

                }

                return false;
            }
        });
    }
}