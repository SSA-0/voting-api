package com.votaciones.api.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// Documento de MongoDB: coleccion "votes". Solo guarda referencias (IDs).
@Document(collection = "votes")
public class Vote {

    @Id
    private String id;

    @NotBlank(message = "El voter_id es obligatorio")
    private String voterId;

    @NotBlank(message = "El candidate_id es obligatorio")
    private String candidateId;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Vote() {
    }

    public Vote(String voterId, String candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
