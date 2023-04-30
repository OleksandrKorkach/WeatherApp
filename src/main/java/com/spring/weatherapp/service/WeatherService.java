package com.spring.weatherapp.service;

import com.spring.weatherapp.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherData getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=2&units=metric";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", city);
        uriVariables.put("apiKey", apiKey);

        ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(url, WeatherResponse.class, uriVariables);

        WeatherResponse response = responseEntity.getBody();

        String cityName = response.getCity().getName();
        String country = response.getCity().getCountry();
        Integer population = response.getCity().getPopulation();
        Double temperature = response.getList().get(0).getMain().getTemp();
        Integer humidity = response.getList().get(0).getMain().getHumidity();
        String description = response.getList().get(0).getWeather().get(0).getDescription();
        Double speedOfWind = response.getList().get(0).getWind().getSpeed();
        Integer clouds = response.getList().get(0).getClouds().getAll();
        String dateInString = response.getList().get(0).getDt_txt();

        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date  = LocalDateTime.parse(dateInString, formatter);

        System.out.println(cityName);
        System.out.println(country);
        System.out.println(population);
        System.out.println(temperature);
        System.out.println(humidity);
        System.out.println(description);
        System.out.println(speedOfWind);
        System.out.println(clouds);
        System.out.println(date);

        return new WeatherData();
    }


}
