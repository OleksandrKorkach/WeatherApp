package com.spring.weatherapp.service;

import com.spring.weatherapp.service.enums.Cities;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchingEngineService {

    public List<String> searchCities(String query) {
        List<String> result = new ArrayList<>();
        for (Cities city : Cities.values()){
            if (city.name().toLowerCase().startsWith(query.toLowerCase())){
                result.add(city.name().toLowerCase());
            }
        }
        return result;
    }

}
