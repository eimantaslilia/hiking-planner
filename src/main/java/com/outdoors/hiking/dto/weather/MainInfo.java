package com.outdoors.hiking.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainInfo {

    private String temp;
    private String feels_like;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
}