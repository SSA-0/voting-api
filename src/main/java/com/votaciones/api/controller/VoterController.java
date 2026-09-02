package com.votaciones.api.controller;

import com.votaciones.api.dto.VoterRequest;
import com.votaciones.api.model.Voter;
import com.votaciones.api.service.VoterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters")
@Tag(name = "Votantes", description = "Gestion de votantes")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    // POST /voters -> registra un votante nuevo
    @PostMapping
    @Operation(summary = "Registrar un nuevo votante")
    public ResponseEntity<Voter> createVoter(@Valid @RequestBody VoterRequest request) {
        Voter voter = voterService.createVoter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(voter);
    }

    // GET /voters?page=0&size=10 -> lista paginada
    @GetMapping
    @Operation(summary = "Obtener la lista de votantes (con paginacion)")
    public ResponseEntity<List<Voter>> getAllVoters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(voterService.getAllVoters(page, size));
    }

    // GET /voters/{id} -> un votante especifico
    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un votante por ID")
    public ResponseEntity<Voter> getVoterById(@PathVariable String id) {
        return ResponseEntity.ok(voterService.getVoterById(id));
    }

    // DELETE /voters/{id} -> elimina, responde 204 sin cuerpo
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un votante")
    public ResponseEntity<Void> deleteVoter(@PathVariable String id) {
        voterService.deleteVoter(id);
        return ResponseEntity.noContent().build();
    }
}
