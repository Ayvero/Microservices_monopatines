package org.example.microservicioadministrador.repository;

import org.example.microservicioadministrador.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
   //tarifa actual
    Tarifa findTopByOrderByFechaDesc();

    //Busca tarifas anteriores a la fecha de actualizacion
   List<Tarifa> findByFechaBefore(LocalDate fecha);


    //Busca tarifas anteriores a la fecha de actualizacion
    List<Tarifa> findByFechaAfter(LocalDate fecha);

}