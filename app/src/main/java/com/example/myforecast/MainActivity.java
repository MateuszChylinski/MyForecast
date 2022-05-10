package com.example.myforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.myforecast.Adapters.MainAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private double mLatitude, mLongitude;

    private final int REQUEST_CODE = 1;
    private String[] mPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermissions();
        checkLocation();
    }

    private void checkLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Location services are disabled");
            dialogBuilder.setMessage("Please enable location services in order to get proper forecast");
            dialogBuilder.setPositiveButton("Enable location", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            Dialog alert = dialogBuilder.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        setupFragments(40.730610, -73.935242);

    }
    @AfterPermissionGranted(REQUEST_CODE)
    public void askForPermissions() {

        if (EasyPermissions.hasPermissions(this, mPermissions)) {
            setupFragments(40.730610, -73.935242);
        } else {
            EasyPermissions.requestPermissions((Activity) this,
                    "In order to get proper forecast, the permissions should be granted",
                    REQUEST_CODE,
                    mPermissions);
        }
    }

    private void setupFragments(double latitude, double longitude) {
        ViewPager2 pager = findViewById(R.id.pager_test);
        pager.setAdapter(new MainAdapter(this, latitude, longitude));

        TabLayout tabLayout = findViewById(R.id.tab_test);
        new TabLayoutMediator(
                tabLayout,
                pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Current");
                                break;
                            case 1:
                                tab.setText("Hourly");
                                break;
                            case 2:
                                tab.setText("Daily");
                                break;
                        }
                    }
                }
        ).attach();
    }
}