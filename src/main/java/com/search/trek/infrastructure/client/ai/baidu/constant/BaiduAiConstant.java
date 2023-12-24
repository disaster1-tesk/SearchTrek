package com.search.trek.infrastructure.client.ai.baidu.constant;

public interface BaiduAiConstant {
    String HOST = "https://aip.baidubce.com";
    String TOKEN = "/oauth/2.0/token";
    String ERNIE_BOT = "/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions";
    String ERNIE_BOT_TURBO = "/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant";
    String EMBEDDING_V1 = "/rpc/2.0/ai_custom/v1/wenxinworkshop/embeddings/embedding-v1";
    String CHAT_URL = "/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/{module}";
}
