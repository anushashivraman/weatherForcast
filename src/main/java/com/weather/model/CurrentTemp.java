package com.weather.model;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentTemp {
    private double temp;
    private Weather[] weather;
}
