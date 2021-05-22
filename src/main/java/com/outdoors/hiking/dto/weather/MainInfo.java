package com.outdoors.hiking.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainInfo {

    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double humidity;
}