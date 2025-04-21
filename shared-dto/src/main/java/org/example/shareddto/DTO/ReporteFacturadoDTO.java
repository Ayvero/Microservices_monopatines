package org.example.shareddto.DTO;

import lombok.Data;

@Data
public class ReporteFacturadoDTO {
    private Double totalFacturado;
    private int anio;
    private int mesInicio;
    private int mesFin;

    public ReporteFacturadoDTO(Double totalFacturado, int anio, int mesInicio, int mesFin) {
        this.totalFacturado = totalFacturado;
        this.anio = anio;
        this.mesInicio = mesInicio;
        this.mesFin = mesFin;
    }
}
