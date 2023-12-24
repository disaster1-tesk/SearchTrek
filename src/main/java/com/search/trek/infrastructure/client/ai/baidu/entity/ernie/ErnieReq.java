package com.search.trek.infrastructure.client.ai.baidu.entity.ernie;

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
public class ErnieReq implements Serializable{
    private List<Message> messages;
    private float temperature;
    private float topP;
    private float penalty_score;
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
