package com.search.trek.infrastructure.client.ai.baidu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.BaseRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse extends BaseRes {
    private String error_code;
    private String error_msg;
}
