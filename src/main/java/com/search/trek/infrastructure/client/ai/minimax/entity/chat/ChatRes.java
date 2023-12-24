package com.search.trek.infrastructure.client.ai.minimax.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRes {
    private EmbeddingRes.BaseResp base_resp;
    private List<Choice> choices;
    private long created;
    private String id;
    private boolean input_sensitive;
    private KnowledgeBase knowledge_base;
    private String model;
    private boolean output_sensitive;
    private String reply;
    private Usage usage;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private String finish_reason;
        private List<Message> messages;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String sender_name;
        private String sender_type;
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KnowledgeBase {
        private List<Chunk> chunks;
        private long knowledge_base_id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chunk {
        private String content;
        private String document;
        private Long index;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {
        private long total_tokens;
    }
}
