package com.test.crawler.common.http;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.UnknownHostException;

public interface XHttpClient {

    Flux<DataBuffer> get(URI uri);

}
