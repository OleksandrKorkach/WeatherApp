package com.spring.weatherapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherData {

    private String city;
    private String country;
    private Integer cityPopulation;
    private List<WeatherInTimestamp> weatherTimestamps;

    public WeatherData(String city, String country, Integer cityPopulation, List<WeatherInTimestamp> weatherTimestamps) {
        this.city = city;
        this.country = country;
        this.cityPopulation = cityPopulation;
        this.weatherTimestamps = weatherTimestamps;
    }

    public WeatherData(){

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(Integer cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    public List<WeatherInTimestamp> getWeatherTimestamps() {
        return weatherTimestamps;
    }

    public void setWeatherTimestamps(List<WeatherInTimestamp> weatherTimestamps) {
        this.weatherTimestamps = weatherTimestamps;
    }

    public static class WeatherInTimestamp {
        private String weatherName;
        private String weatherDescription;
        private LocalDateTime threeHoursTimestamp;
        private Double temperature;
        private Double feelsLikeTemperature;
        private Integer pressure;
        private Integer humidity;
        private Integer visibility;
        private Integer cloudsInPercent;
        private Double windSpeed;

        public String getWeatherName() {
            return weatherName;
        }

        public void setWeatherName(String weatherName) {
            this.weatherName = weatherName;
        }

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }

        public LocalDateTime getThreeHoursTimestamp() {
            return threeHoursTimestamp;
        }

        public void setThreeHoursTimestamp(LocalDateTime threeHoursTimestamp) {
            this.threeHoursTimestamp = threeHoursTimestamp;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Double getFeelsLikeTemperature() {
            return feelsLikeTemperature;
        }

        public void setFeelsLikeTemperature(Double feelsLikeTemperature) {
            this.feelsLikeTemperature = feelsLikeTemperature;
        }

        public Integer getPressure() {
            return pressure;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Integer getVisibility() {
            return visibility;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public Integer getCloudsInPercent() {
            return cloudsInPercent;
        }

        public void setCloudsInPercent(Integer cloudsInPercent) {
            this.cloudsInPercent = cloudsInPercent;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }
    }

}
