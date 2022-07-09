package com.example.myforecast.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myforecast.Date.ConvertData;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;
import com.example.myforecast.databinding.FragmentCurrentForecastBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentForecast extends Fragment {

    private FragmentCurrentForecastBinding mCurrentBinding;

    private ConvertData mConvertData;
    private ForecastIcon mForecastIcon;

    private final double mLatitude, mLongitude;
    private final List<ForecastModel> mForecastData = new ArrayList<>();

    public CurrentForecast(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mCurrentBinding = FragmentCurrentForecastBinding.inflate(getLayoutInflater(), container, false);

        mConvertData = new ConvertData();
        mForecastIcon = new ForecastIcon();

        getForecastData();
        return mCurrentBinding.getRoot();
    }

    private void getForecastData() {
        ForecastViewModel mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.getForecastData(mLatitude, mLongitude).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onChanged(List<ForecastModel> list) {

//              Setup TextView's
                mCurrentBinding.currentDatetime.setText(mConvertData.convertDate(list.get(0).getCurrent().getDateTime()));
                mCurrentBinding.currentMainDescription.setText(getResources().getString(R.string.mainDesc,
                        StringUtils.capitalize(list.get(0).getCurrent().getWeatherList().get(0).getMain())));

                mCurrentBinding.currentSunrise.setText(mConvertData.convertSunriseSunset(list.get(0).getCurrent().getSunrise()));
                mCurrentBinding.currentSunset.setText(mConvertData.convertSunriseSunset(list.get(0).getCurrent().getSunset()));
                mCurrentBinding.currentTemperature.setText(getResources().getString(R.string.temperature,
                        list.get(0).getCurrent().getTemperature()));
                mCurrentBinding.currentPressure.setText(getResources().getString(R.string.pressure,
                        list.get(0).getCurrent().getPressure()));
                mCurrentBinding.currentHumidity.setText(getResources().getString(R.string.humidity,
                        list.get(0).getCurrent().getHumidity()));
                mCurrentBinding.currentWindSpeed.setText(getResources().getString(R.string.windSpeed,
                        list.get(0).getCurrent().getWindSpeed()));
                mCurrentBinding.currentFullDescription.setText(getResources().getString(R.string.fullDesc,
                        StringUtils.capitalize(list.get(0).getCurrent().getWeatherList().get(0).getDescription())));

//              Setup ImageView's
                mForecastIcon.loadIcon("01d", mCurrentBinding.currentSunriseIcon);
                mForecastIcon.loadIcon("01n", mCurrentBinding.currentSunsetIcon);
                mForecastIcon.loadIcon(
                        list.get(0).getCurrent().getWeatherList().get(0).getIconId(),
                        mCurrentBinding.currentIcon
                );
            }
        });
    }
}
