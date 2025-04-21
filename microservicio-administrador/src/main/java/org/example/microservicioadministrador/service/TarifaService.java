package org.example.microservicioadministrador.service;

import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microservicioadministrador.repository.TarifaRepository;
import org.example.microservicioadministrador.service.mapper.TarifaMapper;
import org.example.shareddto.DTO.entity.TarifaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private TarifaMapper tarifaMapper;


    public Tarifa save(TarifaDTO tarifaDTO) {
       validarTarifaDTO(tarifaDTO);

       Tarifa tarifa = tarifaMapper.mapToEntity(tarifaDTO);
       return tarifaRepository.save(tarifa);
    }

    public List<Tarifa> saveAll(List<TarifaDTO> tarifasDTO) {
        tarifasDTO.forEach(this::validarTarifaDTO);
        List<Tarifa> tarifas = tarifasDTO.stream()
                .map(tarifaMapper::mapToEntity)
                .collect(Collectors.toList());
        return tarifaRepository.saveAll(tarifas);
    }

    public List<TarifaDTO> findAll() {
        return tarifaRepository.findAll().stream()
                .map(tarifaMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public TarifaDTO findById(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada"));

        return tarifaMapper.mapToDTO(tarifa);
    }


    public TarifaDTO update(Long id, TarifaDTO tarifaDTO) {
        validarTarifaDTO(tarifaDTO);

       Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada"));

        tarifa.setFecha(tarifaDTO.getFecha());
        tarifa.setTarifaBasePorMinuto(tarifaDTO.getTarifaBasePorMinuto());
        tarifa.setTarifaExtraPorMinuto(tarifaDTO.getTarifaExtraPorMinuto());

        Tarifa updatedTarifa = tarifaRepository.save(tarifa);
        return tarifaMapper.mapToDTO(updatedTarifa);
    }


    public void delete(Long id) {
       if (!tarifaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarifa no encontrada");
        }

        tarifaRepository.deleteById(id);
    }


    public TarifaDTO obtenerTarifaActual() {
        Tarifa tarifa = tarifaRepository.findTopByOrderByFechaDesc();
        if (tarifa == null) {
            throw new ResourceNotFoundException("No hay tarifas registradas.");
        }

       return tarifaMapper.mapToDTO(tarifa);

    }

    public boolean existeTarifa(Long tarifaId) {
        return tarifaRepository.existsById(tarifaId);
    }


    private void validarTarifaDTO(TarifaDTO tarifaDTO) {
        if (tarifaDTO.getFecha() == null) {
            throw new IllegalArgumentException("La fecha de la tarifa no puede ser nula");
        }
        if (tarifaDTO.getTarifaBasePorMinuto() <= 0) {
            throw new IllegalArgumentException("La tarifa base por minuto debe ser mayor que cero");
        }
        if (tarifaDTO.getTarifaExtraPorMinuto() < 0) {
            throw new IllegalArgumentException("La tarifa extra por minuto no puede ser negativa");
        }
    }
}

