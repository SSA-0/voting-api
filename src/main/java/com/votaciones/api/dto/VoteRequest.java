package com.votaciones.api.dto;

import jakarta.validation.constraints.NotBlank;

// Cuerpo esperado en POST /votes.
public class VoteRequest {

    @NotBlank(message = "El voter_id es obligatorio")
    private String voterId;

    @NotBlank(message = "El candidate_id es obligatorio")
    private String candidateId;

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
}
