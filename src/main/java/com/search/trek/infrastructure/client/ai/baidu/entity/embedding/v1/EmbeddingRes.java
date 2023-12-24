package com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1;


import com.search.trek.infrastructure.client.ai.baidu.entity.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingRes extends BaseResponse implements Serializable {
    private String id;
    private String object;
    private int created;
    private List<EmbeddingData> data;
    private Usage usage;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmbeddingData implements Serializable {
        private String object;
        private List<Float> embedding;
        private int index;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage implements Serializable {
        private int prompt_tokens;
        private int total_tokens;
    }
}
