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


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        Log.e("changeReservation", "onPostExecute: "+result );


    }
}

