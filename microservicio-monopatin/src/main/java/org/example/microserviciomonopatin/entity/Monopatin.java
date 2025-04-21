package org.example.microserviciomonopatin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monopatin_id", nullable = false, unique = true)
    private Long id;

    @NotNull(message = "El modelo no puede ser nulo")
    @NotEmpty(message = "El modelo no puede estar vacío")
    private String modelo;

    @Min(value = -90, message = "La latitud debe ser mayor o igual a -90")
    @Max(value = 90, message = "La latitud debe ser menor o igual a 90")
    private double latitud;

    @Min(value = -180, message = "La longitud debe estar entre -180 y 180")
    @Max(value = 180, message = "La longitud debe estar entre -180 y 180")
    private double longitud;

    @NotNull(message = "El estado no puede ser nulo")
    @Pattern(regexp = "en-operacion|en-mantenimiento", message = "El estado debe ser 'en-operacion' o 'en-mantenimiento'")
    private String estado;

    private boolean disponible;

    @Min(value = 0, message = "El tiempo de uso con pausa no puede ser negativo")
    private Long tiempoUsoConPausa;


    @NotNull(message = "El tiempo no puede ser nulo")
    @Min(value = 0, message = "El tiempo de uso sin pausa no puede ser negativo")
    private Long tiempoUsoSinPausa;

    @NotNull(message = "Los km no pueden ser nulos")
    @Min(value = 0, message = "El total de kilómetros recorridos no puede ser negativo")
    private double totalKmRecorridos;



    @Override
    public String toString() {
        return "Monopatin{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                '}';
    }
}
