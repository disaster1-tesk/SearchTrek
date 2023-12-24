package com.search.trek.infrastructure.client.ai.openai.entity.moderations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author 
 *  2023-02-15
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {
    private Categories categories;
    @JsonProperty("category_scores")
    private CategoryScores categoryScores;
    private boolean flagged;
}