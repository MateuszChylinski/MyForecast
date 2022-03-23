package com.example.myforecast;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<ForecastModel> mForecastData = new ArrayList<>();
    private IconLoader mIconLoader = new IconLoader();
    private static final String TAG = "ForecastAdapter";



    public ForecastAdapter(List<ForecastModel> forecastData) {
        this.mForecastData = forecastData;
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ForecastViewHolder holder, int position) {
        holder.datetime.setText(String.valueOf(mForecastData.get(0).getForecastData().get(position).getDateTime()));
        holder.temperature.setText(String.valueOf("Temperature: "+mForecastData.get(0).getForecastData().get(position).getMainData().getTemperature()));
        holder.pressure.setText(String.valueOf("Pressure: "+mForecastData.get(0).getForecastData().get(position).getMainData().getPressure()));
        holder.humidity.setText(String.valueOf("Humidity: "+mForecastData.get(0).getForecastData().get(position).getMainData().getHumidity()));
        Log.i(TAG, "onBindViewHolder: "+
                "http://openweathermap.org/img/wn/"+
                mForecastData.get(0).getForecastData().get(position).getWeather().get(0).getIconId()
                +"@2x.png");

        Picasso.get()

                .load("https://openweathermap.org/img/wn/"+
                mForecastData.get(0).getForecastData().get(position).getWeather().get(0).getIconId()
                +"@4x.png")
                .into(holder.forecastIcon);
    }

    @Override
    public int getItemCount() {
        return mForecastData.get(0).getForecastData().size();
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
