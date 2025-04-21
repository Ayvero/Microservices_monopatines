package org.example.microservicioadministrador.repository;

import org.example.microservicioadministrador.entity.Parada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, String> {
    Optional<Parada> findByShortId(String shortId);
}
