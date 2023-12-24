package com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRes {
    private List<String> results;

    /**
     * 匹配的结果
     */
    private List<QueryDownResp> matches;

    /**
     * 命名空间
     */
    private String namespace;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class QueryDownResp {
        /**
         * 向量id
         */
        private String id;

        /**
         * 相似度分数
         */
        private Float score;

        /**
         * 向量
         */
        private List<Float> values;

        /**
         * 向量的元数据，存放对应文本
         */
        private Map<String, String> metadata;
    }
}
