package com.votaciones.api.dto;

import jakarta.validation.constraints.NotBlank;

// Cuerpo esperado en POST /candidates.
public class CandidateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String party;

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
}
