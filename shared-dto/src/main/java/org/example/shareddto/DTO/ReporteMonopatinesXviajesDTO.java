package org.example.shareddto.DTO;

import lombok.Data;

import java.util.List;
@Data
public class ReporteMonopatinesXviajesDTO {
    private List<ReporteMonopatinDTO> monopatines;
    private long cantidadMonopatines;
    private long minimoViajes;


    public ReporteMonopatinesXviajesDTO(List<ReporteMonopatinDTO> monopatines, long cantidadMonopatines, Long minViajes) {
        this.monopatines = monopatines;
        this.cantidadMonopatines = cantidadMonopatines;
        this.minimoViajes = minViajes;
    }

    public String obtenerMensaje() {
        return "Monopatines con más de " + minimoViajes + " viajes.";
    }
}
