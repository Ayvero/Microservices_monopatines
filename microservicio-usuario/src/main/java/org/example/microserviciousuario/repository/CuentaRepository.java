package org.example.microserviciousuario.repository;


import org.example.microserviciousuario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
