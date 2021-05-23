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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Component
public class RecommendationsService {

    private static final String DESCRIPTION = "description";
    private static final String MAIN = "main";
    private static final String WIND_SPEED = "wind_speed";
    private static final String CITY = "city";
    private static final String VISIBILITY = "visibility";
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";
    private static final String RAIN = "rain";

    @Value("#{${gear.map}}")
    private HashMap<String, List<String>> gearMap;

    public Recommendation constructRecommendations(WeatherData weatherData, Input input) {
        Recommendation rec = new Recommendation();

        HashMap<String, String> recMap = getRelevantWeatherData(weatherData);
        recMap.put("trip_length", input.getTripLength());
        rec.setInformation(recMap);
        rec.setGearRecommendations(getRelevantGear(recMap));
        return rec;
    }

    private HashMap<String, List<String>> getRelevantGear(HashMap<String, String> recMap) {
        String description = recMap.get(DESCRIPTION);
        String rain = recMap.get(RAIN);
        String temperature = recMap.get(TEMPERATURE);
        return null;
    }

    private HashMap<String, String> getRelevantWeatherData(WeatherData data) {
        if(data.getMessage() != null) {
            return new HashMap<>();
        }
        HashMap<String, String> map = new HashMap<>();
        Optional<Weather> weather = data.getWeather().stream().findFirst();
        map.put(DESCRIPTION, weather.map(Weather::getDescription).orElse(null));
        map.put(MAIN, weather.map(Weather::getMain).orElse(null));
        map.put(WIND_SPEED, data.getWind().getSpeed());
        map.put(CITY, data.getName());
        map.put(VISIBILITY, data.getVisibility());

        MainInfo tempInfo = data.getMain();
        map.put(TEMPERATURE, tempInfo.getTemp());
        map.put(HUMIDITY, tempInfo.getHumidity());

        return map;
    }
}
