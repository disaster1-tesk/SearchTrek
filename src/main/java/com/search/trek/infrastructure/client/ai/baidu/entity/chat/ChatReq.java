package com.search.trek.infrastructure.client.ai.baidu.entity.chat;

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
public class ChatReq implements Serializable{
    private List<Message> messages;
    @Builder.Default
    private float temperature = 0.95f;
    @Builder.Default
    private float topP = 0.8f ;

    @Builder.Default
    private float penalty_score = 1.0f;
    private boolean stream;
    private String userId;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message implements Serializable {
        private String role;
        private String content;
    }
}
