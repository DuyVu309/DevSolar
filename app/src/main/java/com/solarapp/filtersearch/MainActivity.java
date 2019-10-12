package com.solarapp.filtersearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.solarapp.filtersearch.adapters.PagerAdapter;
import com.solarapp.filtersearch.databinding.ActivityMainBinding;
import com.solarapp.filtersearch.dialog.DialogLoading;
import com.solarapp.filtersearch.helpers.RetrofitClientInstance;
import com.solarapp.filtersearch.interfaces.GetDataService;
import com.solarapp.filtersearch.models.Response;
import com.solarapp.filtersearch.utils.Validator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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
