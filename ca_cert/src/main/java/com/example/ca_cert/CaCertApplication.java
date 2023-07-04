package com.example.ca_cert;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication

public class CaCertApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaCertApplication.class, args);
    }


}
