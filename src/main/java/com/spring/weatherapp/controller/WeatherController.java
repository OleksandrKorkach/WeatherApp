package com.spring.weatherapp.controller;

import com.spring.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<?> get5DaysWeather(@PathVariable String city) {
        return weatherService.getWeeklyWeather(city);
    }

    @GetMapping("/{city}/today")
    public ResponseEntity<?> getTodayWeather(@PathVariable String city) {
        return weatherService.getTodayWeather(city);
    }

    @GetMapping("/{city}/tomorrow")
    public ResponseEntity<?> getTomorrowWeather(@PathVariable String city) {
        return weatherService.getTomorrowWeather(city);
    }


}