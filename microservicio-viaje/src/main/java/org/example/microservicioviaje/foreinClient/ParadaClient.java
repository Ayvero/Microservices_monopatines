package org.example.microservicioviaje.foreinClient;

import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-administrador-parada", url = "http://localhost:8007")
public interface ParadaClient {

    @GetMapping("/api/paradas/{id}")
    ParadaDTO obtenerParada(@PathVariable String id);
}
