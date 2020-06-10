package com.test.crawler.api;

import com.test.crawler.api.vo.ResponseInfo;
import com.test.crawler.common.http.XHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.URI;
import java.util.LinkedList;

@Service
public class WebCrawlingServiceImpl implements WebCrawlingService {

    private static final Logger log = LoggerFactory.getLogger("api");
    private final XHttpClient xHttpClient;

    public WebCrawlingServiceImpl(XHttpClient xHttpClient) {
        this.xHttpClient = xHttpClient;
    }

    @Override
    public ResponseInfo crawlWeb(URI uri, boolean htmlIgnore, int packagingNumber) {
        // URL을 통한 통신
        Flux<DataBuffer> dataBufferFlux = dataBufferFlux = xHttpClient.get(uri);
        Mono<InputStream> inputStream = dataBufferFlux.map(dataBuffer -> dataBuffer.asInputStream(true))
                .reduce(SequenceInputStream::new);

        StringBuilder stringBuilder = new StringBuilder();
        inputStream.doOnNext(stream -> {
            BufferedReader reader = null;
            int BUFFER_SIZE = 8192;
            try {
                reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), BUFFER_SIZE);
            } catch (UnsupportedEncodingException e) {
                log.error(e.toString());
            }

            while (true) {
                try {
                    String str = reader.readLine();
                    if (str == null)
                        break;

                    stringBuilder.append(str);
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        }).block();

        // 응답 데이터 가공
        String webData = stringBuilder.toString();
        if (htmlIgnore) // HTML 태그를 찾아 삭제
            webData = webData.replaceAll("<([^>]+)>", "");

        // 알파벳, 숫자를 제외한 모든 것을 제거
        webData = webData.replaceAll("[^0-9a-zA-Z]", "");

        // 알파벳, 숫자 각각에 대해서 카운트
        int[] charCount = new int[123];
        webData.chars().forEach(c -> {
            charCount[c]++;
        });

        // 알파벳 대소문자 구분 정렬
        LinkedList<String> alphabetList = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            while (charCount[65 + i] > 0) {
                alphabetList.add(String.valueOf((char) (65 + i)));
                charCount[65 + i]--;
            }
            while (charCount[97 + i] > 0) {
                alphabetList.add(String.valueOf((char) (97 + i)));
                charCount[97 + i]--;
            }
        }

        // 숫자 정렬
        LinkedList<String> numberList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            while (charCount[48 + i] > 0) {
                numberList.add(String.valueOf((char) (48 + i)));
                charCount[48 + i]--;
            }
        }

        // 교차 출력
        String crossStr = "";
        int size = alphabetList.size() > numberList.size() ? alphabetList.size() : numberList.size();
        for (int i = 0; i < size; i++) {
            if (!alphabetList.isEmpty())
                crossStr += alphabetList.poll();
            if (!numberList.isEmpty())
                crossStr += numberList.poll();
        }

        // 나누기
        {
            if (crossStr.isEmpty())
                return ResponseInfo.builder().quotient("").remainder("").build();

            if (packagingNumber == 0)
                return ResponseInfo.builder().quotient("").remainder(crossStr).build();

            if (crossStr.length() < packagingNumber)
                return ResponseInfo.builder().quotient("").remainder(crossStr).build();

            if (crossStr.length() == packagingNumber)
                return ResponseInfo.builder().quotient(crossStr).remainder("").build();

            final String quotient = crossStr.substring(0, packagingNumber);
            final String remainder = crossStr.substring(packagingNumber);

            return ResponseInfo.builder().quotient(quotient).remainder(remainder).build();
        }
    }
}
