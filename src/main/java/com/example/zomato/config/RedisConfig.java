package com.example.zomato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    

// LettuceConnectionFactory: creates the Redis connection.    
@Bean
public LettuceConnectionFactory redisConnectionFactory(){
    return new LettuceConnectionFactory();
}

//StringRedisTemplate: provides simple, ready-to-use Redis operations.
@Bean
public StringRedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory){
    return new StringRedisTemplate(connectionFactory);
}

}
