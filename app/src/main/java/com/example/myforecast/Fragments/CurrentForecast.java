package com.example.myforecast.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myforecast.Date.ConverseDate;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.Picasso.ForecastIcon;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentForecast extends Fragment {

    public static final String TAG = "TestFragment";
    private ForecastViewModel mViewModel;

    private ConverseDate mConverseDate;
    private ForecastIcon mForecastIcon;

    private double mLatitude, mLongitude;
    private List<ForecastModel> mForecastData = new ArrayList<>();

    private TextView mDateTime, mMainDesc, mSunrise, mSunset, mTemperature, mPressure, mHumidity, mWindSpeed, mFullDescription;
    private ImageView mSunriseIV, mSunsetIV, mForecastIV;

    public CurrentForecast(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_current_forecast, container, false);

        mConverseDate = new ConverseDate();
        mForecastIcon = new ForecastIcon();

        mDateTime = fragmentView.findViewById(R.id.current_datetime);
        mMainDesc = fragmentView.findViewById(R.id.current_main_description);
        mSunrise = fragmentView.findViewById(R.id.current_sunrise);
        mSunset = fragmentView.findViewById(R.id.current_sunset);
        mTemperature = fragmentView.findViewById(R.id.current_temperature);
        mPressure = fragmentView.findViewById(R.id.current_pressure);
        mHumidity = fragmentView.findViewById(R.id.current_humidity);
        mWindSpeed = fragmentView.findViewById(R.id.current_wind_speed);
        mFullDescription = fragmentView.findViewById(R.id.current_full_description);

        mSunriseIV = fragmentView.findViewById(R.id.current_sunrise_icon);
        mSunsetIV = fragmentView.findViewById(R.id.current_sunset_icon);
        mForecastIV = fragmentView.findViewById(R.id.current_icon);

        getForecastData();


        return fragmentView;
    }

    private void getForecastData() {
        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.getForecastData(40.730610, -73.935242).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> list) {


//              Setup TextView's
                mDateTime.setText(mConverseDate.converseDate(list.get(0).getCurrent().getDateTime()));
                mMainDesc.setText("Description: "+list.get(0).getHourlyForecast().get(0).getWeatherList().get(0).getMain());
                mSunrise.setText(mConverseDate.converseDate(list.get(0).getCurrent().getSunrise()));
                mSunset.setText(mConverseDate.converseDate(list.get(0).getCurrent().getSunset()));
                mTemperature.setText(String.format("Temperature: %.0f\u2103", list.get(0).getCurrent().getTemperature()));
                mPressure.setText(String.format("Pressure: %d hPa ", list.get(0).getCurrent().getPressure()));
                mHumidity.setText(String.format("Humidity: %d%%", list.get(0).getCurrent().getHumidity()));
                mWindSpeed.setText(String.format("Wind speed: %.2f km/h", list.get(0).getCurrent().getWindSpeed()));
                mFullDescription.setText("Full description: "+StringUtils.capitalize(list.get(0).getCurrent().getWeatherList().get(0).getDescription()));

//              Setup ImageView's
                mForecastIcon.loadIcon("01d", mSunriseIV);
                mForecastIcon.loadIcon("01n", mSunsetIV);
                mForecastIcon.loadIcon(
                        list.get(0).getCurrent().getWeatherList().get(0).getIconId(),
                        mForecastIV
                );
            }
        });
    }
}
