package org.example.microservicioviaje.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_id", nullable = false)
    @NotNull(message = "El ID de la cuenta no puede ser nulo.")
    private Long cuentaId;

    @Column(nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria.")
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @Column(nullable = false)
    @Min(value = 1, message = "El monopatín ID debe ser un valor positivo.")
    private Long monopatinId;

    @NotNull(message = "El estado de pausa no puede ser nulo.")
    private boolean enPausa;

    private boolean fuePausado;

    private Double costoTotal;

    @Min(value = 1, message = "El tiempo máximo de pausa no puede ser menor que 1 minuto.")
    private final long pausaMAX = 15;

    private Double totalKmRecorridos;

    @Min(value = 0, message = "El total de tiempo no puede ser negativo.")
    private Long totalTiempo;

    private Long totalTiempoUsoSinPausas;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pausa> pausas;






    @Override
    public String toString() {
        return "Viaje{" +
                ", id=" + id +
                "monopatin=" + monopatinId +

                '}';
    }
}

