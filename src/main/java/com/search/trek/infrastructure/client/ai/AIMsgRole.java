package com.search.trek.infrastructure.client.ai;

public enum AIMsgRole {
    //系统消息 openai支持
    system("system"),

    //用户消息
    user("user"),

    //ai消息
    assistant("assistant"),

    //函数回调消息
    function("function"),
    ;
    private String role;


    AIMsgRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
