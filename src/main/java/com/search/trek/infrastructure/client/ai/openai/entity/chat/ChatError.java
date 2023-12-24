package com.search.trek.infrastructure.client.ai.openai.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 江永明
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatError implements Serializable {
    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误类型
     */
    private String type;

    /**
     * 参数
     */
    private Object param;

    /**
     * code
     */
    private Object code;
}
