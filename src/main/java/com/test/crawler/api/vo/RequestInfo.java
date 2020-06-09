package com.test.crawler.api.vo;

import lombok.Data;

@Data
public class RequestInfo {
    private String url;
    private boolean htmlIgnore;
    private int packagingNumber;
}
