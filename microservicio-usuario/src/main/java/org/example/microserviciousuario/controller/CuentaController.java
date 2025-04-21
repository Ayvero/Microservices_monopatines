package org.example.microserviciousuario.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microserviciousuario.entity.Cuenta;
import org.example.microserviciousuario.exception.CuentaNotFoundException;
import org.example.microserviciousuario.service.CuentaService;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;


    @Operation(summary = "Crear múltiples cuentas", description = "Este endpoint permite crear varias cuentas al mismo tiempo.")
    @ApiResponse(responseCode = "201", description = "Cuentas creadas exitosamente.")
    @PostMapping("/crearCuentas")
    public ResponseEntity<List<Cuenta>> crearCuentas(@RequestBody List<CuentaDTO> cuentasDTO) {
        List<Cuenta> cuentasCreadas = cuentaService.agregarCuentas(cuentasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentasCreadas);
    }

    @Operation(summary = "Crear cuenta", description = "Este endpoint permite crear una cuenta de usuario.")
    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente.")
    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody CuentaDTO cuentaDTO) {
        Cuenta savedCuenta = cuentaService.save(cuentaDTO);
        return new ResponseEntity<>(savedCuenta, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener cuenta por ID", description = "Este endpoint permite obtener los detalles de una cuenta utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Cuenta encontrada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id) {
        CuentaDTO cuenta = cuentaService.findById(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todas las cuentas", description = "Este endpoint permite obtener una lista de todas las cuentas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAllCuentas() {
        List<CuentaDTO> cuentas = cuentaService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar cuenta", description = "Este endpoint permite actualizar los detalles de una cuenta existente.")
    @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuentaDTO);
        return ResponseEntity.ok(updatedCuenta);
    }

    @Operation(summary = "Eliminar cuenta", description = "Este endpoint permite eliminar una cuenta utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Verificar existencia de cuenta", description = "Este endpoint permite verificar si una cuenta existe utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Cuenta existe.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @GetMapping("/cuenta/{id}")
    public boolean existeCuenta(@PathVariable("id") Long cuentaId) {
        return cuentaService.existeCuenta(cuentaId);
    }


    @PostMapping("/{cuentaId}/usuarios/{usuarioId}")
    public ResponseEntity<Void> agregarUsuarioACuenta(@PathVariable Long cuentaId, @PathVariable Long usuarioId) {
        cuentaService.asociarUsuarioACuenta(cuentaId, usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para descontar saldo de una cuenta
    @Operation(summary = "Descontar saldo de cuenta", description = "Este endpoint permite descontar una cantidad específica de dinero de una cuenta.")
    @ApiResponse(responseCode = "200", description = "Saldo descontado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @ApiResponse(responseCode = "400", description = "Monto inválido para descontar.")
    @PutMapping("/{id}/descontar")
    public ResponseEntity<String> descontarSaldo(@PathVariable Long id, @RequestParam double monto) {
        try {
            String response = cuentaService.descontarSaldo(id, monto);
            return ResponseEntity.ok(response);
        } catch (CuentaNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para anular una cuenta
    @Operation(summary = "Anular cuenta", description = "Este endpoint permite anular una cuenta utilizando su ID y los detalles actualizados de la cuenta.")
    @ApiResponse(responseCode = "204", description = "Cuenta anulada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @PutMapping("/{cuentaId}/anular")
    public ResponseEntity<Void> anularCuenta(@PathVariable Long cuentaId, @RequestBody CuentaDTO cuentaDTO) {
        cuentaService.anularCuenta(cuentaId, cuentaDTO);

        return ResponseEntity.noContent().build();
    }

}