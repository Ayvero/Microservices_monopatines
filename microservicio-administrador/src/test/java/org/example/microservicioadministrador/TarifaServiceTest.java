package org.example.microservicioadministrador;

import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.repository.TarifaRepository;
import org.example.microservicioadministrador.service.TarifaService;
import org.example.microservicioadministrador.service.mapper.TarifaMapper;
import org.example.shareddto.DTO.entity.TarifaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @Mock
    private TarifaMapper tarifaMapper;

    @InjectMocks
    private TarifaService tarifaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Successful() {
        TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setFecha(LocalDate.parse("2024-11-01"));
        tarifaDTO.setTarifaBasePorMinuto(5.0);
        tarifaDTO.setTarifaExtraPorMinuto(2.0);

        Tarifa tarifa = new Tarifa();
        when(tarifaMapper.mapToEntity(tarifaDTO)).thenReturn(tarifa);
        when(tarifaRepository.save(tarifa)).thenReturn(tarifa);

        Tarifa result = tarifaService.save(tarifaDTO);

        assertNotNull(result);
        verify(tarifaRepository, times(1)).save(tarifa);
    }

    @Test
    public void testSave_InvalidTarifaDTO_ThrowsException() {
        TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setFecha(LocalDate.parse("2024-11-01"));
        tarifaDTO.setTarifaBasePorMinuto(-1.0);  // Invalid value

        assertThrows(IllegalArgumentException.class, () -> tarifaService.save(tarifaDTO));
        verify(tarifaRepository, never()).save(any(Tarifa.class));
    }
}
