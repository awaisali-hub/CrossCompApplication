package com.eclairios.CrossComps.Fragment;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.eclairios.CrossComps.Participent;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    public SwipeAdapter(@NonNull FragmentManager fm) {
        super(fm);


    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 : return new FirstFragment();
            case 1 : return new SecondFragment();
            case 2 : return new ThirdFragment();
        }
        return null;

    }
    @Override
    public int getCount() {
        return 3;
    }
}