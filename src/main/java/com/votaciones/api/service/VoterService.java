package com.votaciones.api.service;

import com.votaciones.api.dto.VoterRequest;
import com.votaciones.api.exception.BusinessRuleException;
import com.votaciones.api.exception.ResourceNotFoundException;
import com.votaciones.api.model.Voter;
import com.votaciones.api.repository.CandidateRepository;
import com.votaciones.api.repository.VoterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter createVoter(VoterRequest request) {
        if (voterRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un votante registrado con ese email");
        }

        // no puede ser votante si ya esta registrado como candidato (mismo nombre)
        boolean isCandidate = candidateRepository.findByNameIgnoreCase(request.getName()).isPresent();
        if (isCandidate) {
            throw new BusinessRuleException("La persona ya esta registrada como candidato y no puede ser votante");
        }

        Voter voter = new Voter(request.getName(), request.getEmail());
        return voterRepository.save(voter);
    }

    public List<Voter> getAllVoters(int page, int size) {
        return voterRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Voter getVoterById(String id) {
        return voterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Votante no encontrado con id: " + id));
    }

    public void deleteVoter(String id) {
        if (!voterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Votante no encontrado con id: " + id);
        }
        voterRepository.deleteById(id);
    }
}
