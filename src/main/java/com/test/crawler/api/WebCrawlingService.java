package com.test.crawler.api;

import com.test.crawler.api.vo.ResponseInfo;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public interface WebCrawlingService {

    ResponseInfo crawlWeb(URI uri, boolean htmlIgnore, int packagingNumber) throws UnsupportedEncodingException;

}
