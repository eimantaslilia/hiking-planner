package com.outdoors.hiking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Recommendation {

    private HashMap<String, List<String>> gearRecommendations;

    private HashMap<String, String> information;
}