package org.example.microservicioviaje.service;

import jakarta.validation.Valid;
import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.entity.Viaje;
import org.example.microservicioviaje.foreinClient.CuentaClient;
import org.example.microservicioviaje.foreinClient.MonopatinClient;
import org.example.microservicioviaje.foreinClient.ParadaClient;
import org.example.microservicioviaje.foreinClient.TarifaClient;
import org.example.microservicioviaje.repository.PausaRepository;
import org.example.microservicioviaje.repository.ViajeRepository;
import org.example.microservicioviaje.service.Mapper.PausaMapper;
import org.example.microservicioviaje.service.Mapper.ViajeMapper;
import org.example.shareddto.DTO.*;
import org.example.shareddto.DTO.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ViajeService {


    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaRepository pausaRepository;
    @Autowired
    private PausaMapper pausaMapper;

    @Autowired
    private ParadaClient paradaClient;

    @Autowired
    private TarifaClient tarifaClient;

    @Autowired
    private MonopatinClient monopatinClient;

    @Autowired
    private CuentaClient cuentaClient;

    @Autowired
    private ViajeMapper viajeMapper;


    public ViajeDTO guardarViaje(ViajeDTO viajeDTO) {
        ResponseEntity<CuentaDTO> cuentaResponse = cuentaClient.getCuenta(viajeDTO.getCuentaId());
        if (!cuentaResponse.hasBody()|| cuentaResponse.getBody() == null) {
            throw new ResourceNotFoundException("Cuenta no encontrada con ID: " + viajeDTO.getCuentaId());
        }
        CuentaDTO cuenta = cuentaResponse.getBody();
        if (cuenta.isAnulada()) {
            throw new IllegalStateException("La cuenta está anulada. No se puede crear el viaje.");
        }
        // Verificar si el monopatín está disponible
        ResponseEntity<MonopatinDTO> response = monopatinClient.getMonopatinById(viajeDTO.getMonopatinId());
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("El monopatín no fue encontrado.");
        }
        MonopatinDTO monopatinDTO = response.getBody();
        if (!monopatinDTO.isDisponible()) {
            throw new IllegalArgumentException("No se puede iniciar el viaje: el monopatín no está disponible.");
        }
        ResponseEntity<TarifaDTO> tarifaResponse = tarifaClient.obtenerUltimaTarifa();
        if (!tarifaResponse.getStatusCode().is2xxSuccessful() || tarifaResponse.getBody() == null) {
            throw new RuntimeException("No se puede crear el viaje: no hay tarifas definidas.");
        }
        Viaje viaje = viajeMapper.mapToEntity(viajeDTO);
        Viaje viajeGuardado = viajeRepository.save(viaje);

        return viajeMapper.mapToDTO(viajeGuardado);
    }


    public ViajeDTO obtenerViajePorId(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        return viajeMapper.mapToDTO(viaje);
    }

    public Viaje iniciarViaje(long monopatinId, long cuentaId) {
        ResponseEntity<TarifaDTO> tarifaResponse = tarifaClient.obtenerUltimaTarifa();
        if (!tarifaResponse.getStatusCode().is2xxSuccessful() || tarifaResponse.getBody() == null) {
            throw new RuntimeException("No se puede iniciar el viaje: no hay tarifas definidas.");
        }
        //Verificar cuenta no este anulada
        ResponseEntity<CuentaDTO> cuentaResponse = cuentaClient.getCuenta(cuentaId);
        if (!cuentaResponse.hasBody()|| cuentaResponse.getBody() == null) {
            throw new ResourceNotFoundException("Cuenta no encontrada con ID: " + cuentaId);
        }
        CuentaDTO cuenta = cuentaResponse.getBody();
        if (cuenta.isAnulada()) {
            throw new IllegalStateException("La cuenta está anulada. No se puede crear el viaje.");
        }
        boolean monopatinExiste = monopatinClient.existeMonopatin(monopatinId);
        if (!monopatinExiste) {
            throw new IllegalArgumentException("No se puede crear el viaje: el monopatín no existe.");
        }
        // Verificar si el monopatín está disponible
        ResponseEntity<MonopatinDTO> response = monopatinClient.getMonopatinById(monopatinId);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("El monopatín con ID " + monopatinId + " no fue encontrado.");
        }
        MonopatinDTO monopatinDTO = response.getBody();
        if (!monopatinDTO.isDisponible()) {
            throw new IllegalArgumentException("No se puede iniciar el viaje: el monopatín no está disponible.");
        }

        // Marcar el monopatín como no disponible
        monopatinDTO.setDisponible(false);
        monopatinClient.guardarMonopatin(monopatinDTO);

        Viaje viaje = new Viaje();
        viaje.setFechaInicio(LocalDateTime.now());
        viaje.setFechaFin(null);
        viaje.setTotalKmRecorridos(0.0);
        viaje.setCostoTotal(0.0);
        viaje.setEnPausa(false);
        viaje.setMonopatinId(monopatinId);
        viaje.setCuentaId(cuentaId);

        viajeRepository.save(viaje);
        return viaje;
    }


    public ViajeDTO updateViaje(Long viajeId, ViajeDTO viajeDTO) {
        try {
            System.out.println("Iniciando actualización del viaje con ID: " + viajeId);

            Viaje viajeExistente = viajeRepository.findById(viajeId)
                    .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + viajeId));
            boolean updated = false;

            if (viajeDTO.getFechaFin() != null && !viajeDTO.getFechaFin().equals(viajeExistente.getFechaFin())) {
                viajeExistente.setFechaFin(viajeDTO.getFechaFin());
                updated = true;
            }
            if (viajeDTO.getCostoTotal() != null && !viajeDTO.getCostoTotal().equals(viajeExistente.getCostoTotal())) {
                viajeExistente.setCostoTotal(viajeDTO.getCostoTotal());
                updated = true;
            }
            if (viajeDTO.getTotalKmRecorridos() != null && !viajeDTO.getTotalKmRecorridos().equals(viajeExistente.getTotalKmRecorridos())) {
                if (viajeDTO.getTotalKmRecorridos() >= 0) {
                    viajeExistente.setTotalKmRecorridos(viajeDTO.getTotalKmRecorridos());
                    updated = true;
                } else {
                    throw new IllegalArgumentException("El total de kilómetros recorridos no puede ser negativo.");
                }
            }
            if (viajeDTO.getTotalTiempo() != null && !viajeDTO.getTotalTiempo().equals(viajeExistente.getTotalTiempo())) {
                if (viajeDTO.getTotalTiempo() >= 0) {
                    viajeExistente.setTotalTiempo(viajeDTO.getTotalTiempo());
                    updated = true;
                } else {
                    throw new IllegalArgumentException("El total de tiempo no puede ser negativo.");
                }
            }
            if (viajeDTO.getTotalTiempoUsoSinPausas() != null && !viajeDTO.getTotalTiempoUsoSinPausas().equals(viajeExistente.getTotalTiempoUsoSinPausas())) {
                if (viajeDTO.getTotalTiempoUsoSinPausas() >= 0) { // Permitir 0, pero no valores negativos
                    viajeExistente.setTotalTiempoUsoSinPausas(viajeDTO.getTotalTiempoUsoSinPausas());
                    updated = true;
                } else {
                    throw new IllegalArgumentException("El total de tiempo de uso sin pausas no puede ser negativo.");
                }
            }
            if (!updated) {
                throw new IllegalArgumentException("No se encontraron cambios en el viaje.");
            }
            Viaje updatedViaje = viajeRepository.save(viajeExistente);
            System.out.println("Viaje actualizado con éxito.");

            return viajeMapper.mapToDTO(updatedViaje);

        } catch (Exception e) {
            System.out.println("Error en la actualización del viaje: " + e.getMessage());
            throw new RuntimeException("Error en la actualización del viaje", e);
        }
    }


    public void deleteViaje(Long viajeId) {
        if (!viajeRepository.existsById(viajeId)) {
            throw new IllegalArgumentException("Viaje no encontrado");
        }
        viajeRepository.deleteById(viajeId);
    }

    public List<ViajeDTO> altaViajes(List<ViajeDTO> viajeDTOs) {
        ResponseEntity<TarifaDTO> tarifaResponse = tarifaClient.obtenerUltimaTarifa();
        if (!tarifaResponse.getStatusCode().is2xxSuccessful() || tarifaResponse.getBody() == null) {
            throw new RuntimeException("No se puede crear los viajes: no hay tarifas definidas.");
        }
            List<ViajeDTO> viajesGuardados = new ArrayList<>();

        for (ViajeDTO viajeDTO : viajeDTOs) {
            Viaje viaje = viajeMapper.mapToEntity(viajeDTO);
            viaje.setFechaFin(null);
            viaje.setTotalKmRecorridos(0.0);
            viaje.setCostoTotal(0.0);
            viaje.setEnPausa(false);

            Viaje savedViaje = viajeRepository.save(viaje);
            viajesGuardados.add(viajeMapper.mapToDTO(savedViaje));
        }
        return viajesGuardados;
    }

    public List<ViajeDTO> obtenerViajes() {
        List<Viaje> viajes = viajeRepository.findAll();
        return viajes.stream()
                .map(viajeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ViajeDTO> obtenerViajesPorMonopatinId(long monopatinId) {
        List<Viaje> viajes = viajeRepository.findByMonopatinId(monopatinId);
        return viajes.stream()
                .map(viajeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public void pausarViaje(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
        Pausa nuevaPausa = new Pausa();
        nuevaPausa.setViaje(viaje);
        nuevaPausa.setHoraInicio(LocalDateTime.now());
        nuevaPausa.setHoraFin(null);  // Deja horaFin como null al inicio
        nuevaPausa.setDuracion(0L);  // Inicializa la duración a 0
        pausaRepository.save(nuevaPausa);

        viaje.getPausas().add(nuevaPausa);
        viaje.setEnPausa(true);
        viaje.setFuePausado(true);
        viajeRepository.save(viaje);
    }

    public void reanudarViaje(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        // Actualizar la última pausa para establecer la hora de fin
        if (!viaje.getPausas().isEmpty()) {
            Pausa ultimaPausa = viaje.getPausas().get(viaje.getPausas().size() - 1);
            ultimaPausa.setHoraFin(LocalDateTime.now());
            pausaRepository.save(ultimaPausa);
        }
        viaje.setEnPausa(false);
        viajeRepository.save(viaje);
    }

    public void finalizarViaje(Long viajeId, Long monopatinId, String paradaId) {
        try {
            Viaje viaje = viajeRepository.findById(viajeId)
            .orElseThrow(() -> new RuntimeException("Viaje no encontrada con id: " + viajeId));

            ResponseEntity<MonopatinDTO> monopatinResponse = monopatinClient.getMonopatinById(monopatinId);
            if (!monopatinResponse.getStatusCode().is2xxSuccessful() || monopatinResponse.getBody() == null) {
                throw new RuntimeException("Error al obtener el monopatin: " + monopatinResponse.getStatusCode());
            }
            MonopatinDTO monopatinDTO = monopatinResponse.getBody();

            ParadaDTO paradaDTO = paradaClient.obtenerParada(paradaId);
            if (paradaDTO == null) {
                throw new RuntimeException("Parada no encontrada con id: " + paradaId);
            }
            // Verificar si las ubicaciones coinciden
            if (!ubicacionesCoinciden(monopatinDTO, paradaDTO)) {
                throw new RuntimeException("No se puede finalizar el viaje: la parada no coincide con la ubicación del monopatín.");
            }
            // Finalizar el viaje
            viaje.setFechaFin(LocalDateTime.now());

            // Calcular el costo total del viaje
            Double costoTotal = calcularCostoDelViaje(viajeId);
            viaje.setCostoTotal(costoTotal);
            // Descontar saldo de la cuenta
            try {
                cuentaClient.descontarSaldo(viaje.getCuentaId(), costoTotal);
            } catch (Exception e) {
                throw new RuntimeException("Error al descontar saldo: " + e.getMessage());
            }
            // Actualizar tiempos de Uso
            Long duracionTotalPausas = pausaRepository.calcularDuracionPausasPorViaje(viajeId);
            duracionTotalPausas = (duracionTotalPausas != null) ? duracionTotalPausas : 0L;
            Long totalTiempo = calcularTiempoUso(viajeId);
            Long totalTiempoUsoSinPausas = totalTiempo - duracionTotalPausas;

            viaje.setTotalTiempo(totalTiempo);
            viaje.setTotalTiempoUsoSinPausas(totalTiempoUsoSinPausas);
            monopatinDTO.setTiempoUsoConPausa(monopatinDTO.getTiempoUsoConPausa() + totalTiempo);
            monopatinDTO.setTiempoUsoSinPausa(monopatinDTO.getTiempoUsoSinPausa() + totalTiempoUsoSinPausas);
            // Actualizar total de kilómetros recorridos
            double kmRecorridos = viaje.getTotalKmRecorridos();
            monopatinDTO.setTotalKmRecorridos(monopatinDTO.getTotalKmRecorridos() + kmRecorridos);
            //Actualizar Ubicacion
            monopatinDTO.setLatitud(paradaDTO.getLatitud());
            monopatinDTO.setLongitud(paradaDTO.getLongitud());
            monopatinDTO.setDisponible(true);
            // Guardar cambios en el viaje y el monopatín
            viajeRepository.save(viaje);
            monopatinClient.guardarMonopatin(monopatinDTO);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al ubicar el monopatín en la parada: " + e.getMessage());
        }
    }

    private boolean ubicacionesCoinciden(MonopatinDTO monopatin, ParadaDTO parada) {
        return monopatin.getLatitud() == parada.getLatitud() && monopatin.getLongitud() == parada.getLongitud();
    }


    public double calcularCostoDelViaje(Long viajeId) {
        ResponseEntity<TarifaDTO> tarifaResponse = tarifaClient.obtenerUltimaTarifa();
        if (!tarifaResponse.getStatusCode().is2xxSuccessful() || tarifaResponse.getBody() == null) {
            throw new RuntimeException("No hay tarifas definidas ");
        }
        TarifaDTO tarifaActual  = tarifaResponse.getBody();

        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        double costoTotal = 0.0;

        long tiempoTotalViaje = calcularTiempoUso(viajeId);

        // Obtener la primera pausa que excede el tiempo permitido
        long minPausa = viaje.getPausaMAX();
        Optional<Pausa> pausaExcedida = pausaRepository.primerPausaExcedida(viajeId, minPausa);
        if (pausaExcedida.isPresent()) {
            Pausa pausa = pausaExcedida.get();

            PausaDTO pausaDTO = pausaMapper.mapToDTO(pausa);

            // Convertir fechaInicio y horaInicio a minutos
            long inicioViajeEnMinutos = viaje.getFechaInicio().getHour() * 60 + viaje.getFechaInicio().getMinute();
            long inicioPausaExcedidaEnMinutos = pausaDTO.getHoraInicio().getHour() * 60 + pausaDTO.getHoraInicio().getMinute();

            // Calcular el primer minuto después de la pausa
            long inicioPausaPermitida = inicioPausaExcedidaEnMinutos + minPausa;

            // Calcular duraciones
            long duracion1 = inicioPausaPermitida - inicioViajeEnMinutos; // Desde el inicio del viaje hasta el primer minuto después de la pausa
            long duracion2 = tiempoTotalViaje - duracion1;
            // Cálculo del costo
            costoTotal += (duracion1 * tarifaActual.getTarifaBasePorMinuto()) +
                    (duracion2 * tarifaActual.getTarifaExtraPorMinuto());
        } else {
            costoTotal = tiempoTotalViaje * tarifaActual.getTarifaBasePorMinuto();
        }

        return costoTotal; // Retorna el costo calculado
    }


    public long calcularTiempoUso(long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (viaje.getFechaInicio() == null || viaje.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas");
        }
        if (viaje.getFechaFin().isBefore(viaje.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        long tiempoUso = java.time.Duration.between(viaje.getFechaInicio(), viaje.getFechaFin()).toMinutes();

        return tiempoUso;
    }


   public List<ReporteMonopatinDTO> obtenerMonopatinesConMasDeXViajes(int anio, long minViajes) {
       return viajeRepository.findMonopatinesConMasDeXViajes(anio, minViajes);
   }

    public Double obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        Double totalFacturado = viajeRepository.calcularTotalFacturado(anio, mesInicio, mesFin);

        if (totalFacturado != null) {
            return totalFacturado;
        } else {
            throw new RuntimeException("Error al obtener el total facturado");
        }
    }



}


