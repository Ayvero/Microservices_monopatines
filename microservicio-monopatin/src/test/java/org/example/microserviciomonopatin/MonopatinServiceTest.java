package org.example.microserviciomonopatin;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.microserviciomonopatin.exception.MonopatinNotFoundException;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.microserviciomonopatin.service.MonopatinMapper;
import org.example.microserviciomonopatin.service.MonopatinService;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MonopatinServiceTest {

    @Mock
    private MonopatinRepository monopatinRepository;

    @Mock
    private MonopatinMapper monopatinMapper;

    @InjectMocks
    private MonopatinService monopatinService;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        monopatin.setModelo("Modelo Test");
        monopatin.setLatitud(40.0);
        monopatin.setLongitud(10.0);
        monopatin.setEstado("en-operacion");
        monopatin.setTiempoUsoConPausa(120L);
        monopatin.setTiempoUsoSinPausa(100L);
        monopatin.setTotalKmRecorridos(15);
        monopatin.setDisponible(true);

        MonopatinDTO monopatinDTO = new MonopatinDTO();
        monopatinDTO.setId(1L);
        monopatinDTO.setModelo("Modelo Test");
        monopatinDTO.setLatitud(40.0);
        monopatinDTO.setLongitud(10.0);
        monopatinDTO.setEstado("en-operacion");
        monopatinDTO.setTiempoUsoConPausa(120L);
        monopatinDTO.setTiempoUsoSinPausa(100L);
        monopatinDTO.setTotalKmRecorridos(15);
        monopatinDTO.setDisponible(true);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));

        when(monopatinMapper.mapToDTO(monopatin)).thenReturn(monopatinDTO);

        MonopatinDTO result = monopatinService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Modelo Test", result.getModelo());
        assertEquals("en-operacion", result.getEstado());
        assertTrue(result.isDisponible());

        verify(monopatinRepository, times(1)).findById(1L);
        verify(monopatinMapper, times(1)).mapToDTO(monopatin);
    }

    @Test
    void testFindByIdMonopatinNotFound() {
        when(monopatinRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MonopatinNotFoundException.class, () -> {
            monopatinService.findById(1L);
        });

        verify(monopatinRepository, times(1)).findById(1L);
    }
}
