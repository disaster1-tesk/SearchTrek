package com.search.trek.infrastructure.client.ai.openai.entity.edits;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.openai.entity.common.Choice;
import com.search.trek.infrastructure.client.ai.openai.entity.common.Usage;
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
public class EditResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
}
