package org.example.microservicioviaje.repository;

import org.example.microservicioviaje.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PausaRepository extends JpaRepository<Pausa, Long> {

   //Calcula el total de tiempo pausado por viaje id
    @Query("SELECT SUM(p.duracion) FROM Pausa p WHERE p.viaje.id = :viajeId")
    Long calcularDuracionPausasPorViaje(Long viajeId);


    //Devuelve si hay, la primer pausa que excede el minimo permitido para costo base
    @Query("SELECT p FROM Pausa p WHERE p.viaje.id = :viajeId AND p.duracion > :minPausa ORDER BY p.horaInicio ASC")
    Optional<Pausa> primerPausaExcedida(@Param("viajeId") Long viajeId, @Param("minPausa") long minPausa);
}
