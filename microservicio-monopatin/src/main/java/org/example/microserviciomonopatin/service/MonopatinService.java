package org.example.microserviciomonopatin.service;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.microserviciomonopatin.exception.MonopatinNotFoundException;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.shareddto.DTO.entity.MonopatinDTO;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;
    @Autowired
    private MonopatinMapper monopatinMapper;


    public Monopatin save(MonopatinDTO monopatinDTO) {
        Monopatin monopatin = monopatinMapper.mapToEntity(monopatinDTO);
        return monopatinRepository.save(monopatin);
    }


    public MonopatinDTO findById(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new MonopatinNotFoundException("Monopatín no encontrado con ID: " + id));

        return monopatinMapper.mapToDTO(monopatin);
    }

    public MonopatinDTO update(Long id, MonopatinDTO monopatinDTO) {
        try {
            System.out.println("Iniciando actualización del monopatín con ID: " + id);
            Monopatin monopatin = monopatinRepository.findById(id)
                    .orElseThrow(() -> new MonopatinNotFoundException("Monopatín no encontrado con ID: " + id));

            boolean updated = false;
           if (!monopatin.getModelo().equals(monopatinDTO.getModelo())) {
                monopatin.setModelo(monopatinDTO.getModelo());
                updated = true;
            }
            if (monopatin.getLatitud() != monopatinDTO.getLatitud()) {
                monopatin.setLatitud(monopatinDTO.getLatitud());
                updated = true;
            }
            if (monopatin.getLongitud() != monopatinDTO.getLongitud()) {
                monopatin.setLongitud(monopatinDTO.getLongitud());
                updated = true;
            }
            if (!monopatin.getEstado().equals(monopatinDTO.getEstado())) {
                monopatin.setEstado(monopatinDTO.getEstado());
                updated = true;
            }
            if (monopatin.getTiempoUsoConPausa() != monopatinDTO.getTiempoUsoConPausa()) {
                monopatin.setTiempoUsoConPausa(monopatinDTO.getTiempoUsoConPausa());
                updated = true;
            }
            if (monopatin.getTiempoUsoSinPausa() != monopatinDTO.getTiempoUsoSinPausa()) {
                monopatin.setTiempoUsoSinPausa(monopatinDTO.getTiempoUsoSinPausa());
                updated = true;
            }
            if (monopatin.getTotalKmRecorridos() != monopatinDTO.getTotalKmRecorridos()) {
                monopatin.setTotalKmRecorridos(monopatinDTO.getTotalKmRecorridos());
                updated = true;
            }
            if (monopatin.isDisponible() != monopatinDTO.isDisponible()) {
                monopatin.setDisponible(monopatinDTO.isDisponible());
                updated = true;
            }
            if (!updated) {
                System.out.println("No se encontraron cambios en el monopatín.");
                throw new IllegalArgumentException("No se encontraron cambios en el monopatín.");
            }
            Monopatin updatedMonopatin = monopatinRepository.save(monopatin);
            System.out.println("Monopatín actualizado con éxito.");

            return monopatinMapper.mapToDTO(updatedMonopatin);
        } catch (Exception e) {
            System.out.println("Error en la actualización del monopatín: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la actualización del monopatín", e);
        }
    }


    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    public List<Monopatin> saveAll(List<MonopatinDTO> monopatinDTOs) {
        List<Monopatin> monopatines = monopatinDTOs.stream()
                .map(monopatinMapper::mapToEntity)
                .collect(Collectors.toList());

        return monopatinRepository.saveAll(monopatines);
    }

    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return monopatines.stream()
                .map(monopatinMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public boolean existeMonopatin(Long monopatinId) {
        return monopatinRepository.existsById(monopatinId);
    }

    public List<ReporteUsoDTO> generarReporteUso() {
        return monopatinRepository.findAllReporteUso();
    }

    // Generar reporte por kilómetros recorridos
    public List<ReporteUsoDTO> generarReportePorKilometros() {
        return monopatinRepository.obtenerReporteKm();  // Devolvemos reporte por kilometraje
    }

    // Generar reporte por tiempos con o sin pausa
    public List<ReporteUsoDTO> generarReporteTiempos(boolean excludeSinPausa) {
        return monopatinRepository.obtenerReporteTiempos(excludeSinPausa);
    }

    public Map<String, Long> obtenerCantidadMonopatinesPorEstado() {

        long enOperacion = monopatinRepository.countByEstado("en-operacion");
        long enMantenimiento = monopatinRepository.countByEstado("en-mantenimiento");

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("en_operacion", enOperacion);
        resultado.put("en_mantenimiento", enMantenimiento);

        return resultado;
    }
}
