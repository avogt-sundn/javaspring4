package com.example.javaspring4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = HelloController.class)
@AutoConfigureWebTestClient
public class HelloControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void helloReturnsMessage() {
        webTestClient.get()
                .uri("/api/hello")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.message").isEqualTo("Hello, world!");
    }

}
