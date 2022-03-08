package com.example.myforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ForecastModel> mForecastList = new ArrayList<ForecastModel>();
    ForecastAdapter mAdapter;
    RecyclerView mRecyclerView;
    ForecastViewModel mViewModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_test);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.getRecentForecast().observe(this, new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> models) {
                mForecastList.addAll(models);

                mAdapter = new ForecastAdapter(mForecastList);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}
