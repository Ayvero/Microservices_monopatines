package org.example.microservicioviaje.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.service.PausaService;
import org.example.shareddto.DTO.entity.PausaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/pausas")
public class PausaController {

    @Autowired
    private PausaService pausaService;

    @Operation(summary = "Crear una nueva pausa", description = "Este endpoint permite crear una pausa para un viaje específico, con horas de inicio y fin.")
    @ApiResponse(responseCode = "201", description = "Pausa creada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, parámetros inválidos.")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    @PostMapping("/altaPausa/{viajeId}")
    public ResponseEntity<PausaDTO> crearPausa(
            @PathVariable Long viajeId,
            @RequestParam("horaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaInicio,
            @RequestParam("horaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaFin) {

        try {
            PausaDTO pausaDTO = new PausaDTO();
            pausaDTO.setHoraInicio(horaInicio);
            pausaDTO.setHoraFin(horaFin);
            pausaDTO.setViajeId(viajeId);
            PausaDTO nuevaPausaDTO = pausaService.altaPausa(pausaDTO.getViajeId(), pausaDTO.getHoraInicio(), pausaDTO.getHoraFin());

            return new ResponseEntity<>(nuevaPausaDTO, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener una pausa por ID", description = "Este endpoint permite obtener los detalles de una pausa usando su ID.")
    @ApiResponse(responseCode = "200", description = "Pausa obtenida exitosamente.")
    @ApiResponse(responseCode = "404", description = "Pausa no encontrada.")
    @GetMapping("/{pausaId}")
    public ResponseEntity<PausaDTO> obtenerPausa(@PathVariable Long pausaId) {
        PausaDTO pausa = pausaService.obtenerPausa(pausaId);
        return ResponseEntity.ok(pausa);
    }

    @Operation(summary = "Actualizar una pausa", description = "Este endpoint permite actualizar una pausa existente.")
    @ApiResponse(responseCode = "200", description = "Pausa actualizada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Pausa no encontrada.")
    @PutMapping("/{pausaId}")
    public ResponseEntity<PausaDTO> actualizarPausa(
            @PathVariable Long pausaId,
            @RequestBody PausaDTO pausaDTO) {

        PausaDTO updatedPausa = pausaService.actualizarPausa(
                pausaId,
                pausaDTO.getHoraInicio(),
                pausaDTO.getHoraFin()
        );
        return ResponseEntity.ok(updatedPausa);
    }

    @Operation(summary = "Eliminar una pausa", description = "Este endpoint permite eliminar una pausa específica usando su ID.")
    @ApiResponse(responseCode = "204", description = "Pausa eliminada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Pausa no encontrada.")
    @DeleteMapping("/{pausaId}")
    public ResponseEntity<Void> eliminarPausa(@PathVariable Long pausaId) {
        pausaService.eliminarPausa(pausaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todas las pausas", description = "Este endpoint devuelve una lista de todas las pausas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de pausas obtenida exitosamente.")
    @GetMapping
    public List<PausaDTO> obtenerTodasLasPausas() {
        return pausaService.obtenerTodasLasPausas();
    }
}
