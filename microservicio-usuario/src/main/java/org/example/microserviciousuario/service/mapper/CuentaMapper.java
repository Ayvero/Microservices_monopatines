package org.example.microserviciousuario.service.mapper;

import org.example.microserviciousuario.entity.Cuenta;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuentaMapper {
    public Cuenta mapToEntity(CuentaDTO cuentaDTO) {
        if (cuentaDTO == null) {
            return null;
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(cuentaDTO.getCuentaId());
        cuenta.setFechaAlta(cuentaDTO.getFechaAlta());
        cuenta.setSaldo(cuentaDTO.getSaldo());
        cuenta.setIdMercadoPago(cuentaDTO.getIdMercadoPago());
        cuenta.setAnulada(cuentaDTO.isAnulada());
        return cuenta;
    }

    public CuentaDTO mapToDTO(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        return new CuentaDTO(
                cuenta.getCuentaId(),
                cuenta.getFechaAlta(),
                cuenta.getSaldo(),
                cuenta.getIdMercadoPago(),
                cuenta.isAnulada()
        );
    }

    public List<Cuenta> mapToEntityList(List<CuentaDTO> cuentaDTOs) {
        if (cuentaDTOs == null) {
            return null;
        }

        return cuentaDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    public List<CuentaDTO> mapToDTOList(List<Cuenta> cuentas) {
        if (cuentas == null) {
            return null;
        }

        return cuentas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
