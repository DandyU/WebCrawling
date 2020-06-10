package com.test.crawler.common.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;

@Service
public class XHttpClientImpl implements XHttpClient {
    private static final Logger log = LoggerFactory.getLogger("api");
    private ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;

    public XHttpClientImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        webClient = webClientBuilder.build();
    }

    @Override
    public Flux<DataBuffer> get(URI uri) {
        log.info("GET " + uri);
        final Flux<DataBuffer> dataBufferFlux = webClient.get().uri(uri)
                .accept(MediaType.TEXT_HTML)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        return dataBufferFlux;
    }
}
