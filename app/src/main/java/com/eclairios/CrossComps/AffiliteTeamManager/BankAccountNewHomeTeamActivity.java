package com.eclairios.CrossComps.AffiliteTeamManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.eclairios.CrossComps.R;

public class BankAccountNewHomeTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_new_home_team);
        getSupportActionBar().hide();

        WebView browser = (WebView) findViewById(R.id.webview);

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        String pdf = "https://www.iaea.org/sites/default/files/19/04/cn-273-bank-information-form.pdf";
        browser.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
    }
}