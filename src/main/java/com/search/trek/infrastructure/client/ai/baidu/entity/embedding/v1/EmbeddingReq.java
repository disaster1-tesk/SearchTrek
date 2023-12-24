package com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1;

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
public class EmbeddingReq implements Serializable {
    private List<String> input;
    private String user_id;
}
