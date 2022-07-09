package com.example.myforecast;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.location.LocationRequest;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myforecast.Adapters.MainAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private final String[] mPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private final int REQUEST_CODE = 1;

    private ActivityResultLauncher<Intent> mLocationServiceResult;
    private ActivityResultLauncher<Intent> mLocationPermissionResult;

    private LinearLayout mLayout;

//    TODO AFTER DECLINE OF PERMISSIONS, THE RATIONALE MENU SHOWS UP, BUT INSTEAD OF MOVING USER TO THE APP "SETTINGS" IT IS DISPLAYING THE RATIONALE ONCE AGAIN.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Prepare ActivityResultLauncher in case if permissions are missing, and was declined once.
        mLocationPermissionResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                           getDeviceLocation();
                        }
                    }
                }
        );
//      Prepare ActivityResultLaunch in case if Location service is turned off, so user can manually turn it on in the app settings.
        mLocationServiceResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        checkLocationServices();
                    }
                }
        );
//      Check if the location service is on
        checkInternetConnection();
    }



    private void checkInternetConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()){
           checkLocationServices();
        }else {
            Toast.makeText(this, "Application requires stable network connection", Toast.LENGTH_LONG).show();
        }
    }

    private void askForPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getDeviceLocation();

        } else {
            ActivityCompat.requestPermissions(this, mPermissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: Perform future tasks. Check if location service is enabled");
                getDeviceLocation();


            } else {
                Log.i(TAG, "onRequestPermissionsResult: explain why user cant get forecast (it's because he didnt granted permissions)");
                new AlertDialog.Builder(this)
                        .setTitle("Missing permissions")
                        .setMessage("In order to get proper forecast, you have to grant location permissions")
                        .setPositiveButton("Grant permissions", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveToAppSettings();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

    private void moveToAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        mLocationPermissionResult.launch(intent);
    }

    private void getDeviceLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "getDeviceLocation user selected approximate. Coarse missing.");
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getDeviceLocation user selected precise location. Both missing.");
                return;
            }
        }

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                setupFragments(location.getLatitude(), location.getLongitude());
            }
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                checkLocationServices();
            }
        };
        locationManager.requestSingleUpdate(provider, locationListener, Looper.getMainLooper());
    }

//TODO What if the network is off?
    private void checkLocationServices() {
        Log.i(TAG, "checkLocationServices: ");
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Location services are disabled");
            dialogBuilder.setMessage("Please enable location services in order to get proper forecast");
            dialogBuilder.setPositiveButton("Enable location", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mLocationServiceResult.launch(intent);
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            Dialog alert = dialogBuilder.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();
        } else {
            askForPermissions();
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