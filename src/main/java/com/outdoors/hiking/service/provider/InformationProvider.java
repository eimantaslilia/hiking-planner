package com.outdoors.hiking.service.provider;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;

public interface InformationProvider {

    Recommendation provide(Recommendation rec, WeatherData weatherData, Input input);
}