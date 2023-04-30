package com.spring.weatherapp.controller;

import com.spring.weatherapp.model.WeatherData;
import com.spring.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public WeatherData getWeatherData(@PathVariable String city) {
        return weatherService.getWeatherData(city);
    }


}