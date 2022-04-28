package com.example.myforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myforecast.Adapter.ForecastAdapter;
import com.example.myforecast.Fragments.DailyForecast;
import com.example.myforecast.Fragments.HourlyForecast;
import com.example.myforecast.Fragments.TestFragment;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.ViewModel.ForecastViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int request_code = 1;
    private double mLatitude, mLongitude;

    List<ForecastModel> mForecastList = new ArrayList<ForecastModel>();
    ForecastAdapter mAdapter;
    RecyclerView mRecyclerView;
    ForecastViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Three hour forecast");

        setupData(0, 0);
    }

    private void setupData(double latitude, double longitude) {


        ViewPager2 pager = findViewById(R.id.pager_test);
        mAdapter = new ForecastAdapter(this);
        pager.setAdapter(mAdapter);


        TabLayout tabLayout = findViewById(R.id.tab_test);
        new TabLayoutMediator(
                tabLayout,
                pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText("test");
                    }
                }
        ).attach();
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this)
                    .setTitle("Location permission needed")
                    .setMessage("In order to get accurate data, this application need to have your geographical coordinates")
                    .setPositiveButton("Grant permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, request_code);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, request_code);
            getUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            getUserLocation();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void getUserLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissions();
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    Log.i(TAG, "onSuccess: " + mLatitude + " " + mLongitude);
                    setupData(mLatitude, mLongitude);
                }
            }
        });
    }



}
//    private void setupData(double latitude, double longitude) {
//        ViewPager2 pager = findViewById(R.id.pager_test);
//        ForecastAdapter adapter = new ForecastAdapter(this);
//
//        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
//        mViewModel.getRecentForecast(latitude, longitude).observe(this, new Observer<List<ForecastModel>>() {
//            @Override
//            public void onChanged(List<ForecastModel> models) {
//                mForecastList.addAll(models);
//                pager.setAdapter(adapter);
//            }
//        });
//    }
//}
