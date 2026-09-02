package com.votaciones.api.repository;

import com.votaciones.api.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {

    // el voto de un votante especifico (deberia haber a lo sumo uno)
    Optional<Vote> findByVoterId(String voterId);

    // cuantos votos tiene un candidato
    long countByCandidateId(String candidateId);
}
