package org.example.microservicioadministrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class MicroservicioAdministradorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioAdministradorApplication.class, args);
    }

}
