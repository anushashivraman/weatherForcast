package com.weather.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstrutor
@NoArgsConstrutor
public class WeatherResponse {

    private String zip;
    private String name;
    private double lat;
    private double lon;
    private String country;

}
