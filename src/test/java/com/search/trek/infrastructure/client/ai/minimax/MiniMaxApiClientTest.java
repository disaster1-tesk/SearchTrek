package com.search.trek.infrastructure.client.ai.minimax;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.trek.infrastructure.client.BaseTest;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.knowledge.CreateKnowledgeReq;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import com.search.trek.infrastructure.client.ai.minimax.listener.MiniMaxChatResListener;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;


public class MiniMaxApiClientTest extends BaseTest {

    @Test
    public void embeddingsTest() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(
                        Lists.list("helloworld")
                )
                .build());
        System.out.println(JSONUtil.toJsonStr(embeddings));
    }

    @SneakyThrows
    @Test
    public void chat() {
        ChatReq chatReq = ChatReq.builder()
                .messages(Lists.list(ChatReq.Message.builder()
                        .sender_name("用户")
                        .sender_type("USER")
                        .text("线上问题怎么处理?")
                        .build()))
                .reply_constraints(ChatReq.ReplyConstraints.builder()
                        .sender_name("MM智能助理")
                        .sender_type("BOT")
                        .build())
                .knowledge_base_param(ChatReq.KnowledgeBaseParam.builder()
                        .knowledge_base_id(867245226706927616l)
                        .build())
                .plugins(null)
                .beam_width(1)
                .bot_setting(Lists.list(ChatReq.BotSetting.builder()
                        .bot_name("MM智能助理")
                        .content("MM智能助理是一款由MiniMax自研的，没有调用其他产品的接口的大型语言模型。MiniMax是一家中国科技公司，一直致力于进行大模型相关的研究。")
                        .build()))
                .stream(false)
                .temperature(0.9)
                .top_p(0.95)
                .model("abab5.5-chat")
                .tokens_to_generate(1024)
                .mask_sensitive_info(false)
                .build();
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(chatReq));
        ChatRes chat = client.chat(chatReq);
        System.out.println("chat = " + chat);
    }

    @Test
    @Deprecated
    public void chatCompletionTest() {
        String json = "{\n" +
                "    \"model\": \"abab5.5-chat\",\n" +
                "    \"tokens_to_generate\": 1024,\n" +
                "    \"temperature\": 0.9,\n" +
                "    \"top_p\": 0.95,\n" +
                "    \"stream\": false,\n" +
                "    \"beam_width\": 1,\n" +
                "    \"reply_constraints\": {\n" +
                "        \"sender_type\": \"BOT\",\n" +
                "        \"sender_name\": \"MM智能助理\"\n" +
                "    },\n" +
                "    \"sample_messages\": [],\n" +
                "    \"plugins\": [],\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"sender_type\": \"USER\",\n" +
                "            \"sender_name\": \"用户\",\n" +
                "            \"text\": \"线上问题怎么处理？\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"bot_setting\": [\n" +
                "        {\n" +
                "            \"bot_name\": \"MM智能助理\",\n" +
                "            \"content\": \"MM智能助理是一款由MiniMax自研的，没有调用其他产品的接口的大型语言模型。MiniMax是一家中国科技公司，一直致力于进行大模型相关的研究。\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"knowledge_base_param\": {\n" +
                "        \"knowledge_base_id\": 867245226706927616\n" +
                "    }\n" +
                "}";
        client.chatCompletion(JSONUtil.toBean(json, ChatReq.class), new MiniMaxChatResListener());
        LockSupport.park();
    }

    @Test
    public void createKnowledge() {
        String json = "{\n" +
                "    \"operator_id\": 10,\n" +
                "    \"name\": \"dongtian10\",\n" +
                "    \"embedding_model\": \"kbq-001\",\n" +
                "    \"doc_params\": {\n" +
                "        \"chunk_size\": 500,\n" +
                "        \"chunk_overlap\": 50,\n" +
                "        \"separators\": [\n" +
                "            \"/n\"\n" +
                "        ],\n" +
                "        \"is_regex\": false\n" +
                "    }\n" +
                "}";
        JSONObject entries = miniMaxApi.createKnowledge(JSONUtil.toBean(json, CreateKnowledgeReq.class)).blockingGet();
        System.out.println("knowledge = " + entries);
    }

    @Test
    public void deleteKnowledge() {
        String json = "{\n" +
                "    \"operator_id\": 2622333,\n" +
                "    \"knowledge_base_id\": 867245226706927616 \n" +
                "}";
        JSONObject entries = JSONUtil.parseObj(json);
        JSONObject jsonObjectSingle = miniMaxApi.deleteKnowledge(entries).blockingGet();
        System.out.println("jsonObjectSingle = " + jsonObjectSingle);
    }

    @Test
    public void queryKnowledge() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("knowledge_base_id", "");
        JSONObject entries = miniMaxApi.queryKnowledge(jsonObject).blockingGet();
        System.out.println("entries = " + entries);
    }
}
