package org.example.microservicioviaje.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "pausas")
public class Pausa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    @Column(name = "hora_inicio", nullable = false)
    private LocalDateTime horaInicio;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    private Long duracion;

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
        calcularDuracion();
    }

    public void calcularDuracion() {
        if (horaInicio != null && horaFin != null) {
            this.duracion = Duration.between(horaInicio, horaFin).toMinutes();
        } else {
            this.duracion = 0L;  // Si no hay horaInicio o horaFin, establecemos la duración en 0
        }
    }


}