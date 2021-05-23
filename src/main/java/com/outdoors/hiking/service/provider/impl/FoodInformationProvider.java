package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Nutrition;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;

import java.util.Map;

public class FoodInformationProvider implements InformationProvider {

    private static final double LITERS_OF_WATER_PER_HOUR = 0.75;
    private static final int EXPECTED_KM_PER_HOUR = 3;
    private static final int CALORIES_PER_HOUR = 600;

    private final Map<String, Integer> foods;

    public FoodInformationProvider(Map<String, Integer> foods) {
        this.foods = foods;
    }

    @Override
    public Recommendation provide(Recommendation rec, WeatherData weatherData, Input input) {
        rec.setNutrition(constructNutrition(input.getTripLength()));
        return rec;
    }

    private Nutrition constructNutrition(int tripLength) {
        int hours = tripLength / EXPECTED_KM_PER_HOUR;
        Nutrition nutrition = new Nutrition();
        nutrition.setWater(hours * LITERS_OF_WATER_PER_HOUR + "L");
        nutrition.setEstimated_calories(CALORIES_PER_HOUR * hours);
        nutrition.setFoods(foods);
        return nutrition;
    }
}