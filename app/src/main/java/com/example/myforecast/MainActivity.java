package com.example.myforecast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myforecast.Adapters.MainAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final String[] mPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private final int REQUEST_CODE = 1;

    private ActivityResultLauncher<Intent> mLocationServiceResult;
    private ActivityResultLauncher<Intent> mLocationPermissionResult;

    private Button mCheckInternet;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mCheckInternet = findViewById(R.id.checkInternetConnection);
        mCheckInternet.setVisibility(View.INVISIBLE);
        mCheckInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetConnection();
            }
        });

//      Prepare ActivityResultLauncher in case if permissions are missing, and was declined once.
        mLocationPermissionResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getDeviceLocation();
                    } else {
                        askForPermissions();
                    }
                }
        );
//      Prepare ActivityResultLaunch in case if Location service is turned off, so user can manually turn it on in the app settings.
        mLocationServiceResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        askForPermissions();
                    } else {
                        checkLocationServices();
                    }
                }
        );
//      Check if the location service is on
        checkInternetConnection();
    }

    //  Check if there's stable internet connection. If yes, check the location service, and delete the button that could trigger this method once again. If not, display button that can trigger this method once again.
    private void checkInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            checkLocationServices();
            mCheckInternet.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, this.getString(R.string.noInternetConnectionToast), Toast.LENGTH_LONG).show();
            mCheckInternet.setVisibility(View.VISIBLE);
        }
    }

    /*
    *
    check if mobile device has working location service. If yes, proceed to askForPermissions method. If not, ask for turn it on.

    If not, display explanation why user should turn on the location service.
    * User can decline turning on the location service, so the app wil turn off.
    * If user will click on "enable location" button, then the app will send him to the location settings, so he can turn it on manually.*/
    private void checkLocationServices() {
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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

    /*
      Check if user granted precise/approximate location permissions. If yes, proceed to getDeviceLocation method. If not, request these permissions
    */
    private void askForPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this, mPermissions, 1);
        }
    }

    // Method for getting result from asking for location permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
//          If user granted neither coarse/approximately permission, or precise/fine permission, proceed to getDeviceLocation method.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
            } else {
            /*
             If user didn't granted permissions, display message, which will explain user why he should grant needed permission.
              If he declines granting permissions for once, at the second time, he will be moved to the application settings,
              so he can turn on needed permissions manually.
            */
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

//  Method that will send user to the app settings.
    private void moveToAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        mLocationPermissionResult.launch(intent);
    }

/*
Get user latitude and longitude, and then, pass it to the setupFragments method, that will prepare all three fragments with specified data for each one:
-current forecast
-hourly forecast
-daily forecast

 */
    private void getDeviceLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener;

//      Already covered topic of checking  permissions, but it is needed when we want to get mobile device location.
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "getDeviceLocation: coarse graned " + provider);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "getDeviceLocation: fine granted " + provider);
        }
        Log.i(TAG, "getDeviceLocation: hello?");

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i(TAG, "onLocationChanged: should work now");
                setupFragments(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Log.i(TAG, "onProviderDisabled: or not");
                checkLocationServices();
            }
        };
        locationManager.requestSingleUpdate(provider, locationListener, Looper.getMainLooper());
    }

//  setup fragments
    private void setupFragments(double latitude, double longitude) {
        Log.i(TAG, "setupFragments: sup");
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