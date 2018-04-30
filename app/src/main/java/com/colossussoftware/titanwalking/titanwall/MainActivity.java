package com.colossussoftware.titanwalking.titanwall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.colossussoftware.titanwalking.titanwall.fragments.DashboardFragment;
import com.colossussoftware.titanwalking.titanwall.fragments.HomeFragment;
import com.colossussoftware.titanwalking.titanwall.fragments.NotificationsFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/UbuntuCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        String fragmentName;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                fragmentName = "Home";
                break;
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                fragmentName = "Dashboard";
                break;
            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                fragmentName = "Notifications";
                break;
            default:
                fragment = new HomeFragment();
                fragmentName = "Home";
                break;
        }
        displayFragment(fragment);
        getSupportActionBar().setTitle(fragmentName);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
