package com.example.zomato.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

        @Value("${SPRING_REDIS_HOST}")
        private String redisHost;

        @Value("${SPRING_REDIS_PORT}")
        private int redisPort;

        @Value("${SPRING_REDIS_USERNAME}")
        private String redisUsername;

        @Value("${SPRING_REDIS_PASSWORD}")
        private String redisPassword;

        @Value("${SPRING_REDIS_SSL_ENABLED:true}")
        private boolean redisSslEnabled;

        // LettuceConnectionFactory: creates the Redis connection.
        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {
                RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
                config.setHostName(redisHost);
                config.setPort(redisPort);
                config.setUsername(redisUsername);
                config.setPassword(RedisPassword.of(redisPassword));
                config.setDatabase(0);

                LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder() // SSL is required for
                                                                                               // RedisLabs
                                .build();
                return new LettuceConnectionFactory(config, clientConfig);
        }

        // StringRedisTemplate: provides simple, ready-to-use Redis operations.
        @Bean
        public StringRedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory) {
                return new StringRedisTemplate(connectionFactory);
        }

}
