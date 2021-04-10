package com.eclairios.CrossComps.BackgroundTaskClasses;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.eclairios.CrossComps.EventAndServices.CoordinatorServicePage;
import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Challenge;
import com.eclairios.CrossComps.JavaMailAPI;
import com.eclairios.CrossComps.Teams.MyFundraisingTeamDetailActivity;
import com.eclairios.CrossComps.Teams.TeamsScoreActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BackgroundTaskForJoinTeams  extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;


    public BackgroundTaskForJoinTeams(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login information ...");

    }

    @Override
    protected String doInBackground(String... params) {

        String joinTeam_url = "http://edevz.com/cross_comp/joinNewTeams.php";
        String joinCommunityTeamUrl = "http://edevz.com/cross_comp/joinNewCommunityTeams.php";
        String unJoinTeamUrl = "http://edevz.com/cross_comp/unJoinAllTeams.php";

        String method = params[0];

        if(method.equals("joinTeams")){

            String currentUserID = params[1];
            String strTeamType = params[2];
            String strTeamParentID = params[3];
            String strTeamChildID  = params[4];


            try {
                URL url = new URL(joinTeam_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("UserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("TeamType","UTF-8") + "=" + URLEncoder.encode(strTeamType,"UTF-8") + "&"+
                        URLEncoder.encode("TeamParentID","UTF-8") + "=" + URLEncoder.encode(strTeamParentID,"UTF-8") + "&"+
                        URLEncoder.encode("TeamChildID","UTF-8") + "=" + URLEncoder.encode(strTeamChildID,"UTF-8");


                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

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

        }else if(method.equals("joinCommunityTeams")){

            String currentUserID = params[1];
            String strCountryID = params[2];
            String strStateID = params[3];
            String strCityID = params[4];
            String strPostalCodeID = params[5];
            String strCommunityTeamID = params[6];



            try {
                URL url = new URL(joinCommunityTeamUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("UserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("CountryID","UTF-8") + "=" + URLEncoder.encode(strCountryID,"UTF-8") + "&"+
                        URLEncoder.encode("StateID","UTF-8") + "=" + URLEncoder.encode(strStateID,"UTF-8") + "&"+
                        URLEncoder.encode("CityID","UTF-8") + "=" + URLEncoder.encode(strCityID,"UTF-8") + "&"+
                        URLEncoder.encode("PostalCodeID","UTF-8") + "=" + URLEncoder.encode(strPostalCodeID,"UTF-8") + "&"+
                        URLEncoder.encode("CommunityTeamID","UTF-8") + "=" + URLEncoder.encode(strCommunityTeamID,"UTF-8");


                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

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

        }else if(method.equals("unJoinTeams")){

            String currentUserID = params[1];
            String strTeamType = params[2];
            String strTeamParentID = params[3];
            String strTeamChildID  = params[4];


            try {
                URL url = new URL(unJoinTeamUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("UserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("TeamType","UTF-8") + "=" + URLEncoder.encode(strTeamType,"UTF-8") + "&"+
                        URLEncoder.encode("TeamParentID","UTF-8") + "=" + URLEncoder.encode(strTeamParentID,"UTF-8") + "&"+
                        URLEncoder.encode("TeamChildID","UTF-8") + "=" + URLEncoder.encode(strTeamChildID,"UTF-8");


                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

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

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        Log.e("changeReservation", "onPostExecute: "+result );

        try{
            if(result.equals("TeamJoined")){
                Toast.makeText(ctx, "Team Joined Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx,Dashboard.class);
                intent.putExtra("fragmentNumber",5); //for example
                ctx.startActivity(intent);
            }else if(result.equals("Error updating record")){
                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
            }else if (result.equals("CommunityTeamJoined")) {
                Toast.makeText(ctx, "Team Joined Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, Dashboard.class);
                intent.putExtra("fragmentNumber", 5); //for example
                ctx.startActivity(intent);
            }else if (result.equals("TeamUnJoined")) {
                Toast.makeText(ctx, "Team UnJoined Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, Dashboard.class);
                intent.putExtra("fragmentNumber", 5); //for example
                ctx.startActivity(intent);
            }

        }catch (Exception e){
            e.printStackTrace();

            Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}

