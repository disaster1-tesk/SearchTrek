package com.search.trek.infrastructure.client.ai.openai.entity.embeddings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.openai.entity.common.Usage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author 
 *  2023-02-15
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingResponse implements Serializable {

    private String object;
    private List<Item> data;
    private String model;
    private Usage usage;
}
