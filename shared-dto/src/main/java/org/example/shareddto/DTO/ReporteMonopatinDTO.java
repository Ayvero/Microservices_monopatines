package org.example.shareddto.DTO;

import lombok.Data;

@Data
public class ReporteMonopatinDTO {
    private Long monopatinId;
    private long cantidadViajes;


    public ReporteMonopatinDTO() {
    }

    public ReporteMonopatinDTO(Long monopatinId, long cantidadViajes) {
        this.monopatinId = monopatinId;
        this.cantidadViajes = cantidadViajes;
    }


}
