package org.example.microservicioviaje.service.Mapper;


import org.example.microservicioviaje.entity.Pausa;
import org.example.shareddto.DTO.entity.PausaDTO;
import org.springframework.stereotype.Component;
import org.example.microservicioviaje.entity.Viaje;
@Component
public class PausaMapper {

    public Pausa mapToEntity(PausaDTO pausaDTO) {
        Pausa pausa = new Pausa();
        pausa.setId(pausaDTO.getId());
        pausa.setHoraInicio(pausaDTO.getHoraInicio());
        pausa.setHoraFin(pausaDTO.getHoraFin());
        pausa.setDuracion(pausaDTO.getDuracion());
        Viaje viaje = new Viaje();
        viaje.setId(pausaDTO.getViajeId());
        pausa.setViaje(viaje);
        return pausa;
    }


    public PausaDTO mapToDTO(Pausa pausa) {
        PausaDTO dto = new PausaDTO();
        dto.setId(pausa.getId());
        dto.setViajeId(pausa.getViaje().getId());
        dto.setHoraInicio(pausa.getHoraInicio());
        dto.setHoraFin(pausa.getHoraFin());
        dto.setDuracion(pausa.getDuracion());
        return dto;
    }
}