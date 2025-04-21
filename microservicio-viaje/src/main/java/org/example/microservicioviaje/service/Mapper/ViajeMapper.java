package org.example.microservicioviaje.service.Mapper;
import org.example.microservicioviaje.entity.Viaje;
import org.example.shareddto.DTO.entity.ViajeDTO;
import org.springframework.stereotype.Component;

@Component
public class ViajeMapper {
    public Viaje mapToEntity(ViajeDTO viajeDTO) {
        Viaje viaje = new Viaje();
        viaje.setId(viajeDTO.getId());
        viaje.setCuentaId(viajeDTO.getCuentaId());
        viaje.setFechaInicio(viajeDTO.getFechaInicio());
        viaje.setFechaFin(viajeDTO.getFechaFin());
        viaje.setMonopatinId(viajeDTO.getMonopatinId());
        viaje.setEnPausa(viajeDTO.isEnPausa());
        viaje.setFuePausado(viajeDTO.isFuePausado());
        viaje.setCostoTotal(viajeDTO.getCostoTotal());
        viaje.setTotalKmRecorridos(viajeDTO.getTotalKmRecorridos());
        viaje.setTotalTiempo(viajeDTO.getTotalTiempo());
        viaje.setTotalTiempoUsoSinPausas(viajeDTO.getTotalTiempoUsoSinPausas());

        return viaje;
    }

    public ViajeDTO mapToDTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setCuentaId(viaje.getCuentaId());
        dto.setFechaInicio(viaje.getFechaInicio());
        dto.setFechaFin(viaje.getFechaFin());
        dto.setMonopatinId(viaje.getMonopatinId());
        dto.setEnPausa(viaje.isEnPausa());
        dto.setFuePausado(viaje.isFuePausado());
        dto.setCostoTotal(viaje.getCostoTotal());
        dto.setTotalKmRecorridos(viaje.getTotalKmRecorridos());
        dto.setTotalTiempo(viaje.getTotalTiempo());
        dto.setTotalTiempoUsoSinPausas(viaje.getTotalTiempoUsoSinPausas());

        return dto;
    }
}
