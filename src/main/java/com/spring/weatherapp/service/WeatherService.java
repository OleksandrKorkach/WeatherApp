package com.spring.weatherapp.service;

import com.spring.weatherapp.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherData getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=4&units=metric";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", city);
        uriVariables.put("apiKey", apiKey);

        ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, uriVariables);

        WeatherResponse response = responseEntity.getBody();

        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);


        List<WeatherData.WeatherInTimestamp> weatherTimestamps = new ArrayList<>();

        String cityName = response.getCity().getName();
        String country = response.getCity().getCountry();
        Integer population = response.getCity().getPopulation();
        List<WeatherResponse.WeatherByTimestamp> responseList = response.getList();

        for (WeatherResponse.WeatherByTimestamp weatherByTimestamp : responseList) {
            WeatherData.WeatherInTimestamp weatherInTimestamp = new WeatherData.WeatherInTimestamp();
            weatherInTimestamp.setWeatherName(weatherByTimestamp.getWeather().get(0).getMain());
            weatherInTimestamp.setWeatherDescription(weatherByTimestamp.getWeather().get(0).getDescription());
            String dateInString = weatherByTimestamp.getDt_txt();
            LocalDateTime date = LocalDateTime.parse(dateInString, formatter);
            weatherInTimestamp.setThreeHoursTimestamp(date);
            weatherInTimestamp.setTemperature(weatherByTimestamp.getMain().getTemp());
            weatherInTimestamp.setFeelsLikeTemperature(weatherByTimestamp.getMain().getFeels_like());
            weatherInTimestamp.setPressure(weatherByTimestamp.getMain().getPressure());
            weatherInTimestamp.setHumidity(weatherByTimestamp.getMain().getHumidity());
            weatherInTimestamp.setVisibility(weatherByTimestamp.getVisibility());
            weatherInTimestamp.setCloudsInPercent(weatherByTimestamp.getClouds().getAll());
            weatherInTimestamp.setWindSpeed(weatherByTimestamp.getWind().getSpeed());
            weatherTimestamps.add(weatherInTimestamp);
        }

        return new WeatherData(cityName, country, population, weatherTimestamps);
    }


}
