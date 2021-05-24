package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests covering {@link WeatherInformationProvider}
 */
public class WeatherInformationProviderTest {

    private static final String NAME = "name";
    private static final String VISIBILITY = "visibility";
    private final InformationProvider provider = new WeatherInformationProvider();

    @Test
    void shouldPopulateIfWeatherDataIsPresent() {
        //given
        WeatherData data = new WeatherData();
        data.setName(NAME);
        data.setVisibility(VISIBILITY);

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("vilnius", 25));
        Map<String, String> weatherInfo = recommendation.getWeatherInfo();

        //then
        assertEquals(2, weatherInfo.size());
        assertEquals(VISIBILITY, weatherInfo.get(VISIBILITY));
        assertEquals(NAME, weatherInfo.get("city"));
    }

    @Test
    void shouldNotPopulateIfWeatherDataIsEmpty() {
        //given
        WeatherData data = new WeatherData();
        data.setPopulated(false);

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("vilnius", 25));

        //then
        assertTrue(recommendation.getWeatherInfo().isEmpty());
    }
}