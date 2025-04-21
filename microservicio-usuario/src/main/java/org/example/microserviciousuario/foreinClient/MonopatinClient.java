package org.example.microserviciousuario.foreinClient;

import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009")
public interface MonopatinClient {


    @GetMapping ("/api/monopatines")
    ResponseEntity<List<MonopatinDTO>> getAllMonopatines();
}
