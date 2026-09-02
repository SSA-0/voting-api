package com.votaciones.api.dto;

// Resultado de un candidato dentro de GET /votes/statistics.
public class CandidateStatistics {

    private String candidateId;
    private String name;
    private String party;
    private long votes;
    private double percentage;

    public CandidateStatistics(String candidateId, String name, String party, long votes, double percentage) {
        this.candidateId = candidateId;
        this.name = name;
        this.party = party;
        this.votes = votes;
        this.percentage = percentage;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public long getVotes() {
        return votes;
    }

    public double getPercentage() {
        return percentage;
    }
}
