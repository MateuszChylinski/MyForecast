package com.example.myforecast.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastModel {
//    From root json response
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
    @SerializedName("timezone")
    private String timeZone;
    @SerializedName("timezone_offset")
    private int timeZoneOffset;

    //     Entering current object, for the present forecast
    @SerializedName("current")
    private Current current = null;
    //      Entering the "hourly" forecast
    private List<Hourly> hourlyForecast = null;
    //      Entering the "daily" forecast
    private List<Daily> dailyForecast = null;


    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }
    public Current getCurrent() {
        return current;
    }
    public List<Hourly> getHourlyForecast() {
        return hourlyForecast;
    }
    public List<Daily> getDailyForecast() {
        return dailyForecast;
    }

    class Current{
        @SerializedName("dt")
        private long dateTime;
        @SerializedName("sunrise")
        private long sunrise;
        @SerializedName("sunset")
        private long sunset;
        @SerializedName("temp")
        private double temperature;
        @SerializedName("feels_like")
        private double feelsLike;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;
        @SerializedName("dew_point")
        private double dewPoint;
        @SerializedName("uvi")
        private double uvi;
        @SerializedName("clouds")
        private int clouds;
        @SerializedName("visibility")
        private int visibility;
        @SerializedName("wind_speed")
        private double windSpeed;
        @SerializedName("wind_deg")
        private int windDeg;
//      Entering the Weather list for the current forecast
        @SerializedName("weather")
        private List<Weather> weatherList = null;


        public long getDateTime() {
            return dateTime;
        }
        public long getSunrise() {
            return sunrise;
        }
        public long getSunset() {
            return sunset;
        }
        public double getTemperature() {
            return temperature;
        }
        public double getFeelsLike() {
            return feelsLike;
        }
        public int getPressure() {
            return pressure;
        }
        public int getHumidity() {
            return humidity;
        }
        public double getDewPoint() {
            return dewPoint;
        }
        public double getUvi() {
            return uvi;
        }
        public int getClouds() {
            return clouds;
        }
        public int getVisibility() {
            return visibility;
        }
        public double getWindSpeed() {
            return windSpeed;
        }
        public int getWindDeg() {
            return windDeg;
        }
        public List<Weather> getWeatherList() {
            return weatherList;
        }
    }



    class Hourly{
        @SerializedName("dt")
        private long dateTime;
        @SerializedName("temp")
        private double temperature;
        @SerializedName("feels_like")
        private double feelsLike;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;
        @SerializedName("dew_point")
        private double dewPoint;
        @SerializedName("uvi")
        private double uvi;
        @SerializedName("clouds")
        private int clouds;
        @SerializedName("visibility")
        private int visibility;
        @SerializedName("wind_speed")
        private double windSpeed;
        @SerializedName("wind_deg")
        private int windDeg;
        @SerializedName("wind_gust")
        private double windGust;
        @SerializedName("pop")
        private int pop;
//      Access the "Weather" list data inside the hourly object list
        @SerializedName("weather")
        private List<Weather> weatherList;

        public long getDateTime() {
            return dateTime;
        }
        public double getTemperature() {
            return temperature;
        }
        public double getFeelsLike() {
            return feelsLike;
        }
        public int getPressure() {
            return pressure;
        }
        public int getHumidity() {
            return humidity;
        }
        public double getDewPoint() {
            return dewPoint;
        }
        public double getUvi() {
            return uvi;
        }
        public int getClouds() {
            return clouds;
        }
        public int getVisibility() {
            return visibility;
        }
        public double getWindSpeed() {
            return windSpeed;
        }
        public int getWindDeg() {
            return windDeg;
        }
        public double getWindGust() {
            return windGust;
        }
        public int getPop() {
            return pop;
        }
        public List<Weather> getWeatherList() {
            return weatherList;
        }
    }

    class Daily{
        @SerializedName("dt")
        private long dateTime;
        @SerializedName("sunrise")
        private double temperature;
        @SerializedName("sunset")
        private double feelsLike;
        @SerializedName("moonrise")
        private int pressure;
        @SerializedName("moonset")
        private int humidity;
        @SerializedName("moon_phase")
        private double moonPhase;
        @SerializedName("clouds")
        private int clouds;
        @SerializedName("pop")
        private int pop;
        @SerializedName("uvi")
        private double uvi;
        @SerializedName("dew_point")
        private double dewPoint;
        @SerializedName("wind_speed")
        private double windSpeed;
        @SerializedName("wind_deg")
        private int windDeg;
        @SerializedName("wind_gust")
        private double windGust;
        @SerializedName("weather")
        private List<Weather> weatherList = null;

        public long getDateTime() {
            return dateTime;
        }
        public double getTemperature() {
            return temperature;
        }
        public double getFeelsLike() {
            return feelsLike;
        }
        public int getPressure() {
            return pressure;
        }
        public int getHumidity() {
            return humidity;
        }
        public double getMoonPhase() {
            return moonPhase;
        }
        public int getClouds() {
            return clouds;
        }
        public int getPop() {
            return pop;
        }
        public double getUvi() {
            return uvi;
        }
        public double getDewPoint() {
            return dewPoint;
        }
        public double getWindSpeed() {
            return windSpeed;
        }
        public int getWindDeg() {
            return windDeg;
        }
        public double getWindGust() {
            return windGust;
        }
        public List<Weather> getWeatherList() {
            return weatherList;
        }
    }



    class Temp{
        @SerializedName("day")
        private double dayTemp;
        @SerializedName("min")
        private double minimalTemp;
        @SerializedName("max")
        private double maxTemp;
        @SerializedName("night")
        private double nightTemp;
        @SerializedName("eve")
        private double eveningTemp;
        @SerializedName("morn")
        private double morningTemp;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;

        public double getDayTemp() {
            return dayTemp;
        }
        public double getMinimalTemp() {
            return minimalTemp;
        }
        public double getMaxTemp() {
            return maxTemp;
        }
        public double getNightTemp() {
            return nightTemp;
        }
        public double getEveningTemp() {
            return eveningTemp;
        }
        public double getMorningTemp() {
            return morningTemp;
        }
        public int getPressure() {
            return pressure;
        }
        public int getHumidity() {
            return humidity;
        }
    }



    class FeelsLike{
        @SerializedName("day")
        private double dayTempFeels;
        @SerializedName("night")
        private double nightTempFeels;
        @SerializedName("eve")
        private double eveningTempFeels;
        @SerializedName("morn")
        private double morningTempFeels;

        public double getDayTempFeels() {
            return dayTempFeels;
        }
        public double getNightTempFeels() {
            return nightTempFeels;
        }
        public double getEveningTempFeels() {
            return eveningTempFeels;
        }
        public double getMorningTempFeels() {
            return morningTempFeels;
        }
    }



     class Weather{
        @SerializedName("id")
        private int id;
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String iconId;

        
        public int getId() {
            return id;
        }
        public String getMain() {
            return main;
        }
        public String getDescription() {
            return description;
        }
        public String getIconId() {
            return iconId;
        }
    }
}