package com.votaciones.api.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Documento de MongoDB: coleccion "candidates".
@Document(collection = "candidates")
public class Candidate {

    @Id
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String party; // opcional

    private long votes = 0;

    public Candidate() {
    }

    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
        this.votes = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
