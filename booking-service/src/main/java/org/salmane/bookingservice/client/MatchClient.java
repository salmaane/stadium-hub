package org.salmane.bookingservice.client;

import org.salmane.bookingservice.config.OAuthFeignConfig;
import org.salmane.bookingservice.dto.MatchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "match-service", path = "/api/matches", configuration = OAuthFeignConfig.class)
public interface MatchClient {

    @GetMapping("/{id}")
    public MatchResponse findMatchById(@PathVariable String id);
}