package com.solarapp.filtersearch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.solarapp.filtersearch.adapters.PagerAdapter;
import com.solarapp.filtersearch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpToolBar();
        addControls();
    }

    private void addControls() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        activityMainBinding.viewPager.setAdapter(adapter);
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewPager);

    }

    private void setUpToolBar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


}
