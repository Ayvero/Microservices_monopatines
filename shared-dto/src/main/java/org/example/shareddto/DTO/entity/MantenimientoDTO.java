package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MantenimientoDTO {
    private Long id;
    private Long monopatinId;
    private LocalDateTime fechaInicioMantenimiento;
    private LocalDateTime fechaFinMantenimiento;
    private String descripcion;



    public MantenimientoDTO(Long id, Long monopatinId, LocalDateTime fechaInicioMantenimiento, LocalDateTime fechaFinMantenimiento, String realizadoPor) {
        this.id = id;
        this.monopatinId = monopatinId;
        this.fechaInicioMantenimiento = fechaInicioMantenimiento;
        this.fechaFinMantenimiento = fechaFinMantenimiento;
        this.descripcion = realizadoPor;

    }

    public MantenimientoDTO() {

    }
}
