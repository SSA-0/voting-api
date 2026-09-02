package com.votaciones.api.repository;

import com.votaciones.api.model.Voter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// MongoRepository ya da save/findById/findAll/deleteById gratis.
// Los metodos de abajo los implementa Spring Data solo, por el nombre.
public interface VoterRepository extends MongoRepository<Voter, String> {

    Optional<Voter> findByEmail(String email);

    boolean existsByEmail(String email);
}
