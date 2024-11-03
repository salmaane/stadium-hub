package org.salmane.matchservice.controller;

import lombok.RequiredArgsConstructor;
import org.salmane.matchservice.dto.MatchRequest;
import org.salmane.matchservice.dto.MatchResponse;
import org.salmane.matchservice.dto.MatchUpdateRequest;
import org.salmane.matchservice.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public List<MatchResponse> getAllMatches() {
        return matchService.findAll();
    }

    @GetMapping("/{id}")
    public MatchResponse findMatchById(@PathVariable String id) {
        return matchService.findById(id);
    }

    @PostMapping
    public MatchResponse createMatch(@RequestBody MatchRequest matchRequest) {
        return matchService.createMatch(matchRequest);
    }

    @PutMapping("/{id}")
    public MatchResponse updateMatch(@PathVariable String id,@RequestBody MatchUpdateRequest matchRequest) {
        return matchService.updateMatch(id, matchRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMatchById(@RequestParam String id) {
        matchService.deleteMatchById(id);
    }

}
