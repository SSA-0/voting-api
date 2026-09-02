package com.votaciones.api.controller;

import com.votaciones.api.dto.VoteRequest;
import com.votaciones.api.dto.VotingStatistics;
import com.votaciones.api.model.Vote;
import com.votaciones.api.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
@Tag(name = "Votos", description = "Emision de votos y estadisticas")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // POST /votes -> emite un voto (la validacion vive en VoteService)
    @PostMapping
    @Operation(summary = "Emitir un voto")
    public ResponseEntity<Vote> castVote(@Valid @RequestBody VoteRequest request) {
        Vote vote = voteService.castVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    // GET /votes -> todos los votos emitidos
    @GetMapping
    @Operation(summary = "Obtener todos los votos emitidos")
    public ResponseEntity<List<Vote>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    // GET /votes/statistics -> totales y porcentajes por candidato
    @GetMapping("/statistics")
    @Operation(summary = "Obtener estadisticas de la votacion")
    public ResponseEntity<VotingStatistics> getStatistics() {
        return ResponseEntity.ok(voteService.getStatistics());
    }
}
