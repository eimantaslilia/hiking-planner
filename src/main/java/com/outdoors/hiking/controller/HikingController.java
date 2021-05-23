package com.outdoors.hiking.controller;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.RecommendationsService;
import com.outdoors.hiking.service.WeatherQueryService;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RequestMapping("hiking-app")
public class HikingController {

    private final WeatherQueryService weatherQueryService;

    private final RecommendationsService recommendationsService;

    public HikingController(WeatherQueryService weatherQueryService, RecommendationsService recommendationsService) {
        this.weatherQueryService = weatherQueryService;
        this.recommendationsService = recommendationsService;
    }

    @GetMapping(value = "/recommendations", consumes = "application/json", produces = "application/json")
    public Recommendation recommendations(@RequestBody Input input) {
        validateInput(input);
        WeatherData weatherData = weatherQueryService.retrieveWeatherData(input.getLocation());
        return recommendationsService.constructRecommendations(weatherData, input);
    }

    private void validateInput(Input input) {
        if(input.getTripLength() <= 0 || input.getLocation() == null) {
            throw new IllegalArgumentException("Please provide both: tripLength and location");
        }
    }
}