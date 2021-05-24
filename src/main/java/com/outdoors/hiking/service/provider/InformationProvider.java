package com.outdoors.hiking.service.provider;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;

public interface InformationProvider {

    /**
     * Provides information about certain aspects of a hikign trip such as gear, food, etc. based on trip parameters
     *
     * @param rec {@link Recommendation}
     * @param weatherData information about the weather
     * @param input user's input
     * @return populated {@link Recommendation}
     */
    Recommendation provide(Recommendation rec, WeatherData weatherData, Input input);
}