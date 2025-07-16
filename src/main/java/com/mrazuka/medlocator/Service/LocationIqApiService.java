package com.mrazuka.medlocator.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LocationIqApiService {

    private final WebClient webClient;

    public LocationIqApiService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

//    public Mono<String> convertAdressToCoordinates(String address) {}

//    public Mono<String> compareCoordinates(String latitude, String longitude) {}
}
