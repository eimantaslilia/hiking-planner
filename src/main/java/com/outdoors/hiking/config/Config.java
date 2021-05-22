package com.outdoors.hiking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outdoors.hiking.service.WeatherQueryService;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public WeatherQueryService weatherQueryService(ObjectMapper objectMapper,
                                                   @Value("${open.weather.map.api.url}") String url,
                                                   @Value("${x-rapidapi-key}") String key,
                                                   @Value("${x-rapidapi-host}") String host) {
        return new WeatherQueryService(HttpClients.createDefault(), objectMapper, url, key, host);
    }
}