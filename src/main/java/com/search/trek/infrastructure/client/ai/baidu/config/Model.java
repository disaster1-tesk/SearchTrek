package com.search.trek.infrastructure.client.ai.baidu.config;

public enum Model {
    ERNIE_Bot_turbo("eb-instant"),
    Embedding_V1("embedding-v1"),
    BLOOMZ_7B("bloomz_7b1"),
    Qianfan_BLOOMZ_7B_compressed("qianfan_bloomz_7b_compressed"),
    Qianfan_Chinese_Llama_2_7B("qianfan_chinese_llama_2_7b"),
    ;

    Model(String name) {
        this.name = name;
    }

    private String name;
}
