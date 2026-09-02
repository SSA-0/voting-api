package com.votaciones.api.dto;

import java.util.List;

// Respuesta completa de GET /votes/statistics.
public class VotingStatistics {

    private long totalVotes;
    private long totalVoters;
    private long votersWhoVoted;
    private List<CandidateStatistics> results;

    public VotingStatistics(long totalVotes, long totalVoters, long votersWhoVoted, List<CandidateStatistics> results) {
        this.totalVotes = totalVotes;
        this.totalVoters = totalVoters;
        this.votersWhoVoted = votersWhoVoted;
        this.results = results;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public long getTotalVoters() {
        return totalVoters;
    }

    public long getVotersWhoVoted() {
        return votersWhoVoted;
    }

    public List<CandidateStatistics> getResults() {
        return results;
    }
}
