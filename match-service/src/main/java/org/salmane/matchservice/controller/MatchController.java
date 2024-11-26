package org.salmane.matchservice.controller;

import lombok.RequiredArgsConstructor;
import org.salmane.matchservice.dto.MatchRequest;
import org.salmane.matchservice.dto.MatchResponse;
import org.salmane.matchservice.dto.MatchUpdateRequest;
import org.salmane.matchservice.service.MatchService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
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

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public MatchResponse createMatch(@RequestBody MatchRequest matchRequest) {
        return matchService.createMatch(matchRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public MatchResponse updateMatch(@PathVariable String id,@RequestBody MatchUpdateRequest matchRequest) {
        return matchService.updateMatch(id, matchRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public void deleteMatchById(@PathVariable String id) {
        matchService.deleteMatchById(id);
    }

}
