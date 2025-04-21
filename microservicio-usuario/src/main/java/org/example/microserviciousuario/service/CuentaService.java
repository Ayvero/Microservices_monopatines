package org.example.microserviciousuario.service;


import jakarta.validation.Valid;
import org.example.microserviciousuario.entity.Cuenta;
import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.exception.CuentaAnuladaException;
import org.example.microserviciousuario.exception.CuentaNotFoundException;
import org.example.microserviciousuario.exception.SaldoInsuficienteException;
import org.example.microserviciousuario.exception.UsuarioNotFoundException;
import org.example.microserviciousuario.repository.CuentaRepository;
import org.example.microserviciousuario.repository.UsuarioRepository;
import org.example.microserviciousuario.service.mapper.CuentaMapper;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    //Crear cuenta
    public Cuenta save(@Valid CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaMapper.mapToEntity(cuentaDTO);
        return cuentaRepository.save(cuenta);
    }

    //Leer una cuenta
    public CuentaDTO findById(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return cuentaMapper.mapToDTO(cuenta);
    }

    //Crear cuentas
    public List<Cuenta> agregarCuentas(List<CuentaDTO> cuentasDTO) {
         List<Cuenta> cuentas = cuentaMapper.mapToEntityList(cuentasDTO);
        return cuentaRepository.saveAll(cuentas);
    }

    //Listar cuentas
    public List<CuentaDTO> findAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        return cuentaMapper.mapToDTOList(cuentas);
    }

    //Modificar cuenta
    public Cuenta updateCuenta(Long id, @Valid CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + id));

        if (cuentaDTO.getSaldo() != null && cuentaDTO.getSaldo() >= 0) {
            cuenta.setSaldo(cuentaDTO.getSaldo());
        } else {
           throw new IllegalArgumentException("El saldo debe ser mayor o igual a 0");
        }
        if (cuentaDTO.getIdMercadoPago() != null && !cuentaDTO.getIdMercadoPago().isEmpty()) {
            cuenta.setIdMercadoPago(cuentaDTO.getIdMercadoPago());
        } else {
            throw new IllegalArgumentException("El ID de Mercado Pago no puede ser vacío");
        }
        if (cuentaDTO.getFechaAlta() != null) {
            cuenta.setFechaAlta(cuentaDTO.getFechaAlta());
        } else {
           throw new IllegalArgumentException("La fecha de alta no puede ser nula");
        }
        cuenta.setAnulada(cuentaDTO.isAnulada());
        return cuentaRepository.save(cuenta);
    }


    //Eliminar cuenta
    public void delete(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setAnulada(true); // O puedes eliminar de forma lógica
        cuentaRepository.delete(cuenta);
    }

    public boolean existeCuenta(Long cuentaId) {
        return cuentaRepository.existsById(cuentaId);
    }


    public void asociarUsuarioACuenta(Long cuentaId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new CuentaAnuladaException("No se puede asociar un usuario a una cuenta anulada");
        }
        if (!cuenta.getUsuarios().contains(usuario)) {
            cuenta.agregarUsuario(usuario);
            cuentaRepository.save(cuenta);
        }
    }

  //Descuenta saldo de la cuenta del usuario
    public String descontarSaldo(Long cuentaId, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor o igual a 0");
        }
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new IllegalArgumentException("La cuenta está anulada y no se puede realizar esta operación");
        }
        if (cuenta.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la operación");
        }
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        cuentaRepository.save(cuenta);
        return "Saldo descontado correctamente";
    }

    //Punto b- para poder anular una cuenta
    public void anularCuenta(Long cuentaId, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new RuntimeException("La cuenta ya está anulada" );
        }

        cuenta.setAnulada(cuentaDTO.isAnulada());

        cuentaRepository.save(cuenta);
    }
}
