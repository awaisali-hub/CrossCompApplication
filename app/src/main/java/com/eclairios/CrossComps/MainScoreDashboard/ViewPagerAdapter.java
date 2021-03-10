package com.eclairios.CrossComps.MainScoreDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.eclairios.CrossComps.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    public static final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    Context context;
    ViewPager viewPager;
    TabLayout tabLayout;

    public ViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager,
                            TabLayout tabLayout) {
        super(manager);
        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add("\u2022");
    }

    public void removeFrag(int position) {
        //    removeTab(position);
        Fragment fragment = mFragmentList.get(position);
        mFragmentList.clear();
        mFragmentTitleList.clear();
        //  destroyFragmentView(viewPager, position, fragment);
    }

    public View getTabView(final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_toolbar, null);
//       TextView tabItemName = (TextView) view.findViewById(R.id.textViewTabItemName);
//        tabItemName.setText(mFragmentTitleList.get(position));
//        tabItemName.setTextColor(context.getResources().getColor(android.R.color.background_light));
//        tabItemName.setBackground(context.getResources().getDrawable(R.drawable.tab_selector));
   /*     ImageButton remove = (ImageButton) view.findViewById(R.id.imageButtonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Remove", "Remove");
                removeFrag(position);
            }
        });

        switch (mFragmentTitleList.get(position)) {
            case "Gaiduk":
                tabItemAvatar.setImageResource(R.drawable.boy);
                break;

            default:
                tabItemAvatar.setImageResource(R.drawable.boy);
                break;
        }*/

        return view;
    }

    public void destroyFragmentView(ViewGroup container, int position, Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commit();
    }

    public void removeTab(int position) {
        if (tabLayout != null) {
            if (tabLayout.getChildCount() > 0) {
                tabLayout.removeTabAt(position);
            }
        }
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
