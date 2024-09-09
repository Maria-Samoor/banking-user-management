package com.exalt.training.users.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for defining beans related to user services.
 * This class sets up a RestTemplate bean with load balancing support.
 */
@Configuration
public class UserConfig {

    /**
     * Bean definition for RestTemplate with load balancing.
     *
     * @return a LoadBalanced RestTemplate instance for making HTTP requests.
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
