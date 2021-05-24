package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Gear;
import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

public class GearInformationProvider implements InformationProvider {

    private final Map<String, List<String>> gearMap;

    public GearInformationProvider(Map<String, List<String>> gearMap) {
        this.gearMap = gearMap;
    }

    @PostConstruct
    public void validateKeys() {
        List<String> enums = Arrays.stream(Gear.values()).map(Enum::name).collect(Collectors.toList());
        if (!gearMap.keySet().containsAll(enums) || !enums.containsAll(gearMap.keySet())) {
            throw new RuntimeException(String.format("Please make sure that gearMap keys match %s enum values.", Gear.class.getSimpleName()));
        }
    }

    @Override
    public Recommendation provide(Recommendation rec, WeatherData weatherData, Input input) {
        rec.setGearInfo(getRelevantGear(weatherData, input));
        return rec;
    }

    private Map<String, List<String>> getRelevantGear(WeatherData data, Input input) {
        var recommendedGear = new HashMap<String, List<String>>();
        recommendedGear.put(Gear.ESSENTIALS.name(), gearMap.get(Gear.ESSENTIALS.name()));
        recommendedGear.put(Gear.OPTIONAL.name(), gearMap.get(Gear.OPTIONAL.name()));

        if (input.getTripLength() >= 20) {
            recommendedGear.put(Gear.NIGHT.name(), gearMap.get(Gear.NIGHT.name()));
            recommendedGear.put(Gear.COLD.name(), gearMap.get(Gear.COLD.name()));
            recommendedGear.put(Gear.NAVIGATION.name(), gearMap.get(Gear.NAVIGATION.name()));
        }
        if (data.isPopulated()) {
            return recommendBasedOnWeatherConditions(data, recommendedGear);
        }
        return recommendedGear;
    }

    private HashMap<String, List<String>> recommendBasedOnWeatherConditions(WeatherData data, HashMap<String, List<String>> recommendedGear) {
        Optional.ofNullable(data.getWeather()).flatMap(w -> w.stream().findFirst()).ifPresent(w -> {
            if ("Clear".equals(w.getMain())) {
                recommendedGear.put(Gear.SUNNY.name(), gearMap.get(Gear.SUNNY.name()));
            } else if ("Rain".equals(w.getMain())) {
                recommendedGear.put(Gear.RAINY.name(), gearMap.get(Gear.RAINY.name()));
            }
        });
        Optional.ofNullable(data.getMain()).ifPresent(temp -> {
            if (Double.parseDouble(temp.getTemp()) < 10) {
                recommendedGear.computeIfAbsent(Gear.COLD.name(), k -> gearMap.get(k));
            }
        });
        return recommendedGear;
    }
}