package com.github.asyu.homework.config;

import static com.github.asyu.homework.common.SpringProfiles.TEST;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@ActiveProfiles(TEST)
@Configuration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    @Container
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.0.11-alpine")
        .withExposedPorts(6379);

    static {
        redisContainer.start();
        System.setProperty("spring.data.redis.host", redisContainer.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(redisContainer.getFirstMappedPort()));
    }

}
