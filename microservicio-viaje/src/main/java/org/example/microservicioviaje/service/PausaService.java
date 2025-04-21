package org.example.microservicioviaje.service;

import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.entity.Viaje;
import org.example.microservicioviaje.repository.PausaRepository;
import org.example.microservicioviaje.repository.ViajeRepository;
import org.example.microservicioviaje.service.Mapper.PausaMapper;
import org.example.shareddto.DTO.entity.PausaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PausaService {

    @Autowired
    private PausaRepository pausaRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaMapper pausaMapper;


    public PausaDTO altaPausa(Long viajeId, LocalDateTime horaInicio, LocalDateTime horaFin) {
        // Buscar el viaje por su ID
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("El viaje no existe."));

        // Crear el DTO Pausa y establecer sus propiedades
        PausaDTO pausaDTO = new PausaDTO();
        pausaDTO.setHoraInicio(horaInicio);
        pausaDTO.setHoraFin(horaFin);
        pausaDTO.setViajeId(viajeId);

        Pausa pausa = pausaMapper.mapToEntity(pausaDTO);
        pausa.calcularDuracion();
        pausaRepository.save(pausa);

        viaje.getPausas().add(pausa);
        viaje.setEnPausa(true);
        viaje.setFuePausado(true);

        viajeRepository.save(viaje);

        return pausaMapper.mapToDTO(pausa);
    }



    public PausaDTO obtenerPausa(Long pausaId) {
        Pausa pausa = pausaRepository.findById(pausaId)
                .orElseThrow(() -> new IllegalArgumentException("La pausa no existe."));

        return pausaMapper.mapToDTO(pausa);
    }


    public PausaDTO actualizarPausa(Long pausaId, LocalDateTime horaInicio, LocalDateTime horaFin) {
        Pausa pausaExistente = pausaRepository.findById(pausaId)
                .orElseThrow(() -> new IllegalArgumentException("La pausa no existe."));

        pausaExistente.setHoraInicio(horaInicio);
        pausaExistente.setHoraFin(horaFin);

        pausaRepository.save(pausaExistente);
        return pausaMapper.mapToDTO(pausaExistente);
    }


    public void eliminarPausa(Long pausaId) {
        Pausa pausaExistente = pausaRepository.findById(pausaId)
                .orElseThrow(() -> new IllegalArgumentException("La pausa no existe."));

        pausaRepository.delete(pausaExistente);
    }

    public List<PausaDTO> obtenerTodasLasPausas() {
        List<Pausa> pausas = pausaRepository.findAll();
        return pausas.stream()
                .map(pausaMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
