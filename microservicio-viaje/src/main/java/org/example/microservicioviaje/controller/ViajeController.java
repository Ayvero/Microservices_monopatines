package org.example.microservicioviaje.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.microservicioviaje.entity.Viaje;
import org.example.microservicioviaje.service.ViajeService;
import org.example.shareddto.DTO.ReporteMonopatinDTO;
import org.example.shareddto.DTO.entity.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;


    @Operation(summary = "Crear un nuevo viaje",
            description = "Este endpoint permite crear un nuevo viaje a partir de la información proporcionada.")
    @ApiResponse(responseCode = "201", description = "Viaje creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, datos inválidos.")
    @PostMapping
    public ResponseEntity<ViajeDTO> crearViaje(@Valid @RequestBody ViajeDTO viajeDTO) {
        ViajeDTO viajeCreado = viajeService.guardarViaje(viajeDTO);
        return new ResponseEntity<>(viajeCreado, HttpStatus.CREATED);
    }


    @Operation(summary = "Iniciar un viaje con Monopatín y Cuenta de usuario",
            description = "Este endpoint permite iniciar un viaje especificando un monopatín y una cuenta de usuario.")
    @ApiResponse(responseCode = "201", description = "Viaje iniciado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, ID de monopatín o cuenta no válidos.")
    @PostMapping("/iniciar/monopatin/{monopatinId}/cuenta/{cuentaId}")
    public ResponseEntity<Viaje> iniciarViaje(@PathVariable Long monopatinId, @PathVariable Long cuentaId) {
        Viaje createdViaje = viajeService.iniciarViaje(monopatinId, cuentaId);
        return new ResponseEntity<>(createdViaje, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un viaje por su ID",
            description = "Este endpoint devuelve los detalles de un viaje específico utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Viaje encontrado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> obtenerViajePorId(@PathVariable Long id) {
        ViajeDTO viaje = viajeService.obtenerViajePorId(id);
        return ResponseEntity.ok(viaje);
    }

    @Operation(summary = "Actualizar un viaje",
            description = "Este endpoint permite actualizar la información de un viaje existente.")
    @ApiResponse(responseCode = "200", description = "Viaje actualizado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, datos inválidos.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @PutMapping("/{id}")
    public ResponseEntity<ViajeDTO> updateViaje(@PathVariable Long id, @Valid @RequestBody ViajeDTO viajeDTO) {
        System.out.println("Recibiendo datos: " + viajeDTO);  // Agrega esto para ver qué datos estás recibiendo
        try {
            ViajeDTO updatedViaje = viajeService.updateViaje(id, viajeDTO);
            return ResponseEntity.ok(updatedViaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @Operation(summary = "Eliminar un viaje",
            description = "Este endpoint permite eliminar un viaje existente especificado por su ID.")
    @ApiResponse(responseCode = "204", description = "Viaje eliminado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        viajeService.deleteViaje(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Dar de alta varios viajes",
            description = "Este endpoint permite crear varios viajes a la vez.")
    @ApiResponse(responseCode = "200", description = "Viajes creados exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, datos inválidos.")
    @PostMapping("/altaViajes")
    public ResponseEntity<List<ViajeDTO>> altaViajes(@Valid@RequestBody List<ViajeDTO> viajeDTOs) {
        List<ViajeDTO> viajesGuardados = viajeService.altaViajes(viajeDTOs);
        return ResponseEntity.ok(viajesGuardados);
    }

    @Operation(summary = "Obtener todos los viajes",
            description = "Este endpoint devuelve una lista de todos los viajes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<ViajeDTO>> obtenerViajes() {
        List<ViajeDTO> viajes = viajeService.obtenerViajes();
        return ResponseEntity.ok(viajes);
    }

    @Operation(summary = "Pausar un viaje",
            description = "Este endpoint permite pausar un viaje específico utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Viaje pausado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @PostMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarViaje(@PathVariable Long id) {
        viajeService.pausarViaje(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reanudar un viaje",
            description = "Este endpoint permite reanudar un viaje previamente pausado.")
    @ApiResponse(responseCode = "200", description = "Viaje reanudado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @PostMapping("/{id}/reanudar")
    public ResponseEntity<Void> reanudarViaje(@PathVariable Long id) {
        viajeService.reanudarViaje(id);
        return ResponseEntity.ok().build();
    }


    // Endpoint para ubicar un monopatín en una parada
    @Operation(summary = "Ubicar un monopatín en una parada",
            description = "Este endpoint permite ubicar un monopatín en una parada al finalizar un viaje.")
    @ApiResponse(responseCode = "204", description = "Monopatín ubicado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje, monopatín o parada no encontrados.")
    @PostMapping("/finalizar/{viajeId}/monopatin/{monopatinId}/parada/{paradaId}")
    public ResponseEntity<Void> ubicarMonopatinEnParada(
            @PathVariable Long viajeId,
            @PathVariable Long monopatinId,
            @PathVariable String paradaId) {
        viajeService.finalizarViaje(viajeId, monopatinId, paradaId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Calcular el costo de un viaje",
            description = "Este endpoint permite calcular el costo de un viaje específico utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Costo calculado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @GetMapping("/{id}/costo")
    public ResponseEntity<Double> calcularCostoDelViaje(@PathVariable Long id) {
        double costo = viajeService.calcularCostoDelViaje(id);
        return ResponseEntity.ok(costo);
    }

    @Operation(summary = "Calcular el tiempo de uso de un viaje",
            description = "Este endpoint permite calcular el tiempo de uso de un viaje utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Tiempo de uso calculado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Error con las fechas del viaje.")
    @ApiResponse(responseCode = "404", description = "Viaje no encontrado.")
    @GetMapping("/{id}/tiempo-uso")
    public ResponseEntity<Long> calcularTiempoUso(@PathVariable Long id) {
        try {
           long tiempoUso = viajeService.calcularTiempoUso(id);
            return ResponseEntity.ok(tiempoUso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 si hay un problema con las fechas
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 si el viaje no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 500 para otros errores
        }
    }

    // Endpoint para obtener el reporte de monopatines con más de X viajes en un año
    @Operation(summary = "Obtener reporte de monopatines con más de X viajes en un año",
            description = "Este endpoint devuelve los monopatines que tienen más de un número determinado de viajes en un año específico.")
    @ApiResponse(responseCode = "200", description = "Reporte de monopatines obtenido exitosamente.")
    @GetMapping("/reporte/monopatines")
    public ResponseEntity<List<ReporteMonopatinDTO>> obtenerMonopatinesConMasDeXViajes(
            @RequestParam("anio") int anio,
            @RequestParam("minViajes") long minViajes) {

        List<ReporteMonopatinDTO> monopatines = viajeService.obtenerMonopatinesConMasDeXViajes(anio, minViajes);

        return ResponseEntity.ok(monopatines);
    }

    // Endpoint para obtener el total facturado en un determinado anio en un rango de meses
    @Operation(summary = "Obtener el total facturado en un determinado año y rango de meses",
            description = "Este endpoint devuelve el total facturado en un año específico dentro de un rango de meses.")
    @ApiResponse(responseCode = "200", description = "Total facturado obtenido exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, por ejemplo, parámetros de fecha inválidos.")
    @GetMapping("/facturado")
    public ResponseEntity<Double> obtenerTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {
        Double totalFacturado = viajeService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(totalFacturado);
    }

    @Operation(summary = "Obtener los viajes realizados por un monopatín",
            description = "Este endpoint devuelve una lista de viajes realizados por un monopatín específico, identificado por su ID.")
    @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida exitosamente.")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado.")
    @GetMapping("/viajesPor/{monopatinId}")
    public ResponseEntity<List<ViajeDTO>> obtenerViajesPorMonopatinId(@PathVariable long monopatinId) {
        List<ViajeDTO> viajes = viajeService.obtenerViajesPorMonopatinId(monopatinId);
        return ResponseEntity.ok(viajes);
    }



}
