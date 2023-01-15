package com.ting.nbfans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {


    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        return new SimpleClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory) {
        return new RestTemplate(httpClientFactory);
    }
}