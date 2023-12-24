package com.search.trek.infrastructure.client.ai.openai.entity.completions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.openai.entity.common.Choice;
import com.search.trek.infrastructure.client.ai.openai.entity.common.OpenAiResponse;
import com.search.trek.infrastructure.client.ai.openai.entity.common.Usage;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述： 答案类
 *
 * @author 
 *  2023-02-11
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletionResponse extends OpenAiResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
    private String warning;
}
