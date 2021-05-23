package com.outdoors.hiking.service;

import com.outdoors.hiking.dto.Gear;
import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class RecommendationsService {

    @Autowired
    private List<InformationProvider> providers;

    @Value("#{${gear.map}}")
    private HashMap<String, List<String>> gearMap;

    @PostConstruct
    public void validateKeys() {
        List<String> enums = Arrays.stream(Gear.values()).map(Enum::name).collect(Collectors.toList());
        if(!gearMap.keySet().containsAll(enums)) {
            throw new RuntimeException("Please make sure that gearMap keys match GEAR_TYPE enum values.");
        }
    }

    public Recommendation constructRecommendations(WeatherData weatherData, Input input) {
        Recommendation rec = new Recommendation();
        providers.forEach(provider -> provider.provide(rec, weatherData, input));
        return rec;
    }
}