package com.weather.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherLatLongResponse  {
    
    private String timezone;
    private CurrentTemp main;
    private Weather[] weather;
    private boolean fromCache;


}




