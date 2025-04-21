package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDate;
@Data
public class TarifaDTO {

    private Long id;
    private LocalDate fecha;
    private double tarifaBasePorMinuto;
    private double tarifaExtraPorMinuto;
}
