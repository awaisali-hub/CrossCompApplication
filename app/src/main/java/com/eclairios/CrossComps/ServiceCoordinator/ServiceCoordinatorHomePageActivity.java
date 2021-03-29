package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.CrossCompAffiliate.AffiliateDashboardActivity;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.R;

public class ServiceCoordinatorHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_coordinator_home_page);
        getSupportActionBar().hide();
    }

    public void AffiliatesReservation(View view) {
        startActivity(new Intent(ServiceCoordinatorHomePageActivity.this,CoordinatorReservationsScreenActivity.class));
    }

    public void CoordinatorCheck_InScreen1_7(View view) {
        startActivity(new Intent(ServiceCoordinatorHomePageActivity.this,CoordinatorCheck_InScreen1_7Activity.class));
    }

    public void CoordinatorServices(View view) {
        startActivity(new Intent(ServiceCoordinatorHomePageActivity.this,CoordinatorServicesScreenActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ServiceCoordinatorHomePageActivity.this, Dashboard.class));

    }
}