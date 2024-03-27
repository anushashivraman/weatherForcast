package com.weather.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import com.weather.model.WeatherLatLongResponse;
import com.weather.model.WeatherResponse;

import com.weather.model.WeatherLatLongResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.core.ReactiveRedisOperations;


@Configuration
public class RedisConfig {

    @Bean
    ReactiveRedisOperations<String, WeatherLatLongResponse> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<WeatherLatLongResponse> serializer = new Jackson2JsonRedisSerializer<>(WeatherLatLongResponse.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, WeatherLatLongResponse> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, WeatherLatLongResponse> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
