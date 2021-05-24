package com.outdoors.hiking.service.provider.impl;

import com.outdoors.hiking.dto.Gear;
import com.outdoors.hiking.dto.Input;
import com.outdoors.hiking.dto.Recommendation;
import com.outdoors.hiking.dto.weather.MainInfo;
import com.outdoors.hiking.dto.weather.Weather;
import com.outdoors.hiking.dto.weather.WeatherData;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests covering {@link GearInformationProvider}
 */
public class GearInformationProviderTest {

    private GearInformationProvider provider = new GearInformationProvider(mockGearMap());

    @Test
    public void shouldThrowErrorEnumsAndKeysDoNotMatch() {
        //given
        HashMap<String, List<String>> gearMap = new HashMap<>();
        gearMap.put("DAY", Collections.emptyList());
        gearMap.put("NIGHT", Collections.emptyList());
        gearMap.put("COLD", Collections.emptyList());

        this.provider = new GearInformationProvider(gearMap);

        //then
        assertThrows(RuntimeException.class, provider::validateKeys);
    }

    @Test
    public void shouldValidateKeySensitivity() {
        //given
        Map<String, List<String>> gearMap = mockGearMap();
        Optional<String> entry = gearMap.entrySet().stream().findFirst().map(Map.Entry::getKey);
        if (entry.isPresent()) {
            gearMap.remove(entry.get());
            gearMap.put(entry.get().toLowerCase(), new ArrayList<>());

            this.provider = new GearInformationProvider(gearMap);

            //then
            assertThrows(RuntimeException.class, provider::validateKeys);
        }
    }

    @Test
    void shouldRecommendOnlyEssentialsAndOptionals() {
        //given
        WeatherData data = new WeatherData();
        data.setPopulated(false);
        Input input = new Input("vilnius", 15);

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, input);
        Map<String, List<String>> gearInfo = recommendation.getGearInfo();

        //then
        assertEquals(2, gearInfo.keySet().size());
        assertTrue(gearInfo.containsKey(Gear.ESSENTIALS.name()));
        assertTrue(gearInfo.containsKey(Gear.OPTIONAL.name()));
    }

    @Test
    void shouldRecommendForLongTrip() {
        //given
        WeatherData data = new WeatherData();
        data.setPopulated(false);

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("south dakota", 25));
        Map<String, List<String>> gearInfo = recommendation.getGearInfo();

        //then
        assertEquals(5, gearInfo.keySet().size());
        assertTrue(gearInfo.containsKey(Gear.ESSENTIALS.name()));
        assertTrue(gearInfo.containsKey(Gear.OPTIONAL.name()));
        assertTrue(gearInfo.containsKey(Gear.NIGHT.name()));
        assertTrue(gearInfo.containsKey(Gear.COLD.name()));
        assertTrue(gearInfo.containsKey(Gear.NAVIGATION.name()));
    }

    @Test
    void shouldRecommendForRain() {
        //given
        WeatherData data = new WeatherData();
        Weather weather = new Weather();
        weather.setMain("Rain");
        data.setWeather(Collections.singletonList(weather));

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("mumbai", 15));
        Map<String, List<String>> gearInfo = recommendation.getGearInfo();

        //then
        assertEquals(3, gearInfo.keySet().size());
        assertTrue(gearInfo.containsKey(Gear.ESSENTIALS.name()));
        assertTrue(gearInfo.containsKey(Gear.OPTIONAL.name()));
        assertTrue(gearInfo.containsKey(Gear.RAINY.name()));
    }

    @Test
    void shouldRecommendForAClearDay() {
        //given
        WeatherData data = new WeatherData();
        Weather weather = new Weather();
        weather.setMain("Clear");
        data.setWeather(Collections.singletonList(weather));

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("vilnius", 15));
        Map<String, List<String>> gearInfo = recommendation.getGearInfo();

        //then
        assertEquals(3, gearInfo.keySet().size());
        assertTrue(gearInfo.containsKey(Gear.ESSENTIALS.name()));
        assertTrue(gearInfo.containsKey(Gear.OPTIONAL.name()));
        assertTrue(gearInfo.containsKey(Gear.SUNNY.name()));
    }

    @Test
    void shouldRecommendForAShortColdTrip() {
        //given
        WeatherData data = new WeatherData();
        MainInfo mainInfo = new MainInfo();
        mainInfo.setTemp("5");
        data.setMain(mainInfo);

        //when
        Recommendation recommendation = provider.provide(new Recommendation(), data, new Input("delhi", 10));
        Map<String, List<String>> gearInfo = recommendation.getGearInfo();

        //then
        assertEquals(3, gearInfo.keySet().size());
        assertTrue(gearInfo.containsKey(Gear.ESSENTIALS.name()));
        assertTrue(gearInfo.containsKey(Gear.OPTIONAL.name()));
        assertTrue(gearInfo.containsKey(Gear.COLD.name()));
    }

    private Map<String, List<String>> mockGearMap() {
        return Arrays.stream(Gear.values())
                .map(Enum::name)
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.toMap(Function.identity(), p -> new ArrayList<>()));
    }
}