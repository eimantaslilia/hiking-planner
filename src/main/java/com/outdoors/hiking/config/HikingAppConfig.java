package com.outdoors.hiking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outdoors.hiking.controller.HikingController;
import com.outdoors.hiking.service.RecommendationsService;
import com.outdoors.hiking.service.WeatherQueryService;
import com.outdoors.hiking.service.provider.InformationProvider;
import com.outdoors.hiking.service.provider.impl.FoodInformationProvider;
import com.outdoors.hiking.service.provider.impl.GearInformationProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class HikingAppConfig {

    @Bean
    public HikingController hikingController(WeatherQueryService weatherQueryService, RecommendationsService recommendationsService) {
        return new HikingController(weatherQueryService, recommendationsService);
    }

    @Bean
    public WeatherQueryService weatherQueryService(ObjectMapper objectMapper,
                                                   @Value("${open.weather.map.api.url}") String url,
                                                   @Value("${x-rapidapi-key}") String key,
                                                   @Value("${x-rapidapi-host}") String host) {
        return new WeatherQueryService(HttpClients.createDefault(), objectMapper, url, key, host);
    }

    @Bean
    public InformationProvider foodInformationProvider(@Value("#{${foods}}") Map<String, Integer> foods) {
        return new FoodInformationProvider(foods);
    }

    @Bean
    public InformationProvider gearInformationProvider(@Value("#{${gear.map}}") HashMap<String, List<String>> gearMap) {
        return new GearInformationProvider(gearMap);
    }
}