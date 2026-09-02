package com.votaciones.api.controller;

import com.votaciones.api.dto.CandidateRequest;
import com.votaciones.api.model.Candidate;
import com.votaciones.api.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Candidatos", description = "Gestion de candidatos")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    // POST /candidates -> registra un candidato nuevo
    @PostMapping
    @Operation(summary = "Registrar un nuevo candidato")
    public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody CandidateRequest request) {
        Candidate candidate = candidateService.createCandidate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
    }

    // GET /candidates?page=0&size=10 -> lista paginada
    @GetMapping
    @Operation(summary = "Obtener la lista de candidatos (con paginacion)")
    public ResponseEntity<List<Candidate>> getAllCandidates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(candidateService.getAllCandidates(page, size));
    }

    // GET /candidates/{id} -> un candidato especifico
    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un candidato por ID")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    // DELETE /candidates/{id} -> elimina, responde 204 sin cuerpo
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un candidato")
    public ResponseEntity<Void> deleteCandidate(@PathVariable String id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
