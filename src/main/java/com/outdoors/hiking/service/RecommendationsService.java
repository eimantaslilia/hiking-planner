package com.outdoors.hiking.service;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class RecommendationsService {

    @Autowired
    private List<InformationProvider> providers;

    public Recommendation constructRecommendations(WeatherData weatherData, Input input) {
        Recommendation rec = new Recommendation();
        providers.forEach(provider -> provider.provide(rec, weatherData, input));
        return rec;
    }
}