package com.eclairios.CrossComps.MainFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.MainScoreDashboard.FragmentParent;
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


public class ScoresFragment extends Fragment {


    ArrayList<String> values;
    FragmentParent fragmentParent;
    public static int current_card;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scores, container, false);


        try{
            WaitDialog.showDialog(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new BackgroundTask().execute();
        values = new ArrayList<>();
        fragmentParent = new FragmentParent();

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        return view;
    }

//    @Override
//    public boolean onSupportNavigateUp(){
//       // finish();
//        return true;
//    }

    @Override
    public void onResume() {

        if(fragmentParent.adapter!=null){
            if(fragmentParent.adapter.getCount()>0){
                Log.d("mmm", fragmentParent.adapter.getCount()+"");
            /*  for(int i=0; i<fragmentParent.adapter.getCount();i++){
                  Log.d("mmm","woww"+i);
                  fragmentParent.adapter.removeFrag(i);
              }*/
                fragmentParent.removeAllPages();
            }}

        try {
            String id = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("uid", "idNotFound");

            //   String aVoid = new BCTask(this).execute("http://app.itouchipay.com/api.php?get_cards=true&uid="+id).get();
            Context context=getContext();
            //  Log.d("asdf",aVoid);

            //=====================================

            //======================================
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.d("sss","onRestoreinstancestate");
//        super.onRestoreInstanceState(savedInstanceState);
//    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finishAffinity();
//        finish();
//    }


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

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8"); ;
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
            json_url = "http://edevz.com/cross_comp/get_user_score_details.php";
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            String json_string = result;



            Log.e("bcjknjkksdjcabcdsdsd", "onCreate: "+json_string );
            try {
                JSONObject jsonObject = new JSONObject(json_string);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                if(jsonArray.length() == 0) {
                    Log.e("ffffffssssdds", "onPostExecute: " );
                    fragmentParent.addPage("0","4","00 0000","0","0","_","0","_","0","_","0","_","0.0");
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount());
                }

                int count = 0;
                String Score_ID,userId,Date,Age,Meters,MetersGrade,Squats,SquatsGrade,Leg_raises,Leg_raisesGrade,PushUps,PushUpsGrade,Total_Score;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    Score_ID = JO.getString("Score_ID");
                    userId = JO.getString("User_ID");
                    Date = JO.getString("Date");
                    Age = JO.getString("Age");
                    Meters = JO.getString("Meters");
                    MetersGrade = JO.getString("Meters_Grade");
                    Squats = JO.getString("Squats");
                    SquatsGrade = JO.getString("Squats_Grade");
                    Leg_raises = JO.getString("Leg_raises");
                    Leg_raisesGrade = JO.getString("Leg_raises_Grade");
                    PushUps = JO.getString("Pushups");
                    PushUpsGrade = JO.getString("Pushup_Grade");
                    Total_Score = JO.getString("Total_Score");

                    double number = Double.parseDouble(Total_Score);
                    String score = String.format("%.1f", number);

                    Log.e("values", "onPostExecute: "+Meters);


                    fragmentParent.addPage(Score_ID,userId,Date,Age,Meters,MetersGrade,Squats,SquatsGrade,Leg_raises,Leg_raisesGrade,PushUps,PushUpsGrade,score);


                    count++;
                }

                viewPager.setCurrentItem(viewPager.getAdapter().getCount());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            WaitDialog.hideDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },500);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}