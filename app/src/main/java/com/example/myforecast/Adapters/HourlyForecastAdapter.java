package com.example.myforecast.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myforecast.Date.ConverseDate;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.Model.ForecastModel;

import com.example.myforecast.R;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {
    private List<ForecastModel> mData = new ArrayList<>();
    private ForecastIcon mIcon = new ForecastIcon();
    private ConverseDate mConverseDate = new ConverseDate();

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;


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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//      Expanding layout
        final boolean isExpanded = position == mExpandedPosition;
        holder.mExpandLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });


//      "Basic" layout look
        mIcon.loadIcon(
                mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getIconId(),
                holder.mForecastIcon
        );
        holder.mDateTime.setText(mConverseDate.converseDate(mData.get(0).getHourlyForecast().get(position).getDateTime()));
        holder.mMainDescription.setText(mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getMain());
        holder.mTemperature.setText(String.format("Temperature: %.0f\u2103", mData.get(0).getHourlyForecast().get(position).getTemperature()));
        holder.mPressure.setText(String.format("Pressure: %d hPa ", mData.get(0).getHourlyForecast().get(position).getPressure()));
        holder.mHumidity.setText(String.format("Humidity: %d%%", mData.get(0).getHourlyForecast().get(position).getHumidity()));
        holder.mWindSpeed.setText(String.format("Wind speed: %.2f km/h", mData.get(0).getHourlyForecast().get(position).getWindSpeed()));

//      After expand
        holder.mFeelsLike.setText(String.format("Feels like: %.0f\u2103", mData.get(0).getHourlyForecast().get(position).getFeelsLike()));
        holder.mCloudiness.setText(String.format("Cloudiness: %d%%", mData.get(0).getHourlyForecast().get(position).getClouds()));
        holder.mVisibility.setText(String.format("Visibility: %d meters", mData.get(0).getHourlyForecast().get(position).getVisibility()));
        holder.mWindGust.setText(String.format("Wind gust: %.2f m/s", mData.get(0).getHourlyForecast().get(position).getWindGust()));
        holder.mPrecipitation.setText(String.format("Precipitation: %.0f", mData.get(0).getHourlyForecast().get(position).getPop()));
        holder.mFullDescription.setText(StringUtils.capitalize(mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getDescription()));

    }


    @Override
    public int getItemCount() {
        return mData.get(0).getHourlyForecast().size();
    }


    //TODO add string resources, make shapes if needed
    //TODO trigger location
    //TODO bind views (butter knife?)
    //TODO convert precipitation
    //TODO check all of the names in .xml
    //TODO add placeholders for views
    //TODO add polish translation

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mExpandLayout;


        TextView mDateTime, mMainDescription, mTemperature, mHumidity, mPressure, mWindSpeed, mFeelsLike, mCloudiness, mVisibility, mWindGust, mPrecipitation, mFullDescription;
        ImageView mForecastIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mExpandLayout = itemView.findViewById(R.id.expanded_test);

            mDateTime = itemView.findViewById(R.id.hourly_datetime);
            mMainDescription = itemView.findViewById(R.id.hourly_main_description);
            mTemperature = itemView.findViewById(R.id.hourly_temperature);
            mHumidity = itemView.findViewById(R.id.hourly_humidity);
            mPressure = itemView.findViewById(R.id.hourly_pressure);
            mWindSpeed = itemView.findViewById(R.id.hourly_wind_speed);
            mForecastIcon = itemView.findViewById(R.id.hourly_icon);

//          Views that are being visible after layout expand
            mFeelsLike = itemView.findViewById(R.id.hourly_feels_like);
            mCloudiness = itemView.findViewById(R.id.hourly_clouds);
            mVisibility = itemView.findViewById(R.id.hourly_visibility);
            mWindGust = itemView.findViewById(R.id.hourly_wind_gust);
            mPrecipitation = itemView.findViewById(R.id.hourly_pop);
            mFullDescription = itemView.findViewById(R.id.hourly_full_description);
        }
    }
}
