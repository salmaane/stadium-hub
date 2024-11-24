package org.salmane.matchservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.matchservice.Enum.MatchStatus;
import org.salmane.matchservice.client.TicketClient;
import org.salmane.matchservice.dao.MatchDAO;
import org.salmane.matchservice.dto.MatchRequest;
import org.salmane.matchservice.dto.MatchResponse;
import org.salmane.matchservice.dto.MatchUpdateRequest;
import org.salmane.matchservice.exception.TicketsCreationFailureException;
import org.salmane.matchservice.model.Match;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchDAO matchDAO;
    private final TicketClient ticketClient;

    public List<MatchResponse> findAll() {
        return matchDAO.findAll().stream().map(match -> new MatchResponse(
                match.getId(), match.getKickoffTime(), match.getStadium(), match.getStatus(),
                match.getCity(), match.getHomeTeam(), match.getAwayTeam(), match.getSeatCategories()
        )).toList();
    }

    public MatchResponse findById(String id) {
        Match match = matchDAO.findById(id).orElseThrow();
        return new MatchResponse(
            match.getId(), match.getKickoffTime(), match.getStadium(),
            match.getStatus(), match.getCity(), match.getHomeTeam(),
            match.getAwayTeam(), match.getSeatCategories()
        );
    }

    public MatchResponse createMatch(MatchRequest matchRequest) {

        Match match = Match.builder()
                .id(UUID.randomUUID().toString())
                .status(MatchStatus.SCHEDULED)
                .seatCategories(matchRequest.seatCategories())
                .city(matchRequest.city())
                .stadium(matchRequest.stadium())
                .kickoffTime(matchRequest.kickoffTime())
                .homeTeam(matchRequest.homeTeam())
                .awayTeam(matchRequest.awayTeam())
                .build();

        //**
        // tickets creation
        //**
        if(!ticketClient.generateMatchTickets(match.getId(), match.getSeatCategories())) {
            throw new TicketsCreationFailureException("Failed to create tickets for match " + match.getId());
        }

        matchDAO.save(match);
        log.info("match with id {} created succeffully", match.getId());
        return new MatchResponse(
                match.getId(), match.getKickoffTime(), match.getStadium(),
                match.getStatus(), match.getCity(), match.getHomeTeam(),
                match.getAwayTeam(), match.getSeatCategories()
        );
    }

    public MatchResponse updateMatch(String id, MatchUpdateRequest matchRequest) {
        Match match = matchDAO.findById(id).orElseThrow();

        match.setStatus(matchRequest.status());
        match.setCity(matchRequest.city());
        match.setStadium(matchRequest.stadium());
        match.setKickoffTime(matchRequest.kickoffTime());
        match.setHomeTeam(matchRequest.homeTeam());
        match.setAwayTeam(matchRequest.awayTeam());
        match.setSeatCategories(matchRequest.seatCategories());

        matchDAO.save(match);

        return new MatchResponse(
                match.getId(), match.getKickoffTime(), match.getStadium(),
                match.getStatus(), match.getCity(), match.getHomeTeam(),
                match.getAwayTeam(), match.getSeatCategories()
        );
    }

    public void deleteMatchById(String id) {
        ticketClient.deleteByMatch(id);
        matchDAO.deleteById(id);
    }
}
