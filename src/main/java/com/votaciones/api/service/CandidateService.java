package com.votaciones.api.service;

import com.votaciones.api.dto.CandidateRequest;
import com.votaciones.api.exception.BusinessRuleException;
import com.votaciones.api.exception.ResourceNotFoundException;
import com.votaciones.api.model.Candidate;
import com.votaciones.api.repository.CandidateRepository;
import com.votaciones.api.repository.VoterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public CandidateService(CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    public Candidate createCandidate(CandidateRequest request) {
        // no puede ser candidato si ya esta registrado como votante (mismo nombre)
        boolean isVoter = voterRepository.findAll().stream()
                .anyMatch(v -> v.getName().equalsIgnoreCase(request.getName()));
        if (isVoter) {
            throw new BusinessRuleException("La persona ya esta registrada como votante y no puede ser candidato");
        }

        if (candidateRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new BusinessRuleException("Ya existe un candidato registrado con ese nombre");
        }

        Candidate candidate = new Candidate(request.getName(), request.getParty());
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates(int page, int size) {
        return candidateRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Candidate getCandidateById(String id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));
    }

    public void deleteCandidate(String id) {
        if (!candidateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Candidato no encontrado con id: " + id);
        }
        candidateRepository.deleteById(id);
    }
}
