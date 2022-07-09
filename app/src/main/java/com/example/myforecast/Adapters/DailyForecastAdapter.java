package com.example.myforecast.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myforecast.Date.ConvertData;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.R;
import com.example.myforecast.databinding.DailyItemsBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private ConvertData mConvertData = new ConvertData();
    private ForecastIcon mIcon = new ForecastIcon();

    private List<ForecastModel> mData = new ArrayList<>();
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;


    public DailyForecastAdapter(List<ForecastModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DailyItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //      Expanding layout
        final boolean isExpanded = position == mExpandedPosition;
        holder.mBinding.dailyExpanded.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
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


        holder.mBinding.dailySunrise.setText(mConvertData.convertSunriseSunset(mData.get(0).getDailyForecast().get(position).getSunrise()));
        holder.mBinding.dailySunset.setText(mConvertData.convertSunriseSunset(mData.get(0).getDailyForecast().get(position).getSunset()));
        holder.mBinding.dailyMinTemp.setText(holder.itemView.getResources().getString(R.string.tempMin,
                mData.get(0).getDailyForecast().get(position).getTemp().getMinimalTemp()));
        holder.mBinding.dailyMaxTemp.setText(holder.itemView.getResources().getString(R.string.tempMax,
                mData.get(0).getDailyForecast().get(position).getTemp().getMaxTemp()));
        holder.mBinding.dailyMainDescription.setText(holder.itemView.getResources().getString(R.string.mainDesc,
                StringUtils.capitalize(mData.get(0).getDailyForecast().get(position).getWeatherList().get(0).getMain())));
        holder.mBinding.dailyDatetime.setText(mConvertData.convertDailytDate(mData.get(0).getDailyForecast().get(position).getDateTime()));
        holder.mBinding.dailyTempMorning.setText(holder.itemView.getResources().getString(R.string.tempMorning,
                mData.get(0).getDailyForecast().get(position).getTemp().getMorningTemp()));
        holder.mBinding.dailyTempDay.setText(holder.itemView.getResources().getString(R.string.tempDay,
                mData.get(0).getDailyForecast().get(position).getTemp().getDayTemp()));
        holder.mBinding.dailyTempEve.setText(holder.itemView.getResources().getString(R.string.tempEvening,
                mData.get(0).getDailyForecast().get(position).getTemp().getEveningTemp()));
        holder.mBinding.dailyTempNight.setText(holder.itemView.getResources().getString(R.string.tempNight,
                mData.get(0).getDailyForecast().get(position).getTemp().getNightTemp()));
        holder.mBinding.dailyPressure.setText(holder.itemView.getResources().getString(R.string.pressure,
                mData.get(0).getDailyForecast().get(position).getPressure()));
        holder.mBinding.dailyHumidity.setText(holder.itemView.getResources().getString(R.string.humidity,
                mData.get(0).getDailyForecast().get(position).getHumidity()));
        holder.mBinding.dailyWindSpeed.setText(holder.itemView.getResources().getString(R.string.windSpeed,
                mData.get(0).getDailyForecast().get(position).getWindSpeed()));
        holder.mBinding.dailyFeelsTempMorning.setText(holder.itemView.getResources().getString(R.string.feelsLikeMorning,
                mData.get(0).getDailyForecast().get(position).getTemp().getMorningTemp()));
        holder.mBinding.dailyFeelsTempDay.setText(holder.itemView.getResources().getString(R.string.feelsLikeDay,
                mData.get(0).getDailyForecast().get(position).getTemp().getDayTemp()));
        holder.mBinding.dailyFeelsTempEve.setText(holder.itemView.getResources().getString(R.string.feelsLikeEvening,
                mData.get(0).getDailyForecast().get(position).getTemp().getEveningTemp()));
        holder.mBinding.dailyFeelsTempNight.setText(holder.itemView.getResources().getString(R.string.feelsLikeNight,
                mData.get(0).getDailyForecast().get(position).getTemp().getNightTemp()));
        holder.mBinding.dailyCloudiness.setText(holder.itemView.getResources().getString(R.string.cloudiness,
                mData.get(0).getDailyForecast().get(position).getClouds()));
        holder.mBinding.dailyWindGust.setText(holder.itemView.getResources().getString(R.string.windGust,
                mData.get(0).getDailyForecast().get(position).getWindGust()));
        holder.mBinding.dailyPop.setText(holder.itemView.getResources().getString(R.string.pop,
                mConvertData.convertPop(mData.get(0).getDailyForecast().get(position).getPop())));
        holder.mBinding.dailyFullDescription.setText(holder.itemView.getResources().getString(R.string.fullDesc,
                StringUtils.capitalize(mData.get(0).getDailyForecast().get(position).getWeatherList().get(0).getDescription())));

        mIcon.loadIcon("01d", holder.mBinding.dailySunriseIcon);
        mIcon.loadIcon("01n", holder.mBinding.dailySunsetIcon);

        mIcon.loadIcon(
                mData.get(0).getDailyForecast().get(position).getWeatherList().get(0).getIconId(),
                holder.mBinding.dailyIcon
        );
    }

    @Override
    public int getItemCount() {
        return mData.get(0).getDailyForecast().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final DailyItemsBinding mBinding;

        public ViewHolder(DailyItemsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
