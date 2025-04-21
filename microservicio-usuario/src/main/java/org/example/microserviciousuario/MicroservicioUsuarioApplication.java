package org.example.microserviciousuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioUsuarioApplication.class, args);
    }

}
