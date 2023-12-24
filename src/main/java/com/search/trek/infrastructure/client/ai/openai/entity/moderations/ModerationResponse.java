package com.search.trek.infrastructure.client.ai.openai.entity.moderations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ModerationResponse implements Serializable {
    private String id;
    private String model;
    private List<Result> results;
}
