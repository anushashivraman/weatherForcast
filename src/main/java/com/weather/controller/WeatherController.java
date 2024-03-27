package com.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.weather.service.WeatherService;
import com.weather.model.WeatherLatLongResponse;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{zipCode}")
    public Mono<WeatherLatLongResponse> getWeatherForecast(@PathVariable String zipCode) {
        return weatherService.getWeatherForecast(zipCode);
    }
}
