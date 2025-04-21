package org.example.shareddto.DTO.entity;

import lombok.Data;

@Data
public class ParadaDTO {
    private String shortId;
    private double latitud;
    private double longitud;

    public ParadaDTO() {
    }
    public ParadaDTO(String id, double latitud, double longitud) {
        this.shortId = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }




}
