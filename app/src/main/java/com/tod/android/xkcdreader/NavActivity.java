package com.tod.android.xkcdreader;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

import java.util.Calendar;

/**
 * Created by Margolin on 7/9/2014.
 */
public class NavActivity extends Activity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static long back_pressed;
    private FragmentManager fragmentManager;
    public static final String BROADCAST = "com.tod.android.xkcdreader.android.action.broadcast";
    private static Boolean hidemenu;
        /**
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        private NavigationDrawerFragment mNavigationDrawerFragment;
        private static CharSequence mTitle;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String theme = sharedPreferences.getString("themepref", "LIGHT");
            Boolean notifs = sharedPreferences.getBoolean("notifs", true);
            if (theme.contains("DARK")){
                setTheme(R.style.DarkTheme);
            }else{
                setTheme(R.style.AppTheme);
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nav);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();
            fragmentManager = getFragmentManager();
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
            if (notifs) {
                boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                        new Intent(BROADCAST),
                        PendingIntent.FLAG_NO_CREATE) != null);

                if (alarmUp){
                    Log.d("Alarm Info", "Alarm is already active");
                }else {
                    IntentFilter intentFilter = new IntentFilter(BROADCAST);
                    Receiver myreceiver = new Receiver();
                    registerReceiver(myreceiver , intentFilter);
                    Intent intent = new Intent(BROADCAST);
                    PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alrm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                    AlarmManager alrm1 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                    AlarmManager alrm2 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.HOUR_OF_DAY,24);
                    Calendar moncalendar = Calendar.getInstance();
                    moncalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    Calendar wedcalendar = Calendar.getInstance();
                    wedcalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    Calendar fricalendar = Calendar.getInstance();
                    fricalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    if (moncalendar.getTimeInMillis() < today.getTimeInMillis()) {
                        moncalendar.add(Calendar.DATE, 7);
                        Log.d("timemon","added 7 days");
                    }
                    if (wedcalendar.getTimeInMillis() < today.getTimeInMillis()) {
                        wedcalendar.add(Calendar.DATE, 7);
                        Log.d("timewed","added 7 days");
                    }
                    if (fricalendar.getTimeInMillis() < today.getTimeInMillis()) {
                        fricalendar.add(Calendar.DATE, 7);
                        Log.d("timefri","added 7 days");
                    }
                    alrm.setRepeating(AlarmManager.RTC_WAKEUP, moncalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, operation);
                    alrm1.setRepeating(AlarmManager.RTC_WAKEUP, wedcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, operation);
                    alrm2.setRepeating(AlarmManager.RTC_WAKEUP, fricalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, operation);
                    Log.d("alarm status","started");
                }
                }
            }
        @Override
        public void onNavigationDrawerItemSelected(int position) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }

    void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                // Creating a fragment object
                ReaderFragment aFragment = new ReaderFragment();
                Bundle data = new Bundle();

                // Setting the index of the currently selected item of mDrawerList
                data.putString("LINK", "http://xkcd.com");
                // Setting the position to the fragment
                aFragment.setArguments(data);
                // Creating a fragment transaction

                FragmentTransaction ft = fragmentManager.beginTransaction();
                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, aFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                // Committing the transaction
                ft.commit();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                BlagLoaderFragment bFragment = new BlagLoaderFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", posit
					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();
                // Getting reference to the FragmentManager
                // Adding a fragment to the fragment transaction
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, bFragment);
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                // Committing the transaction
                ft.commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                WhatIfLoaderFragment cFragment = new WhatIfLoaderFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", posit
					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();
                // Getting reference to the FragmentManager

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, cFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                // Committing the transaction
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                ft.commit();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                SettingsFragment dFragment = new SettingsFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", posit
					// Setting the position to the fragment
					rFragment.setArguments(data);*/

                // Getting reference to the FragmentManager
                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();
                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, dFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                // Committing the transaction
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                ft.commit();

                break;
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            if (mTitle== getString(R.string.title_section1)){
                getMenuInflater().inflate(R.menu.main,menu);
            }
            if (hidemenu){
                if (menu.hasVisibleItems()) {
                    menu.clear();
                }
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    public static void setlistfrag(Boolean enabled){
        hidemenu = enabled;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
            NavActivity.setlistfrag(false);
            invalidateOptionsMenu();
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else
                Toast.makeText(getBaseContext(), "Press back again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            // or just go back to main activity
        }
    }
}

