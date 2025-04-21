package org.example.microservicioadministrador.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.service.ParadaService;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/paradas")
public class ParadaController {

    @Autowired
    private ParadaService paradaService;


    @Operation(summary = "Registrar nueva parada", description = "Este endpoint permite registrar una nueva parada con su latitud y longitud.")
    @ApiResponse(responseCode = "201", description = "Parada registrada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para la parada.")
    @PostMapping("/registrarParada")
    public ResponseEntity<Parada> crearParada(@RequestBody Parada parada) {
        Parada nuevaParada = paradaService.save(parada.getLatitud(), parada.getLongitud());
        return new ResponseEntity<>(nuevaParada, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener parada por ID", description = "Este endpoint permite obtener los detalles de una parada utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Parada encontrada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada.")
    @GetMapping("/{id}")
    public ResponseEntity<ParadaDTO> getParadaById(@PathVariable String id) {
        ParadaDTO parada = paradaService.findById(id);
        return new ResponseEntity<>(parada, HttpStatus.OK);
    }


    @Operation(summary = "Actualizar parada", description = "Este endpoint permite actualizar los detalles de una parada existente.")
    @ApiResponse(responseCode = "200", description = "Parada actualizada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para la parada.")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada.")
    @PutMapping("/{id}")
    public ResponseEntity<ParadaDTO> updateParada(@PathVariable String id, @RequestBody ParadaDTO paradaDTO) {
        ParadaDTO updatedParada = paradaService.update(id, paradaDTO);
        return new ResponseEntity<>(updatedParada, HttpStatus.OK);
    }


    @Operation(summary = "Eliminar parada", description = "Este endpoint permite eliminar una parada utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Parada eliminada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada.")
    @DeleteMapping("/quitarParada/{id}")
    public ResponseEntity<Void> quitarParada(@PathVariable String id) {
        paradaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Obtener todas las paradas", description = "Este endpoint permite obtener todas las paradas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<ParadaDTO>> getAllParadas() {
        List<ParadaDTO> paradas = paradaService.findAll();
        return new ResponseEntity<>(paradas, HttpStatus.OK);
    }


    @Operation(summary = "Eliminar todas las paradas", description = "Este endpoint permite eliminar todas las paradas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Todas las paradas han sido eliminadas.")
    @DeleteMapping("/eliminar-todas")
    public ResponseEntity<String> eliminarTodasLasParadas() {
        paradaService.eliminarTodasLasParadas();
        return ResponseEntity.ok("Todas las paradas han sido eliminadas");
    }
}
