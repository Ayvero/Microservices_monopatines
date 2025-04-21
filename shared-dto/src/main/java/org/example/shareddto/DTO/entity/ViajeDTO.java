package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViajeDTO {
    private Long id;
    private Long cuentaId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long monopatinId;
    private boolean enPausa;
    private boolean fuePausado;
    private Double costoTotal;
    private Double totalKmRecorridos;
    private Long totalTiempo;
    private Long totalTiempoUsoSinPausas;


}
