package com.example.crash.config;

import com.example.crash.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfiguration {
    @Bean
    RedisConnectionFactory redisConnectionFactory(
            @Value("${redis.host}") String redisHost,
            @Value("${redis.port}") int redisPort
    ){
        var config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, UserEntity> userEntityRedisTemplate(
            RedisConnectionFactory redisConnectionFactory){

        var template = new RedisTemplate<String, UserEntity>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<UserEntity>(UserEntity.class));

        return template;
    }

}
