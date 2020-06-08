package com.test.crawler.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RequestInfo {

    private String url;
    private boolean htmlIgnore;
    private int packagingNumber;

}
