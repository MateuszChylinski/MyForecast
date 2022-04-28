package com.example.myforecast.Adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.myforecast.Fragments.DailyForecast;
import com.example.myforecast.Fragments.HourlyForecast;
import com.example.myforecast.Fragments.TestFragment;
import com.example.myforecast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastAdapter extends FragmentStateAdapter {

    public ForecastAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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
                return new TestFragment();
        }
    }
}

    //    private String converseDate(long unixDate){
//        Date date = new Date(unixDate*1000L);
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, HH:mm");
//        return format.format(date);
//    }

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

