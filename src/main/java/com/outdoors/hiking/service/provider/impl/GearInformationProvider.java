package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.springframework.stereotype.Component;

@Component
public class GearInformationProvider implements InformationProvider {

    @Override
    public Recommendation provide(Recommendation rec, WeatherData weatherData, Input input) {
        return rec;
    }
}