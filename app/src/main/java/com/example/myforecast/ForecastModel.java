package com.example.myforecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class ForecastModel {
    @SerializedName("list")
    List<dataList> forecastData = new ArrayList<>();

    public List<dataList> getForecastData() {
        return forecastData;
    }


    public class dataList {
        @SerializedName("dt")
        private int dateTime;
        @SerializedName("main")
        private mainData mainData = null;

        public int getDateTime() {
            return dateTime;
        }

        public ForecastModel.mainData getMainData() {
            return mainData;
        }
    }

    public class mainData {
        @SerializedName("temp")
        private double temperature;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;

        public double getTemperature() {
            return temperature;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }
}