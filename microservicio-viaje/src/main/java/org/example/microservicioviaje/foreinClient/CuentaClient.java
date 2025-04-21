package org.example.microservicioviaje.foreinClient;

import org.example.microserviciousuario.entity.Cuenta;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8006")
public interface CuentaClient {

    @GetMapping("/api/cuentas/{id}")
    ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id);

    @PutMapping("/api/cuentas/{id}/descontar")
    ResponseEntity<String> descontarSaldo(@PathVariable Long id, @RequestParam double monto);

    @PutMapping("/api/cuentas/{id}")
    ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO);

    @GetMapping("/api/cuentas/cuenta/{id}")
    boolean existeCuenta(@PathVariable("id") Long cuentaId);
}
