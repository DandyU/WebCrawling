package com.test.crawler.api;

import com.test.crawler.api.vo.RequestInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/html")
public class WebCrawlingController {

    @PostMapping(value = "/crawling")
    public void crawlWeb(@RequestBody RequestInfo requestInfo) {
        ;
    }

}
