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
        return getWeather(city, url);
    }

    public ResponseEntity<?> getTodayWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=40&units=metric";
        return getWeather(city, url, today);
    }

    public ResponseEntity<?> getTomorrowWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=40&units=metric";
        return getWeather(city, url, tomorrow);
    }

    public ResponseEntity<?> getWeather(String city, String url, LocalDate date){
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);
        urlVariables.put("apiKey", apiKey);
        return getWeatherData(url, urlVariables, date);
    }

    public ResponseEntity<?> getWeather(String city, String url){
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);
        urlVariables.put("apiKey", apiKey);
        return getWeatherData(url, urlVariables, null);
    }

    public ResponseEntity<?> getWeatherData(String url, Map<String, String> urlVariables, LocalDate date){
        ResponseEntity<WeatherResponse> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, urlVariables);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body("No such city");
        }
        WeatherResponse response = responseEntity.getBody();
        if (date != null){
            return ResponseEntity.ok(getApiData(response, date));
        } else {
            return ResponseEntity.ok(getApiData(response));
        }
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
        List<WeatherResponse.TimestampWeather> responseList = response.getList();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = new ArrayList<>();

        for (WeatherResponse.TimestampWeather weatherTimestamp : responseList){
            WeatherData.WeatherInTimestamp weatherInTimestamp = convertWeatherResponseToWeatherInTimestamp(weatherTimestamp);
            if (date.isEqual(weatherInTimestamp.getThreeHoursTimestamp().toLocalDate())){
                weatherTimestamps.add(weatherInTimestamp);
            }
        }
        return weatherTimestamps;
    }

    public List<WeatherData.WeatherInTimestamp> getTimestampsData(WeatherResponse response){
        List<WeatherResponse.TimestampWeather> responseList = response.getList();
        List<WeatherData.WeatherInTimestamp> weatherTimestamps = new ArrayList<>();
        for (WeatherResponse.TimestampWeather weatherTimestamp : responseList){
            WeatherData.WeatherInTimestamp weatherInTimestamp = convertWeatherResponseToWeatherInTimestamp(weatherTimestamp);
            weatherTimestamps.add(weatherInTimestamp);
        }
        return weatherTimestamps;
    }

    private WeatherData.WeatherInTimestamp convertWeatherResponseToWeatherInTimestamp(WeatherResponse.TimestampWeather timestampWeather) {
        WeatherData.WeatherInTimestamp weatherInTimestamp = new WeatherData.WeatherInTimestamp();
        weatherInTimestamp.setWeatherName(timestampWeather.getWeather().get(0).getMain());
        weatherInTimestamp.setWeatherDescription(timestampWeather.getWeather().get(0).getDescription());
        String dateInString = timestampWeather.getDt_txt();
        LocalDateTime formattedDate = LocalDateTime.parse(dateInString, formatter);
        weatherInTimestamp.setThreeHoursTimestamp(formattedDate);
        weatherInTimestamp.setTemperature(timestampWeather.getMain().getTemp());
        weatherInTimestamp.setFeelsLikeTemperature(timestampWeather.getMain().getFeels_like());
        weatherInTimestamp.setPressure(timestampWeather.getMain().getPressure());
        weatherInTimestamp.setHumidity(timestampWeather.getMain().getHumidity());
        weatherInTimestamp.setVisibility(timestampWeather.getVisibility());
        weatherInTimestamp.setCloudsInPercent(timestampWeather.getClouds().getAll());
        weatherInTimestamp.setWindSpeed(timestampWeather.getWind().getSpeed());
        return weatherInTimestamp;
    }
}
