package org.example.microservicioadministrador.service.mapper;

import org.example.microservicioadministrador.entity.Tarifa;
import org.example.shareddto.DTO.entity.TarifaDTO;
import org.springframework.stereotype.Component;

@Component
public class TarifaMapper {

    public Tarifa mapToEntity(TarifaDTO tarifaDTO) {
        Tarifa tarifa = new Tarifa();
        tarifa.setId(tarifaDTO.getId());
        tarifa.setFecha(tarifaDTO.getFecha());
        tarifa.setTarifaBasePorMinuto(tarifaDTO.getTarifaBasePorMinuto());
        tarifa.setTarifaExtraPorMinuto(tarifaDTO.getTarifaExtraPorMinuto());
        return tarifa;
    }

    public TarifaDTO mapToDTO(Tarifa tarifa) {
        TarifaDTO dto = new TarifaDTO();
        dto.setId(tarifa.getId());
        dto.setFecha(tarifa.getFecha());
        dto.setTarifaBasePorMinuto(tarifa.getTarifaBasePorMinuto());
        dto.setTarifaExtraPorMinuto(tarifa.getTarifaExtraPorMinuto());
        return dto;
    }
}
