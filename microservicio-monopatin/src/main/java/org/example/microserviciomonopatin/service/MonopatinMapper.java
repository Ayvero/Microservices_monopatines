package org.example.microserviciomonopatin.service;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.stereotype.Component;

@Component
public class MonopatinMapper {
    public MonopatinDTO mapToDTO(Monopatin monopatin) {
        if (monopatin == null) {
            return null;
        }

        return new MonopatinDTO(
                monopatin.getId(),
                monopatin.getModelo(),
                monopatin.getLatitud(),
                monopatin.getLongitud(),
                monopatin.getEstado(),
                monopatin.isDisponible(),
                monopatin.getTotalKmRecorridos(),
                monopatin.getTiempoUsoConPausa(),
                monopatin.getTiempoUsoSinPausa()
        );
    }


    public Monopatin mapToEntity(MonopatinDTO monopatinDTO) {
        if (monopatinDTO == null) {
            return null;
        }

        Monopatin monopatin = new Monopatin();
        monopatin.setId(monopatinDTO.getId());
        monopatin.setModelo(monopatinDTO.getModelo());
        monopatin.setLatitud(monopatinDTO.getLatitud());
        monopatin.setLongitud(monopatinDTO.getLongitud());
        monopatin.setEstado(monopatinDTO.getEstado());
        monopatin.setDisponible(monopatinDTO.isDisponible());
        monopatin.setTotalKmRecorridos(monopatinDTO.getTotalKmRecorridos());
        monopatin.setTiempoUsoConPausa(monopatinDTO.getTiempoUsoConPausa());
        monopatin.setTiempoUsoSinPausa(monopatinDTO.getTiempoUsoSinPausa());

        return monopatin;
    }
}