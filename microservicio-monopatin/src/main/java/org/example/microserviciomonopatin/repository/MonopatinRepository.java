package org.example.microserviciomonopatin.repository;


import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

   //Reporte estado
    long countByEstado(String estado);

    //ReporteUso
    @Query("SELECT new org.example.shareddto.DTO.ReporteUsoDTO(m.id, m.totalKmRecorridos, m.tiempoUsoConPausa, m.tiempoUsoSinPausa) "
            + "FROM Monopatin m")
    List<ReporteUsoDTO> findAllReporteUso();

    // Obtener reporte por kilómetros
    @Query("SELECT new org.example.shareddto.DTO.ReporteUsoDTO(m.id, m.totalKmRecorridos) FROM Monopatin m")
    List<ReporteUsoDTO> obtenerReporteKm();

    // Obtener reporte de tiempos, condicionalmente incluyendo o excluyendo el tiempo sin pausa
    @Query("SELECT new org.example.shareddto.DTO.ReporteUsoDTO(m.id, m.totalKmRecorridos, m.tiempoUsoConPausa, " +
            "CASE WHEN :excludeSinPausa = false THEN m.tiempoUsoSinPausa ELSE null END) " +
            "FROM Monopatin m")
    List<ReporteUsoDTO> obtenerReporteTiempos(@Param("excludeSinPausa") boolean excludeSinPausa);






}

