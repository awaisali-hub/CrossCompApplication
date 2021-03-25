package com.eclairios.CrossComps.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eclairios.CrossComps.ExtraUnusedClasses.Advanced;
import com.eclairios.CrossComps.ExtraUnusedClasses.Basic;
import com.eclairios.CrossComps.ExtraUnusedClasses.Challenge;

public class ViewPagerAdapterC extends FragmentPagerAdapter {


    public ViewPagerAdapterC(FragmentManager supportFragmentManager, Challenge challenge, int tabCount) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Basic loginTabFragment = new Basic();
                return  loginTabFragment;
            case 1:
                Advanced signupTabFragment = new Advanced();
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