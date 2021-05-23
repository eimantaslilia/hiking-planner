package com.outdoors.hiking.controller;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.RecommendationsService;
import com.outdoors.hiking.service.WeatherQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HikingController {

    @Autowired
    private WeatherQueryService weatherQueryService;

    @Autowired
    private RecommendationsService recommendationsService;

    @PostMapping(value = "/recommendations", consumes = "application/json", produces = "application/json")
    public Recommendation recommendations(@RequestBody Input input) {

        WeatherData weatherData = weatherQueryService.retrieveWeatherData(input.getLocation());

        return recommendationsService.constructRecommendations(weatherData, input);
    }
}