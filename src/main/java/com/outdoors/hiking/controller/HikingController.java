package com.outdoors.hiking.controller;

import com.outdoors.hiking.service.WeatherQueryService;
import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.weather.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HikingController {

    @Autowired
    private WeatherQueryService weatherQueryService;

    @PostMapping(value = "/here", consumes = "application/json", produces = "application/json")
    public WeatherData createSomething(@RequestBody Input input) {
        WeatherData weatherData = weatherQueryService.retrieveWeatherData(input.getLocation());
        return weatherData;
    }
}