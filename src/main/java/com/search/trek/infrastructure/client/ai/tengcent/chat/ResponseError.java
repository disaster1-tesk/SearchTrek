package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseError implements Serializable {
    /**
     * 错误消息
     */
    private String message;


    /**
     * String
     */
    private String code;
}
