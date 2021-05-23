package com.outdoors.hiking.dto.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class WeatherData {

    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private MainInfo main;
    private String visibility;
    private Wind wind;
    private Cloud clouds;
    private String dt;
    private Sys sys;
    private String timezone;
    private String id;
    private String name;
    private String cod;
    private String message;
}