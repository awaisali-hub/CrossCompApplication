package com.eclairios.CrossComps.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eclairios.CrossComps.Graph;
import com.eclairios.CrossComps.Scores;
import com.eclairios.CrossComps.Table;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm, Scores scores, int tabCount) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Table loginTabFragment = new Table();
                return  loginTabFragment;
            case 1:
                Graph signupTabFragment = new Graph();
                return signupTabFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2; //three fragments
    }
}