package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Gear;
import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GearInformationProvider implements InformationProvider {

    @Value("#{${gear.map}}")
    private HashMap<String, List<String>> gearMap;

    @PostConstruct
    public void validateKeys() {
        List<String> enums = Arrays.stream(Gear.values()).map(Enum::name).collect(Collectors.toList());
        if(!gearMap.keySet().containsAll(enums)) {
            throw new RuntimeException("Please make sure that gearMap keys match GEAR_TYPE enum values.");
        }
    }

    @Override
    public Recommendation provide(Recommendation rec, WeatherData weatherData, Input input) {
        return rec;
    }
}