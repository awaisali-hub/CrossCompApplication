package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.eclairios.CrossComps.Adapter.ViewPagerAdapterC;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.google.android.material.tabs.TabLayout;

public class Challenge extends AppCompatActivity {

    ViewPager viewPagerr;
    TabLayout tabLayoutt;
    String serviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);



        tabLayoutt = findViewById(R.id.tabb);
        viewPagerr = findViewById(R.id.pagerr);

        tabLayoutt.addTab(tabLayoutt.newTab().setText("Basic"));
        tabLayoutt.addTab(tabLayoutt.newTab().setText("Advance"));
        tabLayoutt.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapterC adapterr = new ViewPagerAdapterC(getSupportFragmentManager(),this,tabLayoutt.getTabCount());
        viewPagerr.setAdapter(adapterr);

        viewPagerr.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutt));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Challenge.this, Participent.class));
    }
}