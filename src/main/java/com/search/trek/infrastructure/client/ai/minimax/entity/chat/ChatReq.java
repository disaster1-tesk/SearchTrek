package com.search.trek.infrastructure.client.ai.minimax.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatReq {
    private long beam_width;
    private List<BotSetting> bot_setting;
    private KnowledgeBaseParam knowledge_base_param;
    private List<Message> messages;
    private String model;
    private List<String> plugins;
    private ReplyConstraints reply_constraints;
    private List<String> sample_messages;
    private boolean stream;
    private double temperature;
    private long tokens_to_generate;
    private double top_p;
    private boolean mask_sensitive_info;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BotSetting {
        private String bot_name;
        private String content;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KnowledgeBaseParam {
        private long knowledge_base_id;
        private int topK;
        private float thresh;
        private String pattern;
        private int num_prev_messages;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String sender_name;
        private String sender_type;
        private String text;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyConstraints {
        private String sender_name;
        private String sender_type;
    }

}
