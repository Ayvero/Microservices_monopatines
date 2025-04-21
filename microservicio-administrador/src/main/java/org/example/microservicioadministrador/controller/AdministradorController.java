package org.example.microservicioadministrador.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microservicioadministrador.service.AdministradorService;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteFacturadoDTO;
import org.example.shareddto.DTO.ReporteMonopatinesXviajesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;


    // Punto b- Endpoint para anular una cuenta
    @Operation(summary = "Anular cuenta", description = "Este endpoint permite anular una cuenta utilizando el ID de la cuenta.")
    @ApiResponse(responseCode = "204", description = "Cuenta anulada exitosamente.")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    @PutMapping("/cuenta/{cuentaId}/anular")
    public ResponseEntity<Void> anularCuenta(@PathVariable Long cuentaId) {
        administradorService.anularCuenta(cuentaId);
        return ResponseEntity.noContent().build();  // Devuelve un HTTP 204 sin contenido
    }

    // Punto c -Endpoint para consultar el reporte de monopatines con más de X viajes
    @Operation(summary = "Obtener reporte de monopatines con más de X viajes", description = "Este endpoint consulta el reporte de monopatines que tienen más de un número específico de viajes.")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Parámetros incorrectos.")
    @GetMapping("/reporte/monopatines")
    public ResponseEntity<ReporteMonopatinesXviajesDTO> obtenerReporteMonopatines(
            @RequestParam(value = "anio") int anio,
            @RequestParam(value = "minViajes") long minViajes) {

        ReporteMonopatinesXviajesDTO reporte = administradorService.obtenerReporteMonopatines(anio, minViajes);

        return ResponseEntity.ok(reporte);
    }

    //Punto d- Endpoint para consultar el totall facturado en un rango de meses
    @Operation(summary = "Generar reporte de facturado", description = "Este endpoint genera un reporte del total facturado en un rango de meses.")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Parámetros incorrectos.")
    @GetMapping("/reporte/facturado")
    public ResponseEntity<ReporteFacturadoDTO> generarReporteFacturado(
            @RequestParam("anio") int anio,
            @RequestParam("mesInicio") int mesInicio,
            @RequestParam("mesFin") int mesFin) {

        ReporteFacturadoDTO reporte = administradorService.generarReporteFacturado(anio, mesInicio, mesFin);

        return ResponseEntity.ok(reporte);
    }

    //Puento e- Endpoint para consultar la cantidad de monopatines en operacion vs en mantenimiento
    @Operation(summary = "Consultar estado de monopatines", description = "Este endpoint consulta el estado de los monopatines, mostrando cuántos están en operación y cuántos en mantenimiento.")
    @ApiResponse(responseCode = "200", description = "Estado de monopatines obtenido exitosamente.")
    @GetMapping("/estado-monopatines")
    public ResponseEntity<Map<String, Long>> obtenerEstadoMonopatines() {
        Map<String, Long> reporte = administradorService.consultarEstadoMonopatines();
        return ResponseEntity.ok(reporte);
    }

    //Punto f- Endpoint para realizar ajuste de precios a partir de cierta fecha
    @Operation(summary = "Ajustar tarifas", description = "Este endpoint permite realizar ajustes en las tarifas a partir de una fecha y aplicar un porcentaje base y extra.")
    @ApiResponse(responseCode = "200", description = "Ajuste de tarifas realizado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Error al realizar el ajuste de tarifas.")
    @PostMapping("/ajustes/tarifas")
    public ResponseEntity<String> ajustarTarifas(@RequestParam LocalDate fechaAjuste,
                                                 @RequestParam double porcentajeBase,
                                                 @RequestParam double porcentajeExtra) {
        try {
            administradorService.ajustarTarifas(fechaAjuste, porcentajeBase, porcentajeExtra);
            return ResponseEntity.ok("Ajuste de tarifas realizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al ajustar tarifas: " + e.getMessage());
        }
    }


    // Endpoint para agregar un monopatín
    @Operation(summary = "Agregar monopatín", description = "Este endpoint permite agregar un monopatín al sistema.")
    @ApiResponse(responseCode = "200", description = "Monopatín agregado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos para el monopatín.")
    @PostMapping("/monopatines")
    public ResponseEntity<MonopatinDTO> agregarMonopatin(@RequestBody MonopatinDTO monopatin) {
        MonopatinDTO nuevoMonopatin = administradorService.agregarMonopatin(monopatin);
        return ResponseEntity.ok(nuevoMonopatin);
    }

    // Endpoint para eliminar un monopatín
    @Operation(summary = "Eliminar monopatín", description = "Este endpoint permite eliminar un monopatín del sistema.")
    @ApiResponse(responseCode = "204", description = "Monopatín eliminado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado.")
    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<Void> quitarMonopatin(@PathVariable Long id) {
        administradorService.quitarMonopatin(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener un monopatín por ID
    @Operation(summary = "Obtener monopatín por ID", description = "Este endpoint permite obtener los detalles de un monopatín utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Monopatín encontrado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado.")
    @GetMapping("/monopatines/{id}")
    public ResponseEntity<MonopatinDTO> obtenerMonopatin(@PathVariable Long id) {
        MonopatinDTO monopatin = administradorService.obtenerMonopatin(id);
        return ResponseEntity.ok(monopatin);
    }
}
