package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class WeatherInformationProvider implements InformationProvider {

    private static final String DESCRIPTION = "description";
    private static final String MAIN = "main";
    private static final String WIND_SPEED = "wind_speed";
    private static final String CITY = "city";
    private static final String VISIBILITY = "visibility";
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";

    @Override
    public Recommendation provide(Recommendation rec, WeatherData weatherData, Input input) {
        Map<String, String> weatherInfo = getRelevantWeatherData(weatherData);
        rec.setWeatherInfo(weatherInfo);
        return rec;
    }

    private HashMap<String, String> getRelevantWeatherData(WeatherData data) {
        HashMap<String, String> weatherMap = new HashMap<>();
        if (!data.isPopulated()) {
            return weatherMap;
        }
        Optional.ofNullable(data.getWind()).ifPresent(wind -> weatherMap.put(WIND_SPEED, wind.getSpeed()));
        Optional.ofNullable(data.getName()).ifPresent(name -> weatherMap.put(CITY, name));
        Optional.ofNullable(data.getVisibility()).ifPresent(vis -> weatherMap.put(VISIBILITY, vis));

        Optional.ofNullable(data.getWeather()).flatMap(w -> w.stream().findFirst()).ifPresent(w -> {
            weatherMap.put(DESCRIPTION, w.getDescription());
            weatherMap.put(MAIN, w.getMain());
        });

        Optional.ofNullable(data.getMain()).ifPresent(temp -> {
            weatherMap.put(TEMPERATURE, temp.getTemp());
            weatherMap.put(HUMIDITY, temp.getHumidity());
        });

        return weatherMap;
    }
}