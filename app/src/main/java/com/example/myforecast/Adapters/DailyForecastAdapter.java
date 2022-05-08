package com.example.myforecast.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myforecast.Date.ConverseDate;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {
    private List<ForecastModel> mData = new ArrayList<>();
    private ConverseDate mConverseDate = new ConverseDate();
    private ForecastIcon mIcon = new ForecastIcon();
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    public DailyForecastAdapter(List<ForecastModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_items, parent, false);
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


        holder.mDateTime.setText(mConverseDate.converseDate(mData.get(0).getDailyForecast().get(position).getDateTime()));
        holder.mSunrise.setText(mConverseDate.converseDate(mData.get(0).getDailyForecast().get(position).getSunrise()));
        holder.mSunset.setText(mConverseDate.converseDate(mData.get(0).getDailyForecast().get(position).getSunset()));
        holder.mHumidity.setText(String.format("Humidity: %d%%", mData.get(0).getDailyForecast().get(position).getHumidity()));
        holder.mPressure.setText(String.format("Pressure: %d hPa ", mData.get(0).getDailyForecast().get(position).getPressure()));
        holder.mWindSpeed.setText(String.format("Wind speed: %.2f km/h", mData.get(0).getDailyForecast().get(position).getWindSpeed()));
        holder.mWindGust.setText(String.format("Wind gust: %.2f m/s", mData.get(0).getDailyForecast().get(position).getWindGust()));
        holder.mCloudiness.setText(String.format("Cloudiness: %d%%", mData.get(0).getDailyForecast().get(position).getClouds()));
        holder.mTempMin.setText(String.format("Minimal temperature:  %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getMinimalTemp()));
        holder.mTempMax.setText(String.format("Maximal temperature:  %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getMaxTemp()));

//      After expand
        holder.mTempMorning.setText(String.format("Morning temperature: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getMorningTemp()));
        holder.mTempDay.setText(String.format("Day temperature: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getDayTemp()));
        holder.mTempEvening.setText(String.format("Evening temperature:: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getEveningTemp()));
        holder.mTempNight.setText(String.format("Night temperature: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getTemp().getNightTemp()));

        holder.mFeelsMorning.setText(String.format("Morning feels like: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getFeelsLike().getMorningTempFeels()));
        holder.mFeelsDay.setText(String.format("Day feels like:: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getFeelsLike().getDayTempFeels()));
        holder.mFeelsEvening.setText(String.format("Evening feels like: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getFeelsLike().getEveningTempFeels()));
        holder.mFeelsNight.setText(String.format("Night feels like: %.0f\u2103", mData.get(0).getDailyForecast().get(position).getFeelsLike().getNightTempFeels()));

        holder.mWeatherMainDesc.setText("Main description: "+ mData.get(0).getDailyForecast().get(position).getWeatherList().get(0).getMain());
        holder.mWeatherFullDesc.setText("Full description: "+ mData.get(0).getDailyForecast().get(position).getWeatherList().get(0).getDescription());
        holder.mPop.setText(String.format("Probability of Precipitation: %.0f", mData.get(0).getDailyForecast().get(position).getPop()));

        mIcon.loadIcon(
                mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getIconId(),
                holder.mForecastIcon
        );
        mIcon.loadIcon(
                "01d",
                holder.mSunriseIcon
        );
        mIcon.loadIcon(
                "01n",
                holder.mSunsetIcon
        );

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDateTime, mSunrise, mSunset, mWeatherFullDesc, mWeatherMainDesc, mPressure, mHumidity, mWindSpeed, mTempMorning,
                mTempDay, mTempEvening, mTempNight, mTempMin, mTempMax, mFeelsMorning, mFeelsDay, mFeelsEvening, mFeelsNight, mWindGust, mCloudiness,
                mPop;
        private ImageView mForecastIcon, mSunriseIcon, mSunsetIcon;
        private LinearLayout mExpandLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mExpandLayout = itemView.findViewById(R.id.daily_expanded);

            mSunriseIcon = itemView.findViewById(R.id.daily_sunrise_icon);
            mSunsetIcon = itemView.findViewById(R.id.daily_sunset_icon);
            mForecastIcon = itemView.findViewById(R.id.daily_icon);

            mDateTime = itemView.findViewById(R.id.daily_datetime);
            mSunrise = itemView.findViewById(R.id.daily_sunrise);
            mSunset = itemView.findViewById(R.id.daily_sunset);
            mHumidity = itemView.findViewById(R.id.daily_humidity);
            mPressure = itemView.findViewById(R.id.daily_pressure);
            mWindSpeed = itemView.findViewById(R.id.daily_wind_speed);
            mWindGust = itemView.findViewById(R.id.daily_wind_gust);
            mCloudiness = itemView.findViewById(R.id.daily_cloudiness);
            mTempMin = itemView.findViewById(R.id.daily_min_temp);
            mTempMax = itemView.findViewById(R.id.daily_max_temp);

//          Views that are being visible after layout expand
            mTempMorning = itemView.findViewById(R.id.daily_temp_morning);
            mTempDay = itemView.findViewById(R.id.daily_temp_day);
            mTempEvening = itemView.findViewById(R.id.daily_temp_eve);
            mTempNight = itemView.findViewById(R.id.daily_temp_night);

            mFeelsMorning = itemView.findViewById(R.id.daily_feels_temp_morning);
            mFeelsDay = itemView.findViewById(R.id.daily_feels_temp_day);
            mFeelsEvening = itemView.findViewById(R.id.daily_feels_temp_eve);
            mFeelsNight = itemView.findViewById(R.id.daily_feels_temp_night);

            mWeatherMainDesc = itemView.findViewById(R.id.daily_main_description);
            mWeatherFullDesc = itemView.findViewById(R.id.daily_full_description);
            mPop = itemView.findViewById(R.id.daily_pop);
        }
    }
}
