package org.example.microserviciomantenimiento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microserviciomantenimiento.service.MantenimientoService;
import org.example.shareddto.DTO.entity.MantenimientoDTO;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mantenimiento")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;



    @Operation(summary = "Registrar nuevo mantenimiento", description = "Este endpoint permite registrar un nuevo mantenimiento para un monopatín.")
    @ApiResponse(responseCode = "201", description = "Mantenimiento registrado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para el mantenimiento.")
    @PostMapping
    public ResponseEntity<MantenimientoDTO> registrarMantenimiento(@RequestBody MantenimientoDTO mantenimientoDTO) {
        MantenimientoDTO mantenimientoGuardado = mantenimientoService.registrarMantenimiento(mantenimientoDTO);
        return new ResponseEntity<>(mantenimientoGuardado, HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener mantenimiento por ID", description = "Este endpoint permite obtener un mantenimiento utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Mantenimiento encontrado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Mantenimiento no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> obtenerMantenimientoPorId(@PathVariable Long id) {
        MantenimientoDTO mantenimientoDTO = mantenimientoService.obtenerMantenimientoPorId(id);
        return ResponseEntity.ok(mantenimientoDTO);
    }


    @Operation(summary = "Actualizar mantenimiento", description = "Este endpoint permite actualizar un mantenimiento existente.")
    @ApiResponse(responseCode = "200", description = "Mantenimiento actualizado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para el mantenimiento.")
    @ApiResponse(responseCode = "404", description = "Mantenimiento no encontrado.")
    @PutMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> actualizarMantenimiento(@PathVariable Long id,
                                                                    @RequestBody MantenimientoDTO mantenimientoDTO) {
        MantenimientoDTO mantenimientoActualizado = mantenimientoService.actualizarMantenimiento(id, mantenimientoDTO);
        return ResponseEntity.ok(mantenimientoActualizado);
    }


    @Operation(summary = "Eliminar mantenimiento", description = "Este endpoint permite eliminar un mantenimiento utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Mantenimiento eliminado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Mantenimiento no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMantenimiento(@PathVariable Long id) {
        mantenimientoService.eliminarMantenimiento(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Obtener todos los mantenimientos", description = "Este endpoint permite obtener una lista de todos los mantenimientos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de mantenimientos obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<MantenimientoDTO>> obtenerTodosLosMantenimientos() {
        List<MantenimientoDTO> mantenimientos = mantenimientoService.obtenerTodosLosMantenimientos();
        return ResponseEntity.ok(mantenimientos);
    }

    //Punto a-Endpoint para generar reporte de uso de monopatines con o sin pausa
    @Operation(summary = "Generar reporte de uso de monopatines", description = "Este endpoint permite generar un reporte de uso de monopatines con o sin pausa.")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente.")
    @GetMapping("/reporteUso-monopatines")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteTiempos(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa) {

        boolean excludeSinPausa = !includeSinPausa;
        List<ReporteUsoDTO> reportes = mantenimientoService.generarReporteTiempos(excludeSinPausa);
        return ResponseEntity.ok(reportes);
    }


    @Operation(summary = "Actualizar estado de monopatines en mantenimiento", description = "Este endpoint permite actualizar el estado de los monopatines que están en mantenimiento.")
    @ApiResponse(responseCode = "200", description = "Estado de los monopatines actualizado exitosamente.")
    @ApiResponse(responseCode = "500", description = "Error interno al actualizar el estado.")
    @PutMapping("/actualizar-estado")
    public ResponseEntity<Void> actualizarEstadoMonopatinesEnMantenimiento(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa,
            @RequestParam(value = "maxKm", required = true) Double maxKm) {
        try {
            mantenimientoService.actualizarEstadoMonopatinesEnMantenimiento(includeSinPausa, maxKm);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Consultar mantenimientos de un monopatín", description = "Este endpoint permite consultar todos los mantenimientos realizados a un monopatín específico.")
    @ApiResponse(responseCode = "200", description = "Mantenimientos obtenidos exitosamente.")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado.")
    @GetMapping("/monopatin/{monopatinId}/mantenimientos")
    public ResponseEntity<List<MantenimientoDTO>> consultarMantenimientosPorMonopatin(@PathVariable Long monopatinId) {
        List<MantenimientoDTO> mantenimientosDTO = mantenimientoService.consultarMantenimientosPorMonopatin(monopatinId);
        return ResponseEntity.ok(mantenimientosDTO);
    }
}
