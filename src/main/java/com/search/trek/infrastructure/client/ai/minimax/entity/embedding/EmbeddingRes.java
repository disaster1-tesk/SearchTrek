package com.search.trek.infrastructure.client.ai.minimax.entity.embedding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingRes {
    private BaseResp baseResp;
    private long totalTokens;
    private List<List<Float>> vectors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BaseResp{
        private long statusCode;
        private String statusMsg;
    }
}
