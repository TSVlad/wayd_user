package ru.tsvlad.wayd_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WaydUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaydUserApplication.class, args);
    }

}
