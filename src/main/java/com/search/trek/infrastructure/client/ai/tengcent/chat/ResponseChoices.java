package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseChoices implements Serializable {

    /**
     * 内容，流模式返回内容，同步模式为 null
     * 输出content内容总数最多支持1024token。
     */
    @JsonProperty("delta")
    private String delta;
    /**
     * 内容，同步模式返回内容，流模式为 null
     * 输出content内容总数最多支持1024token。
     */
    @JsonProperty("messages")
    private Message messages;

    /**
     * 流式结束标志位，为 stop 则表示尾包
     */
    @JsonProperty("finish_reason")
    private String finishReason;
}
