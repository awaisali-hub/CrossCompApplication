package com.eclairios.CrossComps.MainScoreDashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eclairios.CrossComps.ExtraUnusedClasses.ChallengeScreen0Activity;
import com.eclairios.CrossComps.ExtraUnusedClasses.BecomeCrossCompAffiliateActivity;
import com.eclairios.CrossComps.EventAndServices.Dashboard;
import com.eclairios.CrossComps.ExtraUnusedClasses.Participent;
import com.eclairios.CrossComps.ExtraUnusedClasses.Profile;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ExtraUnusedClasses.AllTeamCategoryActivity;
import com.eclairios.CrossComps.ExtraUnusedClasses.TrainingMainPageActivity;
import com.google.android.material.tabs.TabLayout;

public class FragmentParent  extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static View v;
    public static  ViewPagerAdapter adapter;

    private com.github.clans.fab.FloatingActionButton gotoProfile,moreBecomeAffiliate,training,challenges,teams;

    private Button reservationButton;
    private View back_fragment;
    private View next_fragment;
    private TextView CurrentUsername;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);


        gotoProfile = view.findViewById(R.id.profile);
        moreBecomeAffiliate = view.findViewById(R.id.more);
        training = view.findViewById(R.id.training);
        challenges = view.findViewById(R.id.challenges);
        teams = view.findViewById(R.id.teams);

        reservationButton = view.findViewById(R.id.reservationButton);
        back_fragment = view.findViewById(R.id.back_Fragment);
        next_fragment = view.findViewById(R.id.next_fragment);
        CurrentUsername = view.findViewById(R.id.currentUserName);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String firstName = preferences.getString("First_Name", "");
        String lastName = preferences.getString("Last_Name", "");
        CurrentUsername.setText(firstName + " "+lastName);


        back_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.arrowScroll(ViewPager.FOCUS_LEFT);
            }
        });
        next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.arrowScroll(ViewPager.FOCUS_RIGHT);
            }
        });


        String boldText = "Make a Reservation\n";
        String normalText = "for your next CrossComps";
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new RelativeSizeSpan(1.4f), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        reservationButton.setText(str);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String lat = preferences.getString("lat", "");
                String lng = preferences.getString("lng", "");
                Intent intent = new Intent(getContext(), Dashboard.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToTeams();
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChallenges();
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToTraining();
            }
        });

        moreBecomeAffiliate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BecomeAffiliate();
            }
        });
        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfile();
            }
        });

       try {
           getIDs(view);
       }finally {
           setEvents();
       }
        return view;
    }



    private void moveToChallenges() {
        Intent intent = new Intent(getContext(), ChallengeScreen0Activity.class);
        startActivity(intent);
    }

    private void moveToTeams() {
        Intent intent = new Intent(getContext(), AllTeamCategoryActivity.class);
        startActivity(intent);
    }

    private void MoveToTraining() {
        Intent intent = new Intent(getContext(), TrainingMainPageActivity.class);
        startActivity(intent);
    }

    private void BecomeAffiliate() {
        Intent intent = new Intent(getContext(), BecomeCrossCompAffiliateActivity.class);
        startActivity(intent);
    }

    public void moveToProfile() {
        Intent intent = new Intent(getContext(), Profile.class);
        startActivity(intent);
    }

    public void getIDs(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getChildFragmentManager(),getActivity(), viewPager, tabLayout);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.my_tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    int selectedTabPosition;

    public void setEvents() {


        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();

                Log.d("Selected", "Selected " + tab.getPosition());
                Participent.current_card=tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });

    }

    public void addPage(String Score_ID,String User_ID,String Date,String Age,String Meters,String MetersGrade,String Squats,String SquatsGrade,String leg_raises,String Leg_raisesGrade,String PushUps,String PushUpsGrade,String totalScore) {
        Bundle bundle = new Bundle();
        bundle.putString("Score_ID", Score_ID);
        bundle.putString("User_ID", User_ID);
        bundle.putString("Date", Date);
        bundle.putString("Age", Age);
        bundle.putString("Meters", Meters);
        bundle.putString("MetersGrade", MetersGrade);
        bundle.putString("Squats", Squats);
        bundle.putString("SquatsGrade", SquatsGrade);
        bundle.putString("leg_raises", leg_raises);
        bundle.putString("Leg_raisesGrade", Leg_raisesGrade);
        bundle.putString("PushUps", PushUps);
        bundle.putString("PushUpsGrade", PushUpsGrade);
        bundle.putString("totalScore", totalScore);

        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild);
        adapter.notifyDataSetChanged();

        try{
            if(adapter!=null) {
                if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);
                setupTabLayout();

            }}catch (NullPointerException n){}
         //  viewPager.setCurrentItem(adapter.getCount() - 1);

    }
    public void removeAllPages(){
        Log.d("aad", "removeAllPages ");
        if(adapter.getCount()>0) {
            Log.d("aad", "getCount "+adapter.getCount());
           /* for (int i = 0; i < adapter.getCount(); i++) {
                Log.d("aad", "removeFrag "+i);
                adapter.removeFrag(i);
                Log.d("aad", "notifyDataSetChanged ");
            }*/
            adapter.removeFrag(0);
            adapter.notifyDataSetChanged();
        }


    }
    public void setupTabLayout() {

        selectedTabPosition = viewPager.getCurrentItem();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
    }


}
