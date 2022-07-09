package com.example.myforecast.Adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myforecast.Date.ConvertData;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.Model.ForecastModel;

import com.example.myforecast.R;
import com.example.myforecast.databinding.HourlyItemsBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {
    private List<ForecastModel> mData = new ArrayList<>();
    private final ConvertData mConvertData = new ConvertData();
    private final ForecastIcon mIconLoader = new ForecastIcon();

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    public HourlyForecastAdapter(List<ForecastModel> mData) {
        this.mData = mData; }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(HourlyItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //      Expanding layout
        final boolean isExpanded = position == mExpandedPosition;
        holder.itemsBinding.expandedTest.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
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

        holder.itemsBinding.hourlyDatetime.setText(mConvertData.convertDate(mData.get(0).getHourlyForecast().get(position).getDateTime()));
        holder.itemsBinding.hourlyMainDescription.setText(holder.itemView.getResources().getString(R.string.mainDesc,
                StringUtils.capitalize(mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getMain())));
        holder.itemsBinding.hourlyTemperature.setText(holder.itemView.getResources().getString(R.string.temperature,
                mData.get(0).getHourlyForecast().get(position).getTemperature()));
        holder.itemsBinding.hourlyPressure.setText(holder.itemView.getResources().getString(R.string.pressure,
                mData.get(0).getHourlyForecast().get(position).getPressure()));
        holder.itemsBinding.hourlyHumidity.setText(holder.itemView.getResources().getString(R.string.humidity,
                mData.get(0).getHourlyForecast().get(position).getHumidity()));
        holder.itemsBinding.hourlyWindSpeed.setText(holder.itemView.getResources().getString(R.string.windSpeed,
                mData.get(0).getHourlyForecast().get(position).getWindSpeed()));

//      After layout expand
        holder.itemsBinding.hourlyFeelsLike.setText(holder.itemView.getResources().getString(R.string.temperatureFeelsLike,
                mData.get(0).getHourlyForecast().get(position).getFeelsLike()));
        holder.itemsBinding.hourlyClouds.setText(holder.itemView.getResources().getString(R.string.cloudiness,
                mData.get(0).getHourlyForecast().get(position).getClouds()));
        holder.itemsBinding.hourlyVisibility.setText(holder.itemView.getResources().getString(R.string.visibility,
                mData.get(0).getHourlyForecast().get(position).getVisibility()));
        holder.itemsBinding.hourlyWindGust.setText(holder.itemView.getResources().getString(R.string.windGust,
                mData.get(0).getHourlyForecast().get(position).getWindGust()));
        holder.itemsBinding.hourlyPop.setText(holder.itemView.getResources().getString(R.string.pop,
                mConvertData.convertPop(mData.get(0).getHourlyForecast().get(position).getPop())));
        holder.itemsBinding.hourlyFullDescription.setText(holder.itemView.getResources().getString(R.string.fullDesc,
                StringUtils.capitalize(mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getDescription())));

        mIconLoader.loadIcon(mData.get(0).getHourlyForecast().get(position).getWeatherList().get(0).getIconId(),
                holder.itemsBinding.hourlyIcon);
    }

    @Override
    public int getItemCount() {
        return mData.get(0).getHourlyForecast().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final HourlyItemsBinding itemsBinding;
        public ViewHolder(HourlyItemsBinding binding) {
            super(binding.getRoot());
            itemsBinding = binding;
        }
    }
}
