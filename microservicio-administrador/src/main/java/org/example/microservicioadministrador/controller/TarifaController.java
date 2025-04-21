package org.example.microservicioadministrador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.service.TarifaService;
import org.example.shareddto.DTO.entity.TarifaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;


    @Operation(summary = "Crear múltiples tarifas", description = "Este endpoint permite crear varias tarifas al mismo tiempo.")
    @ApiResponse(responseCode = "201", description = "Tarifas creadas exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para las tarifas.")
    @PostMapping("/crearTarifas")
    public ResponseEntity<List<Tarifa>> guardarTarifas(@RequestBody List<TarifaDTO> tarifasDTO) {
        List<Tarifa> tarifasGuardadas = tarifaService.saveAll(tarifasDTO);
        return new ResponseEntity<>(tarifasGuardadas, HttpStatus.CREATED);
    }


    @Operation(summary = "Crear tarifa", description = "Este endpoint permite crear una nueva tarifa.")
    @ApiResponse(responseCode = "201", description = "Tarifa creada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para la tarifa.")
    @PostMapping
    public ResponseEntity<Tarifa> crearTarifa(@RequestBody TarifaDTO tarifaDTO) {
        Tarifa createdTarifa = tarifaService.save(tarifaDTO);
        return new ResponseEntity<>(createdTarifa, HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener tarifa por ID", description = "Este endpoint permite obtener una tarifa utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Tarifa encontrada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada.")
    @GetMapping("/{id}")
    public ResponseEntity<TarifaDTO> obtenerTarifa(@PathVariable Long id) {
        TarifaDTO tarifaDTO = tarifaService.findById(id);
        return ResponseEntity.ok(tarifaDTO);
    }


    @Operation(summary = "Actualizar tarifa", description = "Este endpoint permite actualizar una tarifa existente.")
    @ApiResponse(responseCode = "200", description = "Tarifa actualizada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para la tarifa.")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada.")
    @PutMapping("/{id}")
    public ResponseEntity<TarifaDTO> actualizarTarifa(@PathVariable Long id, @RequestBody TarifaDTO tarifaDTO) {
        TarifaDTO updatedTarifa = tarifaService.update(id, tarifaDTO);
        return new ResponseEntity<>(updatedTarifa, HttpStatus.OK);
    }


    @Operation(summary = "Eliminar tarifa", description = "Este endpoint permite eliminar una tarifa utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Tarifa eliminada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable Long id) {
        tarifaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Verificar si tarifa existe", description = "Este endpoint permite verificar si una tarifa existe utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Tarifa encontrada o no encontrada.")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada.")
    @GetMapping("/tarifa/{id}")
    public boolean existeTarifa(@PathVariable("id") Long tarifaId) {
        return tarifaService.existeTarifa(tarifaId);
    }


    @Operation(summary = "Obtener todas las tarifas", description = "Este endpoint permite obtener una lista de todas las tarifas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de tarifas obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<TarifaDTO>> getAllTarifas() {
        List<TarifaDTO> tarifas = tarifaService.findAll();
        return new ResponseEntity<>(tarifas, HttpStatus.OK);
    }


    @Operation(summary = "Obtener última tarifa", description = "Este endpoint permite obtener la tarifa más reciente registrada en el sistema.")
    @ApiResponse(responseCode = "200", description = "Tarifa obtenida exitosamente.")
    @GetMapping("/actual")
    public ResponseEntity<TarifaDTO> obtenerUltimaTarifa() {
        TarifaDTO tarifaActual = tarifaService.obtenerTarifaActual();
        return new ResponseEntity<>(tarifaActual, HttpStatus.OK);
    }




}
