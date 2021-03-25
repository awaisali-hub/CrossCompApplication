package com.eclairios.CrossComps;

import android.app.AlertDialog;
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
import android.widget.Button;

import com.eclairios.CrossComps.Adapter.AdapterChallenge;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.Model.ModelChallenge;
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


public class Basic extends Fragment {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView recyclerChallenge;
    AdapterChallenge adapterChallenge;
    ArrayList<ModelChallenge> modelChallenges = new ArrayList<>() ;

    FloatingActionButton makeChallenges;
    FloatingActionButton home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);

        makeChallenges = view.findViewById(R.id.makeChallenge);
        home = view.findViewById(R.id.fab);

        recyclerChallenge = view.findViewById(R.id.recycleChallenge);
        adapterChallenge = new AdapterChallenge( modelChallenges,getContext());
        recyclerChallenge.setAdapter(adapterChallenge);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Participent.class));
            }
        });
        makeChallenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button individual,team,location;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog_for_select_user_team,null);
                builder.setCancelable(true);
                builder.setView(dialogView);

                individual = dialogView.findViewById(R.id.individual);
                team = dialogView.findViewById(R.id.team);
                location = dialogView.findViewById(R.id.location);

                AlertDialog pickFileImage = builder.create();
                pickFileImage.show();

                individual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(),Individual_challengeActivity.class);
                        intent.putExtra("challengeType","Basic");
                        startActivity(intent);
                    }
                });
                team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),Team_challengeActivity.class);
                        intent.putExtra("challengeType","Basic");
                        startActivity(intent);
                    }
                });
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),Location_challengeActivity.class);
                        intent.putExtra("challengeType","Basic");
                        startActivity(intent);
                    }
                });

            }
        });
        return view;
    }

    class BackgroundTasksLoadData extends AsyncTask<String, Void, String>
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


                String data = URLEncoder.encode("CurrentUser_ID","UTF-8") + "=" + URLEncoder.encode(CurrentUserID,"UTF-8");
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
            json_url = "http://edevz.com/cross_comp/get_basic_challenges.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            Log.e("BasicChallengerList ", "onCreate: "+json_string );


            try {

                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");



                int count = 0;

                String challengerName,challengerName1,challengerName2,challengerScore;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    challengerName1 = JO.getString("First_Name");
                    challengerName2 = JO.getString("Last_Name");
                    challengerName = challengerName1 + " "+challengerName2;
                    challengerScore = JO.getString("Score");




                    ModelChallenge modelChallenge = new ModelChallenge();
                    modelChallenge.setChallengerName(challengerName);
                    modelChallenge.setChallengerScore(challengerScore);

                    modelChallenges.add(modelChallenge);
                    count++;

                }

                Log.e("counttttt", "onPostExecute: "+count);

                adapterChallenge.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        modelChallenges.clear();
        new BackgroundTasksLoadData().execute();
    }

}