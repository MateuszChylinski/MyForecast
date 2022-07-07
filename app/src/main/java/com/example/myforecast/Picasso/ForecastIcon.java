package com.example.myforecast.Picasso;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ForecastIcon {
    /*
    * Method that load specific icon as a reference to forecast.
    * The iconID is provided by api and it refers to specific piece of forecast data
    * */
    public void loadIcon(String iconId, ImageView icon) {
        String URL = "https://openweathermap.org/img/wn/"+iconId+"@4x.png";
        Picasso
                .get()
                .load(URL)
                .into(icon);
    }
}
