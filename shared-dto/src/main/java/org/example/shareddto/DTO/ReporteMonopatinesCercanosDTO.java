package org.example.shareddto.DTO;

import lombok.Data;

@Data
public class ReporteMonopatinesCercanosDTO {
    private Long id;
    private String modelo;
    private Double latitud;
    private Double longitud;

    public ReporteMonopatinesCercanosDTO(Long id, String modelo, Double latitud, Double longitud) {
        this.id = id;
        this.modelo = modelo;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
