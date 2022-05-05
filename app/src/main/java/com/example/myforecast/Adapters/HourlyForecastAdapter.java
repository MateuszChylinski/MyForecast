package com.example.myforecast.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {
    private List<ForecastModel> mData = new ArrayList<>();

    public HourlyForecastAdapter(List<ForecastModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mDateTime.setText(converseDate(mData.get(0).getHourlyForecast().get(position).getDateTime()));
        holder.mTemperature.setText(String.format("Temperature: %.0f\u2103", mData.get(0).getHourlyForecast().get(position).getTemperature()));
        holder.mPressure.setText(String.format("Pressure: %d hPa ", mData.get(0).getHourlyForecast().get(position).getPressure()));
        holder.mHumidity.setText(String.format("Humidity: %d%%", mData.get(0).getHourlyForecast().get(position).getHumidity()));
        holder.mWindSpeed.setText(String.format("Wind speed: %.2f km/h", mData.get(0).getHourlyForecast().get(position).getWindSpeed()));

    }

    @Override
    public int getItemCount() {
        return mData.get(0).getHourlyForecast().size();
    }

    private String converseDate(long unixDate){
        Date date = new Date(unixDate*1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, HH:mm");
        return format.format(date);
    }

//TODO add function, where user can click on specified piece of hourly/daily data, and after click, view expand, for more specified data
//TODO change Tab names
//TODO Add picasso library to display icons
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mDateTime, mTemperature, mHumidity, mPressure, mWindSpeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDateTime = itemView.findViewById(R.id.hourly_datetime);
            mTemperature = itemView.findViewById(R.id.hourly_temperature);
            mHumidity = itemView.findViewById(R.id.hourly_humidity);
            mPressure = itemView.findViewById(R.id.hourly_pressure);
            mWindSpeed = itemView.findViewById(R.id.hourly_wind_speed);
        }
    }
}
