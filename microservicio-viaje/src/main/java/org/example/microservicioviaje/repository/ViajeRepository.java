package org.example.microservicioviaje.repository;


import org.example.microservicioviaje.entity.Viaje;
import org.example.shareddto.DTO.ReporteMonopatinDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    //Lista de viajes asociado a un monopatin
    List<Viaje> findByMonopatinId(@Param("monopatinId") long monopatinId);


    //Punto d- Total facturado en un rango de meses de cierto anio
    @Query("SELECT COALESCE(SUM(v.costoTotal), 0) FROM Viaje v WHERE YEAR(v.fechaInicio) = :anio " +
            "AND MONTH(v.fechaInicio) BETWEEN :mesInicio AND :mesFin")
    Double calcularTotalFacturado(@Param("anio") int anio,
                                  @Param("mesInicio") int mesInicio,
                                  @Param("mesFin") int mesFin);


    @Query("SELECT new org.example.shareddto.DTO.ReporteMonopatinDTO(v.monopatinId,  COUNT(v.id)) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaInicio) = :anio " +
            "GROUP BY v.monopatinId " +
            "HAVING COUNT(v.id) > :minViajes")
    List<ReporteMonopatinDTO> findMonopatinesConMasDeXViajes(@Param("anio") int anio, @Param("minViajes") long minViajes);

}