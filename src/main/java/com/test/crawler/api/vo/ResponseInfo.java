package com.test.crawler.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ResponseInfo {
    public String quotient;
    public String remainder;
}
