package org.example.microservicioadministrador;

import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microservicioadministrador.repository.ParadaRepository;
import org.example.microservicioadministrador.service.ParadaService;
import org.example.microservicioadministrador.service.mapper.ParadaMapper;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @Mock
    private ParadaMapper paradaMapper; // Mapper para convertir ParadaDTO a Parada

    @InjectMocks
    private ParadaService paradaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Successful() {
        double latitud = 10.0;
        double longitud = 20.0;

        Parada parada = new Parada(latitud, longitud);

        when(paradaRepository.save(parada)).thenReturn(parada);

        Parada result = paradaService.save(latitud, longitud);

        assertNotNull(result);

        verify(paradaRepository, times(1)).save(parada);
    }

    @Test
    public void testSaveAll_Successful() {
        ParadaDTO paradaDTO1 = new ParadaDTO();
        paradaDTO1.setLatitud(10.0);
        paradaDTO1.setLongitud(20.0);

        ParadaDTO paradaDTO2 = new ParadaDTO();
        paradaDTO2.setLatitud(30.0);
        paradaDTO2.setLongitud(40.0);

        List<ParadaDTO> paradaDTOList = List.of(paradaDTO1, paradaDTO2);

        Parada parada1 = new Parada(paradaDTO1.getLatitud(), paradaDTO1.getLongitud());
        Parada parada2 = new Parada(paradaDTO2.getLatitud(), paradaDTO2.getLongitud());
        List<Parada> paradaList = List.of(parada1, parada2);

        when(paradaMapper.mapToEntity(paradaDTO1)).thenReturn(parada1);
        when(paradaMapper.mapToEntity(paradaDTO2)).thenReturn(parada2);

        when(paradaRepository.saveAll(paradaList)).thenReturn(paradaList);

        List<Parada> result = paradaService.saveAll(paradaDTOList);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(paradaRepository, times(1)).saveAll(paradaList);
    }
    @Test
    public void testFindById_Successful() {
        String shortId = "shortId123";
        Parada parada = new Parada(10.0, 20.0);
        ParadaDTO paradaDTO = new ParadaDTO();
        paradaDTO.setLatitud(parada.getLatitud());
        paradaDTO.setLongitud(parada.getLongitud());

        // Simular que el repositorio devuelve una parada
        when(paradaRepository.findByShortId(shortId)).thenReturn(Optional.of(parada));
        when(paradaMapper.mapToDTO(parada)).thenReturn(paradaDTO);

        ParadaDTO result = paradaService.findById(shortId);

        assertNotNull(result);
        assertEquals(parada.getLatitud(), result.getLatitud());
        assertEquals(parada.getLongitud(), result.getLongitud());

        verify(paradaRepository, times(1)).findByShortId(shortId);
    }

    @Test
    public void testFindById_NotFound_ThrowsException() {
        String shortId = "shortId123";

        when(paradaRepository.findByShortId(shortId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paradaService.findById(shortId));

        verify(paradaRepository, times(1)).findByShortId(shortId);
    }

    @Test
    public void testUpdate_Successful() {
        String shortId = "shortId123";
        ParadaDTO paradaDTO = new ParadaDTO();
        paradaDTO.setLatitud(15.0);
        paradaDTO.setLongitud(25.0);

        Parada parada = new Parada(10.0, 20.0);  // Datos originales

        when(paradaRepository.findByShortId(shortId)).thenReturn(Optional.of(parada));
        when(paradaRepository.save(parada)).thenReturn(parada);
        when(paradaMapper.mapToDTO(parada)).thenReturn(paradaDTO);

        ParadaDTO result = paradaService.update(shortId, paradaDTO);

        assertNotNull(result);
        assertEquals(paradaDTO.getLatitud(), result.getLatitud());
        assertEquals(paradaDTO.getLongitud(), result.getLongitud());

        verify(paradaRepository, times(1)).save(parada);
    }

    @Test
    public void testDelete_Successful() {
        String shortId = "a1";
        Parada parada = new Parada(10.0, 20.0);

        when(paradaRepository.findByShortId(shortId)).thenReturn(Optional.of(parada));

        paradaService.delete(shortId);

        verify(paradaRepository, times(1)).delete(parada);
    }

    @Test
    public void testDelete_NotFound_ThrowsException() {
        String shortId = "a1";

        when(paradaRepository.findByShortId(shortId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paradaService.delete(shortId));

        verify(paradaRepository, times(1)).findByShortId(shortId);
    }

    @Test
    public void testEliminarTodasLasParadas_Successful() {
        paradaService.eliminarTodasLasParadas();

        verify(paradaRepository, times(1)).deleteAll();
    }
}
