package org.example.microservicioadministrador.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "paradas")
@Data
public class Parada {
    @Id
    private String id;

    private String shortId;

    @Min(value = -90, message = "La latitud debe ser mayor o igual a -90")
    @Max(value = 90, message = "La latitud debe ser menor o igual a 90")
    private double latitud;

    @Min(value = -180, message = "La longitud debe ser mayor o igual a -180")
    @Max(value = 180, message = "La longitud debe ser menor o igual a 180")
    private double longitud;

    public Parada() {
    }

    public Parada(double latitud, double longitud) {
        this.shortId = generateShortId();
        this.latitud = latitud;
        this.longitud = longitud;

    }
    @PrePersist
    public void prePersist() {
        if (this.shortId == null) {
            this.shortId = generateShortId();
        }
    }

    public String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 2);
    }
}