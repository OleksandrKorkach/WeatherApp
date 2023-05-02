package com.spring.weatherapp.service;

import com.spring.weatherapp.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    final DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    final LocalDateTime now = LocalDateTime.now();
    final LocalDate today = LocalDate.now();
    final LocalDate tomorrow = LocalDate.now().plusDays(1);

    public ResponseEntity<?> getWeeklyWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=40&units=metric";
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);
        urlVariables.put("apiKey", apiKey);
        ResponseEntity<WeatherResponse> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, urlVariables);
        } catch (HttpClientErrorException e){
            return ResponseEntity.badRequest().body("No such city");
        }

        WeatherResponse response = responseEntity.getBody();
        return ResponseEntity.ok(getApiData(response));
    }

    public ResponseEntity<?> getTodayWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=40&units=metric";
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);
        urlVariables.put("apiKey", apiKey);
        ResponseEntity<WeatherResponse> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, urlVariables);
        } catch (HttpClientErrorException e){
            return ResponseEntity.badRequest().body("No such city");
        }
        WeatherResponse response = responseEntity.getBody();
        return ResponseEntity.ok(getApiData(response, today));
    }

    public ResponseEntity<?> getTomorrowWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=40&units=metric";
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);
        urlVariables.put("apiKey", apiKey);
        ResponseEntity<WeatherResponse> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, urlVariables);
        } catch (HttpClientErrorException e){
            return ResponseEntity.badRequest().body("No such city");
        }
        WeatherResponse response = responseEntity.getBody();
        return ResponseEntity.ok(getApiData(response, tomorrow));
    }

    public WeatherData getApiData(WeatherResponse response, LocalDate date){
        String cityName = response.getCity().getName();
        String country = response.getCity().getCountry();
        Integer population = response.getCity().getPopulation();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = getTimestampsData(response, date);
        return new WeatherData(cityName, country, population, weatherTimestamps);
    }

    public WeatherData getApiData(WeatherResponse response){
        String cityName = response.getCity().getName();
        String country = response.getCity().getCountry();
        Integer population = response.getCity().getPopulation();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = getTimestampsData(response);
        return new WeatherData(cityName, country, population, weatherTimestamps);
    }

    public List<WeatherData.WeatherInTimestamp> getTimestampsData(WeatherResponse response, LocalDate date){
        List<WeatherResponse.WeatherByTimestamp> responseList = response.getList();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = new ArrayList<>();

        for (WeatherResponse.WeatherByTimestamp weatherByTimestamp : responseList) {
            WeatherData.WeatherInTimestamp weatherInTimestamp = new WeatherData.WeatherInTimestamp();
            weatherInTimestamp.setWeatherName(weatherByTimestamp.getWeather().get(0).getMain());
            weatherInTimestamp.setWeatherDescription(weatherByTimestamp.getWeather().get(0).getDescription());
            String dateInString = weatherByTimestamp.getDt_txt();
            LocalDateTime formattedDate = LocalDateTime.parse(dateInString, formatter);
            weatherInTimestamp.setThreeHoursTimestamp(formattedDate);
            weatherInTimestamp.setTemperature(weatherByTimestamp.getMain().getTemp());
            weatherInTimestamp.setFeelsLikeTemperature(weatherByTimestamp.getMain().getFeels_like());
            weatherInTimestamp.setPressure(weatherByTimestamp.getMain().getPressure());
            weatherInTimestamp.setHumidity(weatherByTimestamp.getMain().getHumidity());
            weatherInTimestamp.setVisibility(weatherByTimestamp.getVisibility());
            weatherInTimestamp.setCloudsInPercent(weatherByTimestamp.getClouds().getAll());
            weatherInTimestamp.setWindSpeed(weatherByTimestamp.getWind().getSpeed());

            if (date.isEqual(formattedDate.toLocalDate())){
                weatherTimestamps.add(weatherInTimestamp);
            }
        }
        return weatherTimestamps;
    }

    public List<WeatherData.WeatherInTimestamp> getTimestampsData(WeatherResponse response){
        List<WeatherResponse.WeatherByTimestamp> responseList = response.getList();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = new ArrayList<>();

        for (WeatherResponse.WeatherByTimestamp weatherByTimestamp : responseList) {
            WeatherData.WeatherInTimestamp weatherInTimestamp = new WeatherData.WeatherInTimestamp();
            weatherInTimestamp.setWeatherName(weatherByTimestamp.getWeather().get(0).getMain());
            weatherInTimestamp.setWeatherDescription(weatherByTimestamp.getWeather().get(0).getDescription());
            String dateInString = weatherByTimestamp.getDt_txt();
            LocalDateTime formattedDate = LocalDateTime.parse(dateInString, formatter);
            weatherInTimestamp.setThreeHoursTimestamp(formattedDate);
            weatherInTimestamp.setTemperature(weatherByTimestamp.getMain().getTemp());
            weatherInTimestamp.setFeelsLikeTemperature(weatherByTimestamp.getMain().getFeels_like());
            weatherInTimestamp.setPressure(weatherByTimestamp.getMain().getPressure());
            weatherInTimestamp.setHumidity(weatherByTimestamp.getMain().getHumidity());
            weatherInTimestamp.setVisibility(weatherByTimestamp.getVisibility());
            weatherInTimestamp.setCloudsInPercent(weatherByTimestamp.getClouds().getAll());
            weatherInTimestamp.setWindSpeed(weatherByTimestamp.getWind().getSpeed());
            weatherTimestamps.add(weatherInTimestamp);
        }
        return weatherTimestamps;
    }



}
