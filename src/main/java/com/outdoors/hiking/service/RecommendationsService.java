package com.outdoors.hiking.service;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.MainInfo;
import com.outdoors.hiking.dto.weather.Weather;
import com.outdoors.hiking.dto.weather.WeatherData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class RecommendationsService {

    protected enum GEAR_TYPE {
        NIGHT,
        SUN,
        RAIN,
        COLD,
        FOOD,
        ESSENTIALS,
        NAVIGATION,
        OPTIONAL
    }

    private static final String DESCRIPTION = "description";
    private static final String MAIN = "main";
    private static final String WIND_SPEED = "wind_speed";
    private static final String CITY = "city";
    private static final String VISIBILITY = "visibility";
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";
    private static final String RAIN = "rain";
    private static final String TRIP_LENGTH = "trip_length";

    @Value("#{${gear.map}}")
    private HashMap<String, List<String>> gearMap;

    @PostConstruct
    public void validateKeys() {
        List<String> enums = Arrays.stream(GEAR_TYPE.values()).map(Enum::name).collect(Collectors.toList());
        if(!gearMap.keySet().containsAll(enums)) {
            throw new RuntimeException("Please make sure that gearMap keys match GEAR_TYPE enum values.");
        }
    }

    public Recommendation constructRecommendations(WeatherData weatherData, Input input) {
        return new Recommendation();
    }
}