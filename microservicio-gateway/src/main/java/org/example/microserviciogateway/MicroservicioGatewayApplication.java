package org.example.microserviciogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class MicroservicioGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioGatewayApplication.class, args);
    }

}
