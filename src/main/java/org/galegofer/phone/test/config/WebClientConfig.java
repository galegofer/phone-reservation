package org.galegofer.phone.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${serviceHost}")
    private String host;

    @Bean
    WebClient.Builder builder() {
        return WebClient.builder()
                .baseUrl(host)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()));
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
