package com.outdoors.hiking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Nutrition {

    private String water;

    private int estimated_calories;

    private Map<String, Integer> foods;
}