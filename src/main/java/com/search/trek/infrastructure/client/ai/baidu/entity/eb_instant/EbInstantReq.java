package com.search.trek.infrastructure.client.ai.baidu.entity.eb_instant;

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
public class EbInstantReq implements Serializable {
    private List<Message> messages;
    private boolean stream;
    private float temperature;
    private float top_p;
    private float penalty_score;
    private String user_id;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message implements Serializable{
        private String role;
        private String content;
    }
}
