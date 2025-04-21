package org.example.microservicioadministrador;

import org.example.microservicioadministrador.foreinClient.CuentaClient;
import org.example.microservicioadministrador.service.AdministradorService;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AdministradorServiceTest {

    @Mock
    private CuentaClient cuentaClient;

    @InjectMocks
    private AdministradorService administradorService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testAnularCuenta() {
        // Configuración de datos de prueba
        Long cuentaId = 1L;
        LocalDateTime fechaAlta = LocalDateTime.now();
        Double saldo = 1000.0;
        String idMercadoPago = "MP123";
        boolean anulada = false;

        CuentaDTO cuentaOriginal = new CuentaDTO(cuentaId, fechaAlta, saldo, idMercadoPago, anulada);

        when(cuentaClient.getCuenta(cuentaId)).thenReturn(new ResponseEntity<>(cuentaOriginal, HttpStatus.OK));

        administradorService.anularCuenta(cuentaId);

        CuentaDTO cuentaAnulada = new CuentaDTO(cuentaId, fechaAlta, saldo, idMercadoPago, true);
        verify(cuentaClient).anularCuenta(cuentaId, cuentaAnulada);
    }

    @Test
    void testAnularCuenta_CuentaNoEncontrada() {
        Long cuentaId = 2L;
        when(cuentaClient.getCuenta(cuentaId)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> administradorService.anularCuenta(cuentaId));
    }
}


