package com.example.myforecast.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myforecast.Fragments.DailyForecast;
import com.example.myforecast.Fragments.HourlyForecast;
import com.example.myforecast.Fragments.TestFragment;

public class MainAdapter extends FragmentStateAdapter {
    private double mLatitude, mLongitude;

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
            case 0:
                return new HourlyForecast();
            case 1:
                return new DailyForecast();
            default:
                return new TestFragment(mLatitude, mLongitude);
        }
    }
}

//    public class ForecastViewHolder extends RecyclerView.ViewHolder {
//        TextView datetime, temperature, pressure, humidity;
//        ImageView forecastIcon;
//
//        public ForecastViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            this.datetime = (TextView) itemView.findViewById(R.id.five_hours_datetime);
//            this.temperature = (TextView) itemView.findViewById(R.id.five_hours_temperature);
//            this.pressure = (TextView) itemView.findViewById(R.id.five_hours_pressure);
//            this.humidity = (TextView) itemView.findViewById(R.id.five_hours_humidity);
//            this.forecastIcon = (ImageView) itemView.findViewById(R.id.five_hours_icon);
//        }
//    }

