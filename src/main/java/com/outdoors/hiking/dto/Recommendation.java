package com.outdoors.hiking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Recommendation {

    private Map<String, List<String>> gearInfo;

    private Map<String, String> tripInfo;

    private Map<String, String> weatherInfo;
}