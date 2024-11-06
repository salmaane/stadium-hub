package org.salmane.matchservice;

import org.salmane.matchservice.dao.MatchDAO;
import org.salmane.matchservice.dto.MatchRequest;
import org.salmane.matchservice.model.Match;
import org.salmane.matchservice.service.MatchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableMongoAuditing
@EnableFeignClients
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
                            "Stade d'honeur",
                            List.of(
                                    new Match.SeatCategory("VIP", 500, 500),
                                    new Match.SeatCategory("Tribune", 15_000, 200),
                                    new Match.SeatCategory("Regular", 25_000, 100)
                            ),
                            "Casablanca",
                            "Raja Casablanca",
                            "Wydad Casablanca"
                    )
            );

            matches.stream().forEach((matchRequest) -> {
                matchService.createMatch(matchRequest);
            });
        };
    }

}
