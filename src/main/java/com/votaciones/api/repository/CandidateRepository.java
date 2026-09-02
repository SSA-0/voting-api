package com.votaciones.api.repository;

import com.votaciones.api.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

    // busca por nombre sin importar mayus/minus
    Optional<Candidate> findByNameIgnoreCase(String name);
}
