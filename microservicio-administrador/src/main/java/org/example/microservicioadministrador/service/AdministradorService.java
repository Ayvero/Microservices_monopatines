package org.example.microservicioadministrador.service;

import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.foreinClient.CuentaClient;
import org.example.microservicioadministrador.foreinClient.MonopatinClient;
import org.example.microservicioadministrador.foreinClient.ViajeClient;
import org.example.microservicioadministrador.repository.ParadaRepository;
import org.example.microservicioadministrador.repository.TarifaRepository;
import org.example.shareddto.DTO.*;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.entity.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministradorService {

    @Autowired
    private ParadaRepository paradaRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private CuentaClient cuentaClient;

    @Autowired
    private MonopatinClient monopatinClient;

    @Autowired
    private ViajeClient viajeClient;

    //Punto b- Como administrador quiero poder anular cuentas
    public void anularCuenta(Long cuentaId) {
        ResponseEntity<CuentaDTO> response = cuentaClient.getCuenta(cuentaId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            CuentaDTO cuenta = response.getBody();
            cuenta.setAnulada(true);
            cuentaClient.anularCuenta(cuentaId, cuenta);  // Esto actualizará la cuenta en el microservicio Usuario

        } else {
            throw new RuntimeException("No se pudo obtener la cuenta con ID: " + cuentaId);
        }
    }

    // Punto c- Monopatines con mas de X viajes en cierto anio
    public ReporteMonopatinesXviajesDTO obtenerReporteMonopatines(int anio, long minViajes) {
        ResponseEntity<List<ReporteMonopatinDTO>> response = viajeClient.obtenerMonopatinesConMasDeXViajes(anio, minViajes);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<ReporteMonopatinDTO> monopatines = response.getBody();

            List<ReporteMonopatinDTO> monopatinesFiltrados = monopatines.stream()
                    .filter(m -> m.getCantidadViajes() >= minViajes)
                    .collect(Collectors.toList());

           long cantidadMonopatines = monopatinesFiltrados.size();

            return new ReporteMonopatinesXviajesDTO(monopatinesFiltrados, cantidadMonopatines, minViajes);
        } else {
            throw new RuntimeException("No se pudo obtener el reporte de monopatines.");
        }
    }


    // Punto d- Como administrador quiero consultar el total facturado en un rango de meses
    public ReporteFacturadoDTO generarReporteFacturado(int anio, int mesInicio, int mesFin) {
        ResponseEntity<Double> response = viajeClient.obtenerTotalFacturado(anio, mesInicio, mesFin);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Double totalFacturado = response.getBody();

           return new ReporteFacturadoDTO(totalFacturado, anio, mesInicio, mesFin);
        } else {
            throw new RuntimeException("Error al obtener el total facturado.");
        }
    }

    //Punto e- Consultar estado de monopatines(en-operacion vs en-mantenimiento)
    public  Map<String, Long> consultarEstadoMonopatines() {
        Map<String, Long> reporte = monopatinClient.obtenerReporteEstadoMonopatines();

        long enOperacion = reporte.get("en_operacion");
        long enMantenimiento = reporte.get("en_mantenimiento");

        System.out.println("Monopatines en operación: " + enOperacion);
        System.out.println("Monopatines en mantenimiento: " + enMantenimiento);
        return reporte;
    }

    //Punto f- Endpoint para realizar ajuste de precios a partir de cierta fecha
    public void ajustarTarifas(LocalDate fechaAjuste, double porcentajeBase, double porcentajeExtra) {
        List<Tarifa> tarifas = tarifaRepository.findByFechaAfter(fechaAjuste);

        for (Tarifa tarifa : tarifas) {

            tarifa.setTarifaBasePorMinuto(tarifa.getTarifaBasePorMinuto() * (1 + porcentajeBase / 100));
            tarifa.setTarifaExtraPorMinuto(tarifa.getTarifaExtraPorMinuto() * (1 + porcentajeExtra / 100));

            tarifaRepository.save(tarifa);
        }
    }

    public MonopatinDTO agregarMonopatin(MonopatinDTO monopatin) {
        return monopatinClient.guardarMonopatin(monopatin);
    }


    public void quitarMonopatin(Long id) {
        try {
            monopatinClient.eliminarMonopatin(id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Monopatín no encontrado con id: " + id);
        }
    }

    public MonopatinDTO obtenerMonopatin(Long id) {
        ResponseEntity<MonopatinDTO> response = monopatinClient.getMonopatinById(id);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al obtener el monopatín con ID: " + id);
        }
    }




}