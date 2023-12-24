package com.search.trek.infrastructure.client.ai.minimax.enums;

import lombok.Getter;

@Getter
public enum Model {
    EM_BO_01("embo-01","用户embeddings"),
    ABAB5_CHAT("abab5.5-chat","用户对话");

    private String name;
    private String description;

    Model(String name) {
        this.name = name;
    }

    Model(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
