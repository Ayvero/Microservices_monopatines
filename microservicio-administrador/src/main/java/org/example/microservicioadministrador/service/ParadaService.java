package org.example.microservicioadministrador.service;

import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microservicioadministrador.repository.ParadaRepository;
import org.example.microservicioadministrador.service.mapper.ParadaMapper;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    @Autowired
    private ParadaMapper paradaMapper;


    public Parada save(double latitud, double longitud) {
        Parada parada = new Parada(latitud, longitud);
        return paradaRepository.save(parada);
    }

    public List<Parada> saveAll(List<ParadaDTO> paradaDTOList) {

        List<Parada> paradaList = paradaDTOList.stream()
                .map(paradaDTO -> {
                    // El método generateShortId se llama automáticamente en el constructor de Parada
                    return paradaMapper.mapToEntity(paradaDTO);  // No es necesario llamar a generateShortId aquí
                })
                .collect(Collectors.toList());

        // Guardamos todas las paradas en la base de datos y las devolvemos
        return paradaRepository.saveAll(paradaList);
    }

    public ParadaDTO findById(String id) {
        Parada parada = getParadaById(id);  // Reutiliza el metodo
        return paradaMapper.mapToDTO(parada);
    }

    public List<ParadaDTO> findAll() {
        return paradaRepository.findAll().stream()
                .map(paradaMapper::mapToDTO)
                .collect(Collectors.toList());
    }


    public ParadaDTO update(String id, ParadaDTO paradaDTO) {
        Parada parada = getParadaById(id);

        parada.setLatitud(paradaDTO.getLatitud());
        parada.setLongitud(paradaDTO.getLongitud());
        return paradaMapper.mapToDTO(paradaRepository.save(parada));
    }


    public void delete(String id) {
        Parada parada = getParadaById(id);
        paradaRepository.delete(parada);
    }


    private Parada getParadaById(String shortId) {
        return paradaRepository.findByShortId(shortId)
                .orElseThrow(() -> new ResourceNotFoundException("Parada no encontrada"));
    }

    public void eliminarTodasLasParadas() {
        paradaRepository.deleteAll();
    }

}

