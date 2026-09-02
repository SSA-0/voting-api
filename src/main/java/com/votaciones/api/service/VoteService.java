package com.votaciones.api.service;

import com.votaciones.api.dto.CandidateStatistics;
import com.votaciones.api.dto.VoteRequest;
import com.votaciones.api.dto.VotingStatistics;
import com.votaciones.api.exception.BusinessRuleException;
import com.votaciones.api.exception.ResourceNotFoundException;
import com.votaciones.api.model.Candidate;
import com.votaciones.api.model.Vote;
import com.votaciones.api.model.Voter;
import com.votaciones.api.repository.CandidateRepository;
import com.votaciones.api.repository.VoteRepository;
import com.votaciones.api.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    public VoteService(VoteRepository voteRepository, VoterRepository voterRepository,
                        CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    // Emite un voto: valida, guarda el voto, y actualiza votante + candidato.
    public Vote castVote(VoteRequest request) {
        Voter voter = voterRepository.findById(request.getVoterId())
                .orElseThrow(() -> new ResourceNotFoundException("Votante no encontrado con id: " + request.getVoterId()));

        if (voter.isHasVoted()) {
            throw new BusinessRuleException("El votante ya ha emitido su voto");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + request.getCandidateId()));

        Vote vote = new Vote(voter.getId(), candidate.getId());
        Vote savedVote = voteRepository.save(vote);

        // actualiza los contadores relacionados
        voter.setHasVoted(true);
        voterRepository.save(voter);

        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);

        return savedVote;
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // Calcula totales y el porcentaje de cada candidato.
    public VotingStatistics getStatistics() {
        List<Candidate> candidates = candidateRepository.findAll();
        long totalVotes = voteRepository.count();
        long totalVoters = voterRepository.count();
        long votersWhoVoted = voterRepository.findAll().stream()
                .filter(Voter::isHasVoted)
                .count();

        List<CandidateStatistics> results = candidates.stream()
                .map(c -> {
                    double percentage = totalVotes == 0 ? 0.0 : (c.getVotes() * 100.0) / totalVotes;
                    return new CandidateStatistics(c.getId(), c.getName(), c.getParty(), c.getVotes(),
                            Math.round(percentage * 100.0) / 100.0); // redondeo a 2 decimales
                })
                .collect(Collectors.toList());

        return new VotingStatistics(totalVotes, totalVoters, votersWhoVoted, results);
    }
}
