package com.search.trek.infrastructure.client.ai.minimax.entity.embedding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmbeddingReq {
    private String model;
    private List<String> texts;
    private String type;
}
