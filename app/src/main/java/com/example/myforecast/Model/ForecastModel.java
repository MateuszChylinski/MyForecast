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
    private Current current;
    //      Entering the "hourly" forecast
    @SerializedName("hourly")
    private List<Hourly> hourlyForecast = new ArrayList<>();
    //      Entering the "daily" forecast
    @SerializedName("daily")
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

    public class Current{
        @SerializedName("dt")
        private int dateTime;
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


        public int getDateTime() {
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



    public class Hourly{
        @SerializedName("dt")
        private int dateTime;
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
        private double pop;
//      Access the "Weather" list data inside the hourly object list
        @SerializedName("weather")
        private List<Weather> weatherList;

        public int getDateTime() {
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
        public double getPop() {
            return pop;
        }
        public List<Weather> getWeatherList() {
            return weatherList;
        }
    }

    public class Daily{
        @SerializedName("dt")
        private int dateTime;
        @SerializedName("sunrise")
        private int sunrise;
        @SerializedName("sunset")
        private int sunset;
        @SerializedName("moonrise")
        private int moonrise;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;
        @SerializedName("moonset")
        private int moonset;
        @SerializedName("moon_phase")
        private double moonPhase;
        @SerializedName("clouds")
        private int clouds;
        @SerializedName("pop")
        private double pop;
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
        @SerializedName("temp")
        private Temp temp;
        @SerializedName("feels_like")
        private FeelsLike feelsLike;

        public int getDateTime() {
            return dateTime;
        }
        public int getSunrise() {
            return sunrise;
        }
        public int getSunset() {
            return sunset;
        }
        public int getMoonrise() {
            return moonrise;
        }
        public int getMoonset() {
            return moonset;
        }
        public double getMoonPhase() {
            return moonPhase;
        }
        public int getClouds() {
            return clouds;
        }
        public double getPop() {
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

        public FeelsLike getFeelsLike() {
            return feelsLike;
        }

        public Temp getTemp() {
            return temp;
        }

        public int getPressure() {
            return pressure;
        }
        public int getHumidity() {
            return humidity;
        }
        public List<Weather> getWeatherList() {
            return weatherList;
        }
    }



    public class Temp{
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

    }



    public class FeelsLike{
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



    public class Weather{
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