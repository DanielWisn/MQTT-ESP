package com.example.java.temperatureSensor.config;

import com.example.java.temperatureSensor.Temperature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Temperature> redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, Temperature> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        JacksonJsonRedisSerializer<Temperature> serializer =
                new JacksonJsonRedisSerializer<>(Temperature.class);

        template.setValueSerializer(serializer);
        return template;
    }
}