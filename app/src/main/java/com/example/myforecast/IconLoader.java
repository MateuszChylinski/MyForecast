package com.example.myforecast;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class IconLoader {
    public void prepareForecastIcons(String iconId, ImageView imageView){
        String URL = "http://openweathermap.org/img/wn/"+iconId+"@2x.png";
        Picasso.get()
                .load(URL)
                .into(imageView);
    }
}
