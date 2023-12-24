package com.search.trek.infrastructure.client.ai.minimax.entity.knowledge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateKnowledgeReq {
    private DocParams doc_params;
    private String embedding_model;
    private String name;
    private long operator_id;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocParams{
        private long chunk_overlap;
        private long chunk_size;
        private boolean is_regex;
        private List<String> separators;
    }
}
