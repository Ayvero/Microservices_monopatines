package org.example.shareddto.DTO;

import lombok.Data;

@Data
public class ReporteUsoDTO {
    private Long monopatinId;
    private Double totalKmRecorridos;
    private Long totalTiempoConPausa;
    private Long totalTiempoSinPausa;

    public ReporteUsoDTO() {
    }

    public ReporteUsoDTO(Long monopatinId, Double totalKmRecorridos) {
        this.monopatinId = monopatinId;
        this.totalKmRecorridos = totalKmRecorridos;
    }

    public ReporteUsoDTO(Double totalKmRecorridos, Long totalTiempoConPausa, Long totalTiempoSinPausa) {
        this.totalKmRecorridos = totalKmRecorridos;
        this.totalTiempoConPausa = totalTiempoConPausa;
        this.totalTiempoSinPausa = totalTiempoSinPausa;
    }

    public ReporteUsoDTO(Long monopatinId, Long totalTiempoConPausa, Long totalTiempoSinPausa) {
        this.monopatinId = monopatinId;
        this.totalTiempoConPausa = totalTiempoConPausa;
        this.totalTiempoSinPausa = totalTiempoSinPausa;
    }

    public ReporteUsoDTO(Long monopatinId, Double totalKmRecorridos, Long totalTiempoConPausa, Long totalTiempoSinPausa) {
        this.monopatinId = monopatinId;
        this.totalKmRecorridos = totalKmRecorridos;
        this.totalTiempoConPausa = totalTiempoConPausa;
        this.totalTiempoSinPausa = totalTiempoSinPausa;
    }

}
