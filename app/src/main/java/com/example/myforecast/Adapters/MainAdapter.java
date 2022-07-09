package com.example.myforecast.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myforecast.Fragments.DailyForecast;
import com.example.myforecast.Fragments.HourlyForecast;
import com.example.myforecast.Fragments.CurrentForecast;

public class MainAdapter extends FragmentStateAdapter {
    private final double mLatitude, mLongitude;

    public MainAdapter(@NonNull FragmentActivity fragmentActivity, double latitude, double longitude) {
        super(fragmentActivity);
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return HourlyForecast.newHourlyInstance(mLatitude, mLongitude);
            case 2:
                return DailyForecast.newDailyInstance(mLatitude, mLongitude);
//                Return current forecast, as a default.
            default:
                return CurrentForecast.newCurrentInstance(mLatitude, mLongitude);
        }
    }
}
