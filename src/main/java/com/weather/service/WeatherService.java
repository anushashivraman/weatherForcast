package com.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.weather.model.WeatherResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import com.weather.model.WeatherLatLongResponse;

import com.weather.exception.WeatherApiException;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String api_key;

    @Value("${openweathermap.api.url}")
    private String api_url;

    @Value("${openweathermap.api.lat.url}")
    private String api_lat_url;

    private static final String CACHE_KEY_PREFIX = "weather::zipCode::";
    public static final Duration CACHE_EXPIRATION = Duration.ofMinutes(30);

    @Autowired
    private WebClient.Builder webClientBuilder;

    private ReactiveRedisOperations<String, WeatherLatLongResponse> reactiveRedisTemplate;

    public WeatherService(ReactiveRedisOperations<String, WeatherLatLongResponse> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<WeatherLatLongResponse> getWeatherForecast(String zipCode) {
        String cacheKey = CACHE_KEY_PREFIX + zipCode;
        return reactiveRedisTemplate.opsForValue().get(cacheKey)
                .flatMap(weatherResponse -> {
                    weatherResponse.setFromCache(true);
                    return Mono.just(weatherResponse);
                })
                .switchIfEmpty(fetchWeatherAndCache(zipCode, cacheKey));
    }

    private Mono<WeatherLatLongResponse> fetchWeatherAndCache(String zipCode, String cacheKey) {
        return callWeatherApi(zipCode)
                .doOnNext(weatherResponse -> reactiveRedisTemplate.opsForValue()
                        .set(cacheKey, weatherResponse, CACHE_EXPIRATION).subscribe());
    }

    public Mono<WeatherLatLongResponse> callWeatherApi(String zipCode) {
        String url = api_url
                + "?zip={zipCode}&appid={apiKey}".replace("{zipCode}", zipCode).replace("{apiKey}", api_key);

        return webClientBuilder.build().get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .flatMap(weatherResponse -> {
                    double latitude = weatherResponse.getLat();
                    double longitude = weatherResponse.getLon();

                    if (latitude == 0 || longitude == 0) {
                        throw new WeatherApiException("Latitude and Longitude not found");
                    }

                    String urlLat = api_lat_url
                            + "?lat={lat}&lon={long}&appid={apiKey}".replace("{lat}", String.valueOf(latitude))
                                    .replace("{long}", String.valueOf(longitude)).replace("{apiKey}", api_key);

                    
                    return webClientBuilder.build().get()
                            .uri(urlLat)
                            .retrieve()
                            .bodyToMono(WeatherLatLongResponse.class);
                })
                .onErrorResume(WeatherApiException.class, ex -> {
                    
                    return Mono.error(new WeatherApiException("Exception in API: " + ex.getMessage()));
                })
                .onErrorResume(Exception.class, ex -> {
                   
                    return Mono.error(new WeatherApiException("Exception occurred: " + ex.getMessage()));
                });
    }

}
