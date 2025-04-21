package org.example.shareddto.DTO.entity;

import lombok.Data;

@Data
public class MonopatinDTO {
    private Long id;
    private String modelo;
    private double latitud;
    private double longitud;
    private String estado;
    private boolean disponible;
    private double totalKmRecorridos;
    private Long tiempoUsoConPausa;
    private Long tiempoUsoSinPausa;

    public MonopatinDTO() {}

    public MonopatinDTO(Long id, String modelo, double latitud, double longitud, String estado, boolean disponible, double totalKmRecorridos, Long tiempoUsoConPausa, Long tiempoUsoSinPausa) {
        this.id = id;
        this.modelo = modelo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.disponible = disponible;
        this.totalKmRecorridos = totalKmRecorridos;
        this.tiempoUsoConPausa = tiempoUsoConPausa;
        this.tiempoUsoSinPausa = tiempoUsoSinPausa;
    }


}


