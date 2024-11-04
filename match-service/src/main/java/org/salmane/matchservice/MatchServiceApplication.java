package org.salmane.matchservice;

import org.salmane.matchservice.dao.MatchDAO;
import org.salmane.matchservice.dto.MatchRequest;
import org.salmane.matchservice.model.Match;
import org.salmane.matchservice.service.MatchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableMongoAuditing
public class MatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(MatchService matchService) {
        return args -> {
            List<MatchRequest> matches = List.of(
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 15, 18, 30),
                            "Stadium A",
                            List.of(new Match.SeatCategory("VIP", 10000, 500), new Match.SeatCategory("Regular", 20000, 100)),
                            "City X",
                            "Team Alpha",
                            "Team Beta"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 16, 20, 0),
                            "Stadium B",
                            List.of(new Match.SeatCategory("VIP", 8000, 500), new Match.SeatCategory("Regular", 22000, 100)),
                            "City Y",
                            "Team Gamma",
                            "Team Delta"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 17, 17, 45),
                            "Stadium C",
                            List.of(new Match.SeatCategory("Economy", 30000, 50), new Match.SeatCategory("Regular", 15000, 100)),
                            "City Z",
                            "Team Epsilon",
                            "Team Zeta"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 18, 19, 15),
                            "Stadium D",
                            List.of(new Match.SeatCategory("VIP", 5000,500), new Match.SeatCategory("Premium", 20000, 200), new Match.SeatCategory("Regular", 30000, 100)),
                            "City W",
                            "Team Theta",
                            "Team Iota"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 19, 16, 0),
                            "Stadium E",
                            List.of(new Match.SeatCategory("Economy", 25000, 50), new Match.SeatCategory("VIP", 5000,500)),
                            "City V",
                            "Team Kappa",
                            "Team Lambda"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 20, 18, 30),
                            "Stadium F",
                            List.of(new Match.SeatCategory("VIP", 7000,500), new Match.SeatCategory("Regular", 28000, 100)),
                            "City U",
                            "Team Mu",
                            "Team Nu"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 21, 21, 0),
                            "Stadium G",
                            List.of(new Match.SeatCategory("VIP", 15000,500), new Match.SeatCategory("Economy", 45000, 50)),
                            "City T",
                            "Team Xi",
                            "Team Omicron"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 22, 15, 30),
                            "Stadium H",
                            List.of(new Match.SeatCategory("Regular", 30000, 100), new Match.SeatCategory("Premium", 17000,500)),
                            "City S",
                            "Team Pi",
                            "Team Rho"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 23, 20, 15),
                            "Stadium I",
                            List.of(new Match.SeatCategory("VIP", 12000,500), new Match.SeatCategory("Economy", 40000, 50)),
                            "City Q",
                            "Team Sigma",
                            "Team Tau"
                    ),
                    new MatchRequest(
                            LocalDateTime.of(2024, 11, 24, 17, 0),
                            "Stadium J",
                            List.of(new Match.SeatCategory("VIP", 10000,500), new Match.SeatCategory("Regular", 40000, 100)),
                            "City P",
                            "Team Upsilon",
                            "Team Phi"
                    )
            );

            matches.stream().forEach((matchRequest) -> {
                matchService.createMatch(matchRequest);
            });
        };
    }

}
