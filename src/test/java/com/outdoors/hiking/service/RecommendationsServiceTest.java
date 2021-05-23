package com.outdoors.hiking.service;

import com.outdoors.hiking.dto.Gear;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecommendationsServiceTest {

    private static final String GEAR_MAP = "gearMap";
    private final RecommendationsService service = new RecommendationsService();

    @Test
    public void shouldSuccessfullyValidateKeys() {
        //given
        Map<String, List<String>> gearMap = collectEnums();

        ReflectionTestUtils.setField(service,GEAR_MAP, gearMap);

        //then
        service.validateKeys();
    }

    @Test
    public void shouldThrowErrorEnumsAndKeysDoNotMatch() {
        //given
        HashMap<String, List<String>> gearMap = new HashMap<>();
        gearMap.put("DAY", Collections.emptyList());
        gearMap.put("NIGHT", Collections.emptyList());
        gearMap.put("COLD", Collections.emptyList());

        ReflectionTestUtils.setField(service,GEAR_MAP, gearMap);

        //then
        assertThrows(RuntimeException.class, service::validateKeys);
    }

    @Test
    public void shouldValidateKeySensitivity() {
        //given
        Map<String, List<String>> gearMap = collectEnums();
        Optional<String> entry = gearMap.entrySet().stream().findFirst().map(Map.Entry::getKey);
        if (entry.isPresent()) {
            gearMap.remove(entry.get());
            gearMap.put(entry.get().toLowerCase(), new ArrayList<>());

            ReflectionTestUtils.setField(service, GEAR_MAP, gearMap);

            //then
            assertThrows(RuntimeException.class, service::validateKeys);
        }
    }

    private Map<String, List<String>> collectEnums() {
        return Arrays.stream(Gear.values())
                .map(Enum::name)
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.toMap(Function.identity(), p -> new ArrayList<>()));
    }
}