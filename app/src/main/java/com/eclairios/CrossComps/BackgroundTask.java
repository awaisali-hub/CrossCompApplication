package com.eclairios.CrossComps;

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

import com.eclairios.CrossComps.CrossCompAffiliate.AffiliateDashboardActivity;
import com.eclairios.CrossComps.EventAndServices.CoordinatorServicePage;
import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Challenge;
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

public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Intent intent;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String latt,lngg,CurrentUserID;
    String First_Name,Last_Name,SelectedTeamGeneral_ID;

    Context ctx;
    File file;
    ProgressDialog progressDialog;
    String result;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login information ...");

    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://edevz.com/cross_comp/profile.php";
        String login_url = "http://edevz.com/cross_comp/login.php";
        String make_appointment_url = "http://edevz.com/cross_comp/appointments.php";
        String update_appointment_url = "http://edevz.com/cross_comp/appointments_update.php";
        String joinTeam_url = "http://edevz.com/cross_comp/team_user_relationship.php";
        String insertNameTeam_url = "http://edevz.com/cross_comp/team.php";
        String insertIndividualChallenge_url = "http://edevz.com/cross_comp/challenges.php";
        String insertTeamChallenge_url = "http://edevz.com/cross_comp/insertBasicTeamChallenge.php";
        String request_event_reservation = "http://edevz.com/cross_comp/set_event_reservation.php";
        String request_service_reservation = "http://edevz.com/cross_comp/set_service_reservation.php";
        String send_message_url = "http://edevz.com/cross_comp/sendMessage.php";
        String request_cancel_event_reservation_url = "http://edevz.com/cross_comp/cancel_event_reservation.php";
        String request_cancel_service_reservation_url = "http://edevz.com/cross_comp/cancel_service_reservation.php";
        String request_change_event_reservation_url = "http://edevz.com/cross_comp/change_event_reservation.php";
        String request_change_service_reservation_url = "http://edevz.com/cross_comp/change_service_reservation.php";
        String insertUserTeamSelected_url = "http://edevz.com/cross_comp/set_new_user_teams.php";
        String insertUserChurchTeam_url = "http://edevz.com/cross_comp/set_new_user_church_teams.php";
        String insert_user_score_url = "http://edevz.com/cross_comp/score.php";
        String insert_affiliate_url = "http://edevz.com/cross_comp/setCrossCompsAffiliates.php";

        String method = params[0];

        if(method.equals("register")){

            String strFirstName = params[1];
            String strLastName = params[2];
            String strPhone  = params[3];
            String strEmail = params[4];
            String strPassword  = params[5];
            String strLat = params[6];
            String strLng = params[7];
            String strAddress  = params[8];


            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("firstName","UTF-8") + "=" + URLEncoder.encode(strFirstName,"UTF-8") + "&"+
                        URLEncoder.encode("lastName","UTF-8") + "=" + URLEncoder.encode(strLastName,"UTF-8") + "&"+
                        URLEncoder.encode("phone","UTF-8") + "=" + URLEncoder.encode(strPhone,"UTF-8") + "&"+
                        URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(strEmail,"UTF-8") + "&"+
                        URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(strPassword,"UTF-8") + "&"+
                        URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(strLat,"UTF-8") + "&"+
                        URLEncoder.encode("lng","UTF-8") + "=" + URLEncoder.encode(strLng,"UTF-8") + "&"+
                        URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(strAddress,"UTF-8");


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

        }else if (method.equals("login")){
            String login_email = params[1];
            String login_pass  = params[2];

            try {
                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("login_email","UTF-8") + "=" + URLEncoder.encode(login_email,"UTF-8") + "&"+
                        URLEncoder.encode("login_pass","UTF-8") + "=" + URLEncoder.encode(login_pass,"UTF-8");
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

        }else if(method.equals("makeAppointment")){
            String weekDay = params[1];
            String currentDate  = params[2];
            String currentTime = params[3];
            String currentUserID  = params[4];
            String coordinatorID = params[5];
            String serviceID = params[6];


            Log.e("hffjhfh", "doInBackground: "+weekDay );
            Log.e("hffjhfh", "doInBackground: "+currentDate );
            Log.e("hffjhfh", "doInBackground: "+currentTime );
            Log.e("hffjhfh", "doInBackground: "+currentUserID );
            Log.e("hffjhfh", "doInBackground: "+coordinatorID );


            try {
                URL url = new URL(make_appointment_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("appointmentDay","UTF-8") + "=" + URLEncoder.encode(weekDay,"UTF-8") + "&"+
                        URLEncoder.encode("appointmentDate","UTF-8") + "=" + URLEncoder.encode(currentDate,"UTF-8")  + "&"+
                        URLEncoder.encode("appointmentTime","UTF-8") + "=" + URLEncoder.encode(currentTime,"UTF-8")  + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")  + "&"+
                        URLEncoder.encode("coordinatorId","UTF-8") + "=" + URLEncoder.encode(coordinatorID,"UTF-8") + "&"+
                        URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8");
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
        }else if(method.equals("updateAppointment")){
            String weekDay = params[1];
            String currentDate  = params[2];
            String currentTime = params[3];
            String currentUserID  = params[4];
            String coordinatorID = params[5];
            String serviceID = params[6];


            Log.e("hffjhfh", "doInBackground: "+weekDay );
            Log.e("hffjhfh", "doInBackground: "+currentDate );
            Log.e("hffjhfh", "doInBackground: "+currentTime );
            Log.e("hffjhfh", "doInBackground: "+currentUserID );
            Log.e("hffjhfh", "doInBackground: "+coordinatorID );


            try {
                URL url = new URL(update_appointment_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("appointmentDay","UTF-8") + "=" + URLEncoder.encode(weekDay,"UTF-8") + "&"+
                        URLEncoder.encode("appointmentDate","UTF-8") + "=" + URLEncoder.encode(currentDate,"UTF-8")  + "&"+
                        URLEncoder.encode("appointmentTime","UTF-8") + "=" + URLEncoder.encode(currentTime,"UTF-8")  + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")  + "&"+
                        URLEncoder.encode("coordinatorId","UTF-8") + "=" + URLEncoder.encode(coordinatorID,"UTF-8")  + "&"+
                        URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8");
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
        }else if (method.equals("joinTeam")){
            String userID = params[1];
            String teamID  = params[2];
            String teamScore = params[3];

            Log.e("jointeamscore", "doInBackground: "+ teamScore);
            String CurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            try {
                URL url = new URL(joinTeam_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userID,"UTF-8") + "&"+
                        URLEncoder.encode("teamId","UTF-8") + "=" + URLEncoder.encode(teamID,"UTF-8") + "&" +
                        URLEncoder.encode("teamScore","UTF-8") + "=" + URLEncoder.encode(teamScore,"UTF-8") + "&"+
                        URLEncoder.encode("CurrentDate","UTF-8") + "=" + URLEncoder.encode(CurrentDate,"UTF-8");
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

        }else if (method.equals("insertNewTeam")){
            String teamName = params[1];
            String teamAddress  = params[2];
            String serviceID = params[3];
            String CurrentUserID = params[4];

            String CurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            try {
                URL url = new URL(insertNameTeam_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("team_name","UTF-8") + "=" + URLEncoder.encode(teamName,"UTF-8") + "&"+
                        URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(teamAddress,"UTF-8") + "&"+
                        URLEncoder.encode("serviceID","UTF-8") + "=" + URLEncoder.encode(serviceID,"UTF-8") + "&"+
                        URLEncoder.encode("CurrentUserID","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8")+ "&"+
                        URLEncoder.encode("Date","UTF-8") + "=" + URLEncoder.encode(CurrentDate,"UTF-8");
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

        }else if(method.equals("individualUserChallenge")){
            String CurrentUserID = params[1];
            String ChallengerID  = params[2];
            String challengeType = params[3];
            String challengeName  = params[4];
            String userScore = params[5];
            String gender = params[6];
            String age = params[7];
            String address = params[8];
            String type = params[9];



            try {
                URL url = new URL(insertIndividualChallenge_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("challengerID","UTF-8") + "=" + URLEncoder.encode(ChallengerID,"UTF-8")  + "&"+
                        URLEncoder.encode("challengeType","UTF-8") + "=" + URLEncoder.encode(challengeType,"UTF-8")  + "&"+
                        URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(challengeName,"UTF-8")  + "&"+
                        URLEncoder.encode("score","UTF-8") + "=" + URLEncoder.encode(userScore,"UTF-8")  + "&"+
                        URLEncoder.encode("gender","UTF-8") + "=" + URLEncoder.encode(gender,"UTF-8")   + "&"+
                        URLEncoder.encode("age","UTF-8") + "=" + URLEncoder.encode(age,"UTF-8")   + "&"+
                        URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(address,"UTF-8") + "&"+
                        URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode(type,"UTF-8");
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
        }else if(method.equals("basicTeamChallenge")){
            String CurrentUserID = params[1];
            String TeamID  = params[2];
            String challengeType = params[3];
            String challengeName  = params[4];
            String userScore = params[5];
            String address = params[6];



            try {
                URL url = new URL(insertTeamChallenge_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("teamID","UTF-8") + "=" + URLEncoder.encode(TeamID,"UTF-8")  + "&"+
                        URLEncoder.encode("challengeType","UTF-8") + "=" + URLEncoder.encode(challengeType,"UTF-8")  + "&"+
                        URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(challengeName,"UTF-8")  + "&"+
                        URLEncoder.encode("score","UTF-8") + "=" + URLEncoder.encode(userScore,"UTF-8")  + "&"+
                        URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(address,"UTF-8");
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
        }else if(method.equals("Event_Reservation")){

            String eventTimeID = params[1];
            String userId = params[2];



            try {
                URL url = new URL(request_event_reservation);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("eventTimeID","UTF-8") + "=" + URLEncoder.encode(eventTimeID,"UTF-8") + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userId,"UTF-8");


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

        }else if(method.equals("Service_Reservation")){

            String Facility_ID = params[1];
            String serviceDATE = params[2];
            String currentUserID = params[3];


            try {
                URL url = new URL(request_service_reservation);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));


                Log.e("logeeeees", "doInBackground: "+Facility_ID);
                Log.e("logeeeees", "doInBackground: "+"2021-03-01");
                Log.e("logeeeees", "doInBackground: "+currentUserID);


                String data = URLEncoder.encode("serviceFacilityID","UTF-8") + "=" + URLEncoder.encode(Facility_ID,"UTF-8") + "&"+
                        URLEncoder.encode("reservationDate","UTF-8") + "=" + URLEncoder.encode(serviceDATE,"UTF-8")  + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8");


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

        }else if(method.equals("SendUserMessage")){

            String currentUserID = params[1];
            String message = params[2];
//            String receiverID = params[3];

            Log.e("messaggeessss", "doInBackground: "+message );



            try {
                URL url = new URL(send_message_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String data = URLEncoder.encode("userID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("message","UTF-8") + "=" + URLEncoder.encode(message,"UTF-8") + "&"+
                        URLEncoder.encode("receiverID","UTF-8") + "=" + URLEncoder.encode("1","UTF-8");


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

        }else if (method.equals("Cancel_event_reservation")){
            String eventTimeID = params[1];
            String userId  = params[2];

            try {
                URL url = new URL(request_cancel_event_reservation_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("eventTimeID","UTF-8") + "=" + URLEncoder.encode(eventTimeID,"UTF-8") + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userId,"UTF-8");
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

        }else if (method.equals("Cancel_service_reservation")){
            String serviceFacilityID = params[1];
            String reservationDate  = params[2];
            String userId = params[3];

            try {
                URL url = new URL(request_cancel_service_reservation_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("serviceFacilityID","UTF-8") + "=" + URLEncoder.encode(serviceFacilityID,"UTF-8") + "&"+
                        URLEncoder.encode("reservationDate","UTF-8") + "=" + URLEncoder.encode(reservationDate,"UTF-8") + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userId,"UTF-8");
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

        }else if (method.equals("Change_event_reservation")){
            String eventTimeID = params[1];
            String userId  = params[2];
            String E_Reservation_ID = params[3];
            Log.e("testedddddd", "doInBackground: "+eventTimeID );

            try {
                URL url = new URL(request_change_event_reservation_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("eventTimeID","UTF-8") + "=" + URLEncoder.encode(eventTimeID,"UTF-8") + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userId,"UTF-8") + "&"+
                        URLEncoder.encode("E_Reservation_ID","UTF-8") + "=" + URLEncoder.encode(E_Reservation_ID,"UTF-8");
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

        }else if (method.equals("Change_Service_Reservation")){
            String serviceFacilityID = params[1];
            String reservationDate  = params[2];
            String userId = params[3];
            String S_Reservation_ID = params[4];

            try {
                URL url = new URL(request_change_service_reservation_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("serviceFacilityID","UTF-8") + "=" + URLEncoder.encode(serviceFacilityID,"UTF-8") + "&"+
                        URLEncoder.encode("reservationDate","UTF-8") + "=" + URLEncoder.encode(reservationDate,"UTF-8") + "&"+
                        URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(userId,"UTF-8") + "&"+
                        URLEncoder.encode("S_Reservation_ID","UTF-8") + "=" + URLEncoder.encode(S_Reservation_ID,"UTF-8");
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

        }else if (method.equals("insertUserTeamSelected_url")){

            String SelectedTeamGeneralID = params[1];
            String currentUserID  = params[2];
            String SelectedPostalCode = params[3];
            String str_city_team = params[4];
            String str_county_team  = params[5];
            String str_conference_team = params[6];
            String str_state_team = params[7];
            String str_union_team = params[8];
            String str_country_team = params[9];
            String str_division_team  = params[10];
            String str_world_team = params[11];


            try {
                URL url = new URL(insertUserTeamSelected_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("SelectedTeamGeneralID","UTF-8") + "=" + URLEncoder.encode(SelectedTeamGeneralID,"UTF-8") + "&"+
                        URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("SelectedPostalCode","UTF-8") + "=" + URLEncoder.encode(SelectedPostalCode,"UTF-8") + "&"+
                        URLEncoder.encode("str_city_team","UTF-8") + "=" + URLEncoder.encode(str_city_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_county_team","UTF-8") + "=" + URLEncoder.encode(str_county_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_conference_team","UTF-8") + "=" + URLEncoder.encode(str_conference_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_state_team","UTF-8") + "=" + URLEncoder.encode(str_state_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_union_team","UTF-8") + "=" + URLEncoder.encode(str_union_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_country_team","UTF-8") + "=" + URLEncoder.encode(str_country_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_division_team","UTF-8") + "=" + URLEncoder.encode(str_division_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_world_team","UTF-8") + "=" + URLEncoder.encode(str_world_team,"UTF-8");
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

        }else if (method.equals("insertUserChurchTeamSelected")){

            String SelectedTeamGeneralID = params[1];
            String currentUserID  = params[2];
            String str_faith_team = params[3];
            String str_division_team  = params[4];
            String str_union_team = params[5];
            String str_conference_team = params[6];



            try {
                URL url = new URL(insertUserChurchTeam_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("SelectedTeamGeneralID","UTF-8") + "=" + URLEncoder.encode(SelectedTeamGeneralID,"UTF-8") + "&"+
                        URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8") + "&"+
                        URLEncoder.encode("str_faith_team","UTF-8") + "=" + URLEncoder.encode(str_faith_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_division_team","UTF-8") + "=" + URLEncoder.encode(str_division_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_union_team","UTF-8") + "=" + URLEncoder.encode(str_union_team,"UTF-8") + "&"+
                        URLEncoder.encode("str_conference_team","UTF-8") + "=" + URLEncoder.encode(str_conference_team,"UTF-8");
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
        }else if (method.equals("judgeInsertUserScore")){


            String UserID  = params[1];
            String date = params[2];
            String walk_meters = params[3];
            String walk_meters_grade = params[4];
            String squats = params[5];
            String squats_grade = params[6];
            String leg_raises = params[7];
            String leg_raises_grade = params[8];
            String push_ups = params[9];
            String push_ups_grade = params[10];
            String total_score = params[11];




            try {
                URL url = new URL(insert_user_score_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("userId","UTF-8") + "=" + URLEncoder.encode(UserID,"UTF-8") + "&"+
                        URLEncoder.encode("date","UTF-8") + "=" + URLEncoder.encode(date,"UTF-8") + "&"+
                        URLEncoder.encode("meters","UTF-8") + "=" + URLEncoder.encode(walk_meters,"UTF-8") + "&"+
                        URLEncoder.encode("meters_grade","UTF-8") + "=" + URLEncoder.encode(walk_meters_grade,"UTF-8") + "&"+
                        URLEncoder.encode("squats","UTF-8") + "=" + URLEncoder.encode(squats,"UTF-8") + "&"+
                        URLEncoder.encode("squats_grade","UTF-8") + "=" + URLEncoder.encode(squats_grade,"UTF-8") + "&"+
                        URLEncoder.encode("leg_raises","UTF-8") + "=" + URLEncoder.encode(leg_raises,"UTF-8") + "&"+
                        URLEncoder.encode("leg_raises_grade","UTF-8") + "=" + URLEncoder.encode(leg_raises_grade,"UTF-8") + "&"+
                        URLEncoder.encode("push_ups","UTF-8") + "=" + URLEncoder.encode(push_ups,"UTF-8") + "&"+
                        URLEncoder.encode("push_ups_grade","UTF-8") + "=" + URLEncoder.encode(push_ups_grade,"UTF-8") + "&"+
                        URLEncoder.encode("total_score","UTF-8") + "=" + URLEncoder.encode(total_score,"UTF-8");
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
        }else if (method.equals("AffiliatesRegister")){


            String UserID  = params[1];
            String firstName = params[2];
            String lastName = params[3];
            String date = params[4];
            String lat = params[5];
            String lng = params[6];
            String affiliateType = params[7];
            String balance = params[8];
            String status = params[9];


            try {
                URL url = new URL(insert_affiliate_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("UserID","UTF-8") + "=" + URLEncoder.encode(UserID,"UTF-8") + "&"+
                        URLEncoder.encode("firstName","UTF-8") + "=" + URLEncoder.encode(firstName,"UTF-8") + "&"+
                        URLEncoder.encode("lastName","UTF-8") + "=" + URLEncoder.encode(lastName,"UTF-8") + "&"+
                        URLEncoder.encode("date","UTF-8") + "=" + URLEncoder.encode(date,"UTF-8") + "&"+
                        URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(lat,"UTF-8") + "&"+
                        URLEncoder.encode("lng","UTF-8") + "=" + URLEncoder.encode(lng,"UTF-8") + "&"+
                        URLEncoder.encode("affiliateType","UTF-8") + "=" + URLEncoder.encode(affiliateType,"UTF-8") + "&"+
                        URLEncoder.encode("balance","UTF-8") + "=" + URLEncoder.encode(balance,"UTF-8") + "&"+
                        URLEncoder.encode("status","UTF-8") + "=" + URLEncoder.encode(status,"UTF-8");
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
            if(result.contains("Profile Registration Success")){


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, "Registration Success", Toast.LENGTH_SHORT).show();
                        Log.e("jdsusdjdjdu", "run: "+result);

                        try {

                            jsonObject = new JSONObject(result);
                            jsonArray = jsonObject.getJSONArray("server_response");


                            int count = 0;


                            while(count < jsonArray.length())
                            {
                                JSONObject JO = jsonArray.getJSONObject(count);
                                latt = JO.getString("lat");
                                lngg = JO.getString("lon");
                                CurrentUserID = JO.getString("User_ID");
                                First_Name = JO.getString("First_Name");
                                Last_Name = JO.getString("Last_Name");

                                Log.e("First_Name", "run: "+First_Name);
                                Log.e("First_Name", "run: "+Last_Name );

                                count++;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("CurrentUserId",CurrentUserID);
                        editor.putString("First_Name",First_Name);
                        editor.putString("Last_Name",Last_Name);
                        editor.putString("lat",latt);
                        editor.putString("lng",lngg);
                        editor.apply();

                        Log.e("sddududu", "run: "+latt+"\n"+lngg );
                        intent = new Intent(ctx, Dashboard.class);
                        intent.putExtra("lat",latt);
                        intent.putExtra("lng",lngg);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ctx.startActivity(intent);

                    }
                }, 3000);

            }else if(result.equals("You can't Register with this Phone Number and Email Address")){

                Toast.makeText(ctx, "You can't Register with this Phone Number and Email Address", Toast.LENGTH_SHORT).show();
            }
            else if(result.contains("Profile Registration UnSuccess")){

                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();

            } else if(result.contains("Login success")){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("jdsusdjdjdu", "run: "+result);

                                try {

                                    jsonObject = new JSONObject(result);
                                    jsonArray = jsonObject.getJSONArray("server_response");


                                    int count = 0;


                                    while(count < jsonArray.length())
                                    {
                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        latt = JO.getString("lat");
                                        lngg = JO.getString("lon");
                                        CurrentUserID = JO.getString("User_ID");
                                        First_Name = JO.getString("First_Name");
                                        Last_Name = JO.getString("Last_Name");


                                        Log.e("First_Name", "run: "+First_Name);
                                        Log.e("First_Name", "run: "+Last_Name );

                                        count++;

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("CurrentUserId",CurrentUserID);
                                editor.putString("First_Name",First_Name);
                                editor.putString("Last_Name",Last_Name);
                                editor.putString("lat",latt);
                                editor.putString("lng",lngg);
                                editor.apply();



                                Log.e("sddududu", "run: "+latt+"\n"+lngg );
                                intent = new Intent(ctx,Dashboard.class);
                                intent.putExtra("lat",latt);
                                intent.putExtra("lng",lngg);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                ctx.startActivity(intent);
                            }
                        }, 1500);

            }else if(result.contains("No record found...")){

                        Toast.makeText(ctx, "No record found...", Toast.LENGTH_SHORT).show();

            }else if(result.contains("Login failed...")){
                Toast.makeText(ctx, "Login failed...", Toast.LENGTH_SHORT).show();

            }else if(result.contains("Appointment Registration Success")){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(ctx, CoordinatorServicePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ctx.startActivity(intent);
                        Toast.makeText(ctx, "Appointment Request Send Successful", Toast.LENGTH_SHORT).show();

                    }
                }, 1500);



            }else if(result.contains("Appointment Registration UnSuccess")){
                Toast.makeText(ctx, "Appointment Request Not Send", Toast.LENGTH_SHORT).show();
            }else if(result.contains("You already send Request for this service")){
                Toast.makeText(ctx, "You already send Request for this service", Toast.LENGTH_SHORT).show();
            } else if (result.equals("RescheduleAppointment Success")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(ctx, CrossComp.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ctx.startActivity(intent);
                        Toast.makeText(ctx, "Reschedule Appointment Request Send Successful", Toast.LENGTH_SHORT).show();

                    }
                }, 1500);
            }else if(result.equals("RescheduleAppointment UnSuccess")){
                Toast.makeText(ctx, "Reschedule Appointment Request Not Send", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Team User Registration Success")){
                Toast.makeText(ctx, "Join Team Successful", Toast.LENGTH_SHORT).show();
            }else if(result.equals("You already member of this team")){
                Toast.makeText(ctx, "You already member of this team", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("Team User Registration UnSuccess")){
                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Team Registration Success")){
                Toast.makeText(ctx, "Team Inserted", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Team Registration UnSuccess")){
                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
            }else if(result.equals("This Team is Already Added")){
                Toast.makeText(ctx, "This Team is Already Added", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Challenge Individual Registration Success")){
                Toast.makeText(ctx, "Your challenge Send", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, Challenge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               ctx.startActivity(intent);
            }else if(result.equals("Challenge Individual Registration UnSuccess")){
                Toast.makeText(ctx, "Challenge not send!!! Please Try Again", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Challenge basicTeam Registration Success")){
                Toast.makeText(ctx, "Your challenge Send", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx,Challenge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(intent);
            }else if(result.equals("Challenge basicTeam Registration UnSuccess")){
                Toast.makeText(ctx, "Challenge not send!!! Please Try Again", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Event Reservation Success")){
                Toast.makeText(ctx, "Event Reservation Success", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Event Reservation UnSuccess")){
                Toast.makeText(ctx, "Event Reservation UnSuccess", Toast.LENGTH_SHORT).show();
            }else if(result.equals("You already send Request for this Event")){
                Toast.makeText(ctx, "You already reserve this Event", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Message Send")){
                Toast.makeText(ctx, "Message Send", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Message not Send")){
                Toast.makeText(ctx, "Message not Send", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Service Reservation Success")){
                Toast.makeText(ctx, "Service Reservation Success", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Service Reservation UnSuccess")){
                Toast.makeText(ctx, "Service Reservation UnSuccess", Toast.LENGTH_SHORT).show();
            }else if(result.equals("You already send Request for this Service")){
                Toast.makeText(ctx, "You already send Request for this Service", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Event Reservation Cancel")){

                Toast.makeText(ctx, "Event Reservation Cancel", Toast.LENGTH_LONG).show();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                Intent intent = new Intent(ctx,Dashboard.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(intent);
            }else if(result.equals("Event Reservation Cancel Fail")){
                Toast.makeText(ctx, "Event Reservation Cancel Fail", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Service Reservation Cancel")){

                Toast.makeText(ctx, "Service Reservation Cancel", Toast.LENGTH_LONG).show();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                Intent intent = new Intent(ctx,Dashboard.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(intent);
            }else if(result.equals("Service Reservation Cancel Fail")){
                Toast.makeText(ctx, "Service Reservation Cancel Fail", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Change Event Reservation")){
                Toast.makeText(ctx, "Event Reservation Changed", Toast.LENGTH_SHORT).show();

            }else if(result.equals("Change Event Reservation Fail")){
                Toast.makeText(ctx, "Event Reservation Changed Fail", Toast.LENGTH_SHORT).show();
            }else if(result.equals("You already Reserve this Event Time")){
                Toast.makeText(ctx, "You already Reserve this Event Time", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Change Service Reservation")){
                Toast.makeText(ctx, "Service Reservation Changed", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Change Service Reservation Fail")){
                Toast.makeText(ctx, "Service Reservation Changed Fail", Toast.LENGTH_SHORT).show();
            }else if(result.equals("You already Reserve this Service")){
                Toast.makeText(ctx, "You already Reserve this Service", Toast.LENGTH_SHORT).show();
            }
            else if(result.contains("User new Team Success")){
                Toast.makeText(ctx, "Team inserted", Toast.LENGTH_SHORT).show();

                if (result.equals("User new Team Success3")) {
                    ctx.startActivity(new Intent(ctx, MyFundraisingTeamDetailActivity.class));
                } else {
                    ctx.startActivity(new Intent(ctx, TeamsScoreActivity.class));
                }
            }else if(result.equals("User new Team UnSuccess")){
                Toast.makeText(ctx, "Team not inserted", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("User new church Team Success")){
                Toast.makeText(ctx, "Team inserted", Toast.LENGTH_SHORT).show();
                ctx.startActivity(new Intent(ctx, TeamsScoreActivity.class));
            }else if(result.equals("User new church Team UnSuccess")){
                Toast.makeText(ctx, "Team not inserted", Toast.LENGTH_SHORT).show();
            }else if(result.equals("Score Insert Success")){
                Toast.makeText(ctx, "Score inserted", Toast.LENGTH_SHORT).show();
             //   ctx.startActivity(new Intent(ctx, JudgeHomePageParticipantRegistrationActivity.class));
            }else if(result.equals("Score Insert UnSuccess")){
                Toast.makeText(ctx, "Score not inserted", Toast.LENGTH_SHORT).show();
            }else if(result.equals("New Affiliate Success")){
                Toast.makeText(ctx, "Now You are CrossComp Affiliate ", Toast.LENGTH_SHORT).show();
            //    ctx.startActivity(new Intent(ctx, AffiliateDashboardActivity.class));

                Intent intent = new Intent(ctx,Dashboard.class);
                intent.putExtra("fragmentNumber",1); //for example
                ctx.startActivity(intent);

            }else if(result.equals("New Affiliate UnSuccess")){
                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                    Toast.makeText(ctx, "Something went Wrong !!! Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        }
