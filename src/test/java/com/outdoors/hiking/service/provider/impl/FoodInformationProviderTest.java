package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Nutrition;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.provider.InformationProvider;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests covering {@link FoodInformationProvider}
 */
class FoodInformationProviderTest {

    private final InformationProvider provider = new FoodInformationProvider(new HashMap<>() {{
        put("Granola Bar", 300);
    }});

    @Test
    void shouldConstructNutrition() {

        Recommendation recommendation = provider.provide(new Recommendation(), new WeatherData(), new Input("vilnius", 25));

        Nutrition nutrition = recommendation.getNutrition();
        assertEquals("6.0L", nutrition.getWater());
        assertEquals(4800, nutrition.getEstimated_calories());
        assertFalse(nutrition.getFoods().isEmpty());
        assertEquals(1, nutrition.getFoods().size());
    }
}