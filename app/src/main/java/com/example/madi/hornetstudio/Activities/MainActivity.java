package com.example.madi.hornetstudio.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.madi.hornetstudio.Fragments.DashboardFragment;
import com.example.madi.hornetstudio.Fragments.OrdersFragment;
import com.example.madi.hornetstudio.Fragments.SearchFragment;
import com.example.madi.hornetstudio.R;

public class MainActivity extends AppCompatActivity {

    String TAG = "___";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new SearchFragment();
                    Log.d(TAG, "onNavigationItemSelected: " + fragment);
                    break;
                case R.id.navigation_dashboard:
                    fragment = new DashboardFragment();
                    Log.d(TAG, "onNavigationItmSelected: " + fragment);
                    break;
                case R.id.navigation_notifications:
                    fragment = new OrdersFragment();
                    Log.d(TAG, "onNavigationItemSelected: " + fragment);
                    break;
            }

            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                        beginTransaction();
                Log.d(TAG, "Transaction have begun");
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMainPage();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setMainPage() {
        Fragment fragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                beginTransaction();
        Log.d(TAG, "Transaction have begun");
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


}
