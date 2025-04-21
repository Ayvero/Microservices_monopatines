package org.example.microservicioadministrador.foreinClient;


import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009")
public interface MonopatinClient {

    @GetMapping("/api/monopatines/{id}")
    ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PostMapping("/api/monopatines")
    MonopatinDTO guardarMonopatin(@RequestBody MonopatinDTO monopatin);

    @DeleteMapping("/api/monopatines/{id}")
    ResponseEntity<Void> eliminarMonopatin(@PathVariable Long id);


    //KM  recorridos de un determinado Monopatin
    @GetMapping("/api/monopatines/{id}/totalKm")
    ResponseEntity<Double> getTotalKmRecorridos(@PathVariable Long id);

    @GetMapping("/api/monopatines/{id}/tiempoUsoConPausa")
    ResponseEntity<Long> obtenerTiempoUsoConPausa(@PathVariable("id") Long id);

    @GetMapping("/api/monopatines/{id}/tiempoUsoSinPausa")
    ResponseEntity<Long> obtenerTiempoUsoSinPausa(@PathVariable("id") Long id);

    @GetMapping("/api/monopatines/estado/reporte")
    Map<String, Long> obtenerReporteEstadoMonopatines();
}
