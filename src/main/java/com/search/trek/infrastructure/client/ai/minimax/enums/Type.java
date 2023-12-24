package com.search.trek.infrastructure.client.ai.minimax.enums;

import lombok.Getter;

@Getter
public enum Type {
    DB("db","用于构建向量库，生成向量存储到库中（作为被检索文本）"),
    QUERY("query","用于生成查询用的向量（作为检索文本时）");

    private String name;
    private String description;

    Type(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
