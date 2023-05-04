package com.spring.weatherapp.controller;

import com.spring.weatherapp.service.SearchingEngineService;
import com.spring.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private SearchingEngineService searchingEngineService;

    @PostMapping("/")
    public ResponseEntity<?> searchCities(@RequestParam String city){
        List<String> result = searchingEngineService.searchCities(city);
        return ResponseEntity.ok(result);
    }

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