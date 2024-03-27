package com.weather.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstrutor
@NoArgsConstrutor
public class Weather {
    private String main;
    private String description;
}
