package org.example.microservicioviaje.foreinClient;

import org.example.shareddto.DTO.entity.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "microservicio-administrador-tarifa", url = "http://localhost:8007")
public interface TarifaClient {

    @GetMapping("/api/tarifas/actual")
    ResponseEntity<TarifaDTO> obtenerUltimaTarifa();


}
