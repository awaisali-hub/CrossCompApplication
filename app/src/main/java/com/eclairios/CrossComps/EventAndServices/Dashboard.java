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
import com.eclairios.CrossComps.R;
import com.google.android.material.navigation.NavigationView;

import static com.eclairios.CrossComps.R.id.navHostFragment;

public class Dashboard extends AppCompatActivity {
    String lat,lng;

    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();



        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            lat = bundle.getString("lat");
            lng = bundle.getString("lng");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("lat",lat);
            editor.putString("lng",lng);
            editor.apply();

        }


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

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                    a = preferences.getInt("check_user_affiliate",0);

                    if(a == 1){
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


}