package com.eclairios.CrossComps.ExtraUnusedClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;

import com.eclairios.CrossComps.Adapter.ViewPagerAdapter;
import com.eclairios.CrossComps.R;
import com.google.android.material.tabs.TabLayout;

import static java.lang.String.format;

public class Scores extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    float v = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.pager);
        String s = format(Scores.this.getResources().getString(R.string.string_resource_name));
        String ss = format(Scores.this.getResources().getString(R.string.string_resource_namee));
        getSpannedText(s);
        getSpannedText(ss);
        tabLayout.addTab(tabLayout.newTab().setText(getSpannedText(s)));
        tabLayout.addTab(tabLayout.newTab().setText(getSpannedText(ss)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//         tabLayout.setTranslationY(300);
//          tabLayout.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(500).start();
    }
    private Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }
}