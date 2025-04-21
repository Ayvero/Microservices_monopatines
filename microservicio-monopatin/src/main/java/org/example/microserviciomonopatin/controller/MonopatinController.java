package org.example.microserviciomonopatin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.microserviciomonopatin.service.MonopatinMapper;
import org.example.microserviciomonopatin.service.MonopatinService;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService monopatinService;
    @Autowired
    private MonopatinMapper monopatinMapper;


    @Operation(summary = "Crear un nuevo monopatín",
            description = "Crea un nuevo monopatín en el sistema a partir de los datos proporcionados en el DTO.")
    @ApiResponse(responseCode = "201", description = "Monopatín creado con éxito")
    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody MonopatinDTO monopatinDTO) {
        Monopatin savedMonopatin = monopatinService.save(monopatinDTO);
        return new ResponseEntity<>(savedMonopatin, HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener monopatín por ID",
            description = "Recupera los detalles de un monopatín específico por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id) {
       MonopatinDTO monopatin = monopatinService.findById(id);
        return new ResponseEntity<>(monopatin, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar monopatín",
            description = "Actualiza los detalles de un monopatín existente por su ID.")
    @ApiResponse(responseCode = "200", description = "Monopatín actualizado con éxito")
    @PutMapping("/{id}")
    public ResponseEntity<MonopatinDTO> updateMonopatin(@PathVariable Long id, @RequestBody MonopatinDTO monopatinDTO) {
        MonopatinDTO updatedMonopatin = monopatinService.update(id, monopatinDTO);
        return new ResponseEntity<>(updatedMonopatin, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar monopatín",
            description = "Elimina un monopatín del sistema por su ID.")
    @ApiResponse(responseCode = "204", description = "Monopatín eliminado con éxito")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonopatin(@PathVariable Long id) {
        monopatinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Crear varios monopatines",
            description = "Crea múltiples monopatines en el sistema a partir de una lista de DTOs.")
    @ApiResponse(responseCode = "201", description = "Monopatines creados con éxito")
    @PostMapping("/crearMonopatines")
    public ResponseEntity<List<MonopatinDTO>> crearMonopatines(@RequestBody List<MonopatinDTO> monopatinDTOs) {
        List<Monopatin> savedMonopatines = monopatinService.saveAll(monopatinDTOs);
        List<MonopatinDTO> savedMonopatinesDTOs = savedMonopatines.stream()
                .map(monopatinMapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(savedMonopatinesDTOs, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los monopatines",
            description = "Recupera una lista de todos los monopatines disponibles en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de monopatines recuperada con éxito")
    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> getAllMonopatines() {
        List<MonopatinDTO> monopatines = monopatinService.findAll();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @Operation(summary = "Verificar si el monopatín existe",
            description = "Verifica si un monopatín existe en el sistema dado su ID.")
    @ApiResponse(responseCode = "200", description = "Estado de existencia del monopatín")
    @GetMapping("/monopatin/{id}")
    public boolean existeMonopatin(@PathVariable("id") Long monopatinId) {
        return monopatinService.existeMonopatin(monopatinId);
    }

    @Operation(summary = "Generar reporte de uso de monopatines",
            description = "Genera un reporte de uso de monopatines para su análisis.")
    @ApiResponse(responseCode = "200", description = "Reporte de uso generado con éxito")
    @GetMapping("/reporteUso")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporte() {
        List<ReporteUsoDTO> reportesUso = monopatinService.generarReporteUso();
        return ResponseEntity.ok(reportesUso);
    }

    @Operation(summary = "Generar reporte por kilómetros recorridos",
            description = "Genera un reporte de uso de monopatines ordenado por los kilómetros recorridos.")
    @ApiResponse(responseCode = "200", description = "Reporte por kilómetros generado con éxito")
    @GetMapping("/reporteUso/km")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteKm() {
        List<ReporteUsoDTO> reportes = monopatinService.generarReportePorKilometros();
        return ResponseEntity.ok(reportes);
    }

    // Punto a- Endpoint para obtener reporte de tiempos con un parámetro opcional para incluir o excluir tiempos con pausa
    @Operation(summary = "Generar reporte filtrado de tiempos de uso",
            description = "Genera un reporte de tiempos de uso, con un filtro opcional para incluir o excluir los tiempos con pausa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte filtrado de tiempos generado con éxito")
    })
    @GetMapping("/reporteUso/filtro-tiempos")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteTiempos(
            @Parameter(description = "Incluir tiempos sin pausa en el reporte")
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa) {

        boolean excludeSinPausa = !includeSinPausa;
        List<ReporteUsoDTO> reportes = monopatinService.generarReporteTiempos(excludeSinPausa);
        return ResponseEntity.ok(reportes);
    }


    // Punto e-Endpoint para obtener la cantidad de monopatines en operación y mantenimiento
    @Operation(summary = "Obtener reporte de estado de los monopatines",
            description = "Obtiene un reporte con la cantidad de monopatines en operación y en mantenimiento.")
    @ApiResponse(responseCode = "200", description = "Reporte de estado generado con éxito")
    @GetMapping("/estado/reporte")
    public ResponseEntity<Map<String, Long>> obtenerReporteEstadoMonopatines() {
        Map<String, Long> reporte = monopatinService.obtenerCantidadMonopatinesPorEstado();
        return ResponseEntity.ok(reporte);
    }
}
