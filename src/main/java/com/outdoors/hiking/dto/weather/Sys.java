package com.outdoors.hiking.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sys {

    private String type;
    private String id;
    private String country;
    private String sunrise;
    private String sunset;
}