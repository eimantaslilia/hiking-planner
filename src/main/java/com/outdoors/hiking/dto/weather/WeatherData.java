package com.outdoors.hiking.dto.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherData {

    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private MainInfo main;
    private Long visibility;
    private Wind wind;
    private Cloud clouds;
    private Long dt;
    private Sys sys;
    private Long timezone;
    private Long id;
    private String name;
    private String cod;
    private String message;
}