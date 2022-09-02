package com.horin.sentinel.service;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class SentinelServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SentinelServiceApplication.class, args);
  }

  @Bean
  @LoadBalanced
  @SentinelRestTemplate
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
