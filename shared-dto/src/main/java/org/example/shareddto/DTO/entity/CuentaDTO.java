package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CuentaDTO {
    private Long cuentaId;
    private LocalDateTime fechaAlta;
    private Double saldo;
    private String idMercadoPago;
    private boolean anulada;


    public CuentaDTO(Long cuentaId, LocalDateTime fechaAlta, Double saldo, String idMercadoPago, boolean anulada) {
        this.cuentaId = cuentaId;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.idMercadoPago = idMercadoPago;
        this.anulada = anulada;
    }
}
