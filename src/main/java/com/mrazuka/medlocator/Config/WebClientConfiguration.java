package com.mrazuka.medlocator.Config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient.Builder webClientBuilder() {

        HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient ->
                tcpClient
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))
                        )
        );

        // Setup ExchangeStrategies to avoid buffer limit issues (if needed)
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(exchangeStrategies);
    }
}
