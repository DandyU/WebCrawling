package com.test.crawler.api;

import com.test.crawler.api.vo.ResponseInfo;

import java.net.URI;
import java.net.UnknownHostException;

public interface WebCrawlingService {

    ResponseInfo crawlWeb(URI uri, boolean htmlIgnore, int packagingNumber);

}
