package org.salmane.matchservice.config;

import org.salmane.matchservice.client.TicketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class TicketClientConfig {

    @Value("${ticket.url}")
    private String ticketServerUrl;

    @Bean
    public TicketClient ticketClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(ticketServerUrl)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(TicketClient.class);
    }
}
