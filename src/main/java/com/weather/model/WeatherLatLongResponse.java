package com.weather.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstrutor
@NoArgsConstrutor
public class WeatherLatLongResponse {
    
    private String timezone;
    private double temp;
    private CurrentTemp current;


}




