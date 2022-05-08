package com.example.myforecast.Picasso;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ForecastIcon {

    public void loadIcon(String iconId, ImageView icon) {
        String URL = "https://openweathermap.org/img/wn/"+iconId+"@4x.png";

        Picasso
                .get()
                .load(URL)
                .into(icon);
    }
}
