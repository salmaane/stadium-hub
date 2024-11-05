package org.salmane.matchservice.config;

import lombok.RequiredArgsConstructor;
import org.salmane.matchservice.client.TicketClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class TicketClientConfig {

    private final DiscoveryClient discoveryClient;

    @Bean
    public TicketClient ticketClient() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("ticket-service").get(0);
        RestClient restClient = RestClient.builder()
                .baseUrl(serviceInstance.getUri().toString())
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(TicketClient.class);
    }
}
