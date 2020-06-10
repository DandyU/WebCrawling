package com.test.crawler.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crawler.api.vo.RequestInfo;
import com.test.crawler.api.vo.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api/html")
public class WebCrawlingController {

    private static final Logger log = LoggerFactory.getLogger("api");

    private final WebCrawlingService webCrawlingService;

    public WebCrawlingController(WebCrawlingService webCrawlingService) {
        this.webCrawlingService = webCrawlingService;
    }

    @PostMapping(value = "/crawling")
    public ResponseEntity crawlWeb(@RequestBody RequestInfo requestInfo) {
        final String targetUrl = requestInfo.getUrl();
        final boolean htmlIgnore = requestInfo.isHtmlIgnore();
        final int packagingNumber = requestInfo.getPackagingNumber();

        if (targetUrl == null || targetUrl.isEmpty() || !targetUrl.matches("^(http|https)://.*$"))
            return ResponseEntity.badRequest().build();

        final URI uri;
        try {
            uri = new URI(targetUrl);
        } catch (URISyntaxException e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().build();
        }

        ResponseInfo resInfo = webCrawlingService.crawlWeb(uri, htmlIgnore, packagingNumber);

        return ResponseEntity.ok().body(resInfo);
    }
}
