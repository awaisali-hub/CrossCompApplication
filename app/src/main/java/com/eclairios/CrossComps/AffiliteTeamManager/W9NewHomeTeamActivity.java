package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.eclairios.CrossComps.R;

public class W9NewHomeTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w9_new_home_team);
        getSupportActionBar().hide();
        WebView browser = (WebView) findViewById(R.id.webview);

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        String pdf = "https://www.irs.gov/pub/irs-pdf/fw9.pdf";
        browser.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
    }

    public void MoveToBankForHomeTeam(View view) {
        startActivity(new Intent(W9NewHomeTeamActivity.this,BankAccountNewHomeTeamActivity.class));
    }
}