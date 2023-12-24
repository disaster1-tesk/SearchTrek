package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ResponseUsage implements Serializable {
    /**
     * 输入 token 数量
     */
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    /**
     * 总 token 数量
     */
    @JsonProperty("total_tokens")
    private Integer totalTokens;

    /**
     * 输出 token 数量
     */
    @JsonProperty("completion_tokens")
    private Integer completionTokens;
}
