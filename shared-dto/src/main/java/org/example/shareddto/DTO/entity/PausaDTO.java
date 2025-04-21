package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PausaDTO {
    private Long id;
    private Long viajeId; // Puedes usar el ID del viaje relacionado
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Long duracion;


}
