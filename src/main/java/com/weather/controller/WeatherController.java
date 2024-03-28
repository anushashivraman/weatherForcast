package com.weather.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.model.WeatherLatLongResponse;
import com.weather.service.WeatherService;

import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private final static String INDIA_COUNTRY_CODE="IN";

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public Mono<ResponseEntity<WeatherLatLongResponse>> getWeatherForecast(@RequestParam String zipCode, @RequestParam(required = false) String countryCode) {
        if(countryCode==null)
            countryCode=INDIA_COUNTRY_CODE;

        String requestParam = zipCode+","+countryCode;
        return weatherService.getWeatherForecast(requestParam)
                .map(weatherResponse -> ResponseEntity.ok().body(weatherResponse));
    }
}
