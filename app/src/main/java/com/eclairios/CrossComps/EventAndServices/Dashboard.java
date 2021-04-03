package com.eclairios.CrossComps.EventAndServices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.eclairios.CrossComps.CrossCompAffiliate.AffiliateDashboardActivity;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.MainFragments.ChallengesFragment;
import com.eclairios.CrossComps.MainFragments.DashboardFragment;
import com.eclairios.CrossComps.MainFragments.MoreAffilitesFragment;
import com.eclairios.CrossComps.MainFragments.ScoresFragment;
import com.eclairios.CrossComps.Profile.UpdateProfileActivity;
import com.eclairios.CrossComps.R;
import com.google.android.material.navigation.NavigationView;

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

import static com.eclairios.CrossComps.R.id.navHostFragment;

public class Dashboard extends AppCompatActivity {
    String lat,lng;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        new BackgroundTasksForCheckAffiliates().execute();



//        Bundle bundle = getIntent().getExtras();
//        if(bundle!=null){
//            lat = bundle.getString("lat");
//            lng = bundle.getString("lng");
//
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("lat",lat);
//            editor.putString("lng",lng);
//            editor.apply();
//
//        }


        Log.e("sdfafdsafds", "onCreate: "+lat+lng );

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appVersion","2");
        editor.apply();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);


        NavController navController = Navigation.findNavController(this, navHostFragment);

        if(getIntent().getIntExtra("fragmentNumber",0)==1){
            navController.navigate(R.id.nav_More_fragment);
        }else if(getIntent().getIntExtra("fragmentNumber",0)==2){
            navController.navigate(R.id.nav_Profile_fragment);
        }else if(getIntent().getIntExtra("fragmentNumber",0)==3){
            navController.navigate(R.id.nav_Reservation_fragment);
        }else if(getIntent().getIntExtra("fragmentNumber",0)==4){
            navController.navigate(R.id.nav_dashboard_fragment);
        }else if(getIntent().getIntExtra("fragmentNumber",0)==5){
            navController.navigate(R.id.nav_Teams_fragment);
        }


        NavigationUI.setupWithNavController(navigationView,navController);

        final TextView textTitle = findViewById(R.id.textTitle);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();


                if (id==R.id.nav_More_fragment){


                    if(count == 0){
                        try{
                            WaitDialog.showDialog( Dashboard.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(getApplicationContext(),AffiliateDashboardActivity.class));
                    }


                }

                NavigationUI.onNavDestinationSelected(menuItem,navController);

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    class BackgroundTasksForCheckAffiliates extends AsyncTask<String, Void, String>
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


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                String currentUserID = preferences.getString("CurrentUserId", "");


                String data = URLEncoder.encode("currentUserID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")  ;
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
            json_url = "http://edevz.com/cross_comp/get_affiliate_data.php";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;

            if(json_string!=null){
                Log.e("abcd", "onCreate: "+json_string );

                try {

                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("server_response");



                    String User_ID;
                    while(count < jsonArray.length())
                    {
                        JSONObject JO = jsonArray.getJSONObject(count);

                        User_ID = JO.getString("User_ID");
                        count++;

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}