package com.example.myforecast;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<ForecastModel> mForecastData = new ArrayList<>();

    public ForecastAdapter(List<ForecastModel> forecastData) {
        this.mForecastData = forecastData;
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ForecastViewHolder holder, int position) {
        holder.datetime.setText(converseDate(mForecastData.get(0).getForecastData().get(position).getDateTime()));

        holder.temperature.setText(String.format("Temperature: %.0f\u2103", mForecastData.get(0).getForecastData().get(position).getMainData().getTemperature()));
        holder.pressure.setText(String.format("Pressure: %d hPa ", mForecastData.get(0).getForecastData().get(position).getMainData().getPressure()));
        holder.humidity.setText(String.format("Humidity: %d%%", mForecastData.get(0).getForecastData().get(position).getMainData().getHumidity()));
    }

    @Override
    public int getItemCount() {
        return mForecastData.get(0).getForecastData().size();
    }

    private String converseDate(long unixDate){
        Date date = new Date(unixDate*1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, HH:mm");
        return format.format(date);
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView datetime, temperature, pressure, humidity;
        ImageView forecastIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            this.datetime = (TextView) itemView.findViewById(R.id.five_hours_datetime);
            this.temperature = (TextView) itemView.findViewById(R.id.five_hours_temperature);
            this.pressure = (TextView) itemView.findViewById(R.id.five_hours_pressure);
            this.humidity = (TextView) itemView.findViewById(R.id.five_hours_humidity);
            this.forecastIcon = (ImageView) itemView.findViewById(R.id.five_hours_icon);
        }
    }
}
