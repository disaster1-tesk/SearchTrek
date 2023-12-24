package com.search.trek.application;

import com.search.trek.domain.Result;
import com.search.trek.domain.Results;
import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApiClient;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import com.search.trek.infrastructure.client.vector.pinecone.PineConeApiClient;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryRes;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatServiceApplication {
    private final MiniMaxApiClient miniMaxApiClient;

    private final PineConeApiClient pineConeApiClient;

    @Autowired
    public ChatServiceApplication(MiniMaxApiClient miniMaxApiClient, PineConeApiClient pineConeApiClient) {
        this.miniMaxApiClient = miniMaxApiClient;
        this.pineConeApiClient = pineConeApiClient;
    }


    public Result chat(String prompt) {
        EmbeddingRes embeddings = miniMaxApiClient.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(Lists.newArrayList(prompt))
                .build()
        );
        List<List<Float>> vectors = embeddings.getVectors();
        QueryRes query = pineConeApiClient.query(QueryReq.builder()
                .namespace("hello_bike")
                .topK(10)
                .includeMetadata(true)
                .includeValues(false)
                .vector(vectors.get(0))
                .build());
        List<QueryRes.QueryDownResp> matches = query.getMatches();
        matches = matches.stream().sorted((o1, o2) -> (int) (o2.getScore() - o1.getScore())).collect(Collectors.toList());
        String content = CollectionUtils.isEmpty(matches) ? "" : matches.get(0).getMetadata().get("content");
        ChatReq chatReq = ChatReq.builder()
                .messages(Lists.list(ChatReq.Message.builder()
                        .sender_name("用户")
                        .sender_type("USER")
                        .text(String.format("我将给你一些内容，现在需要你通过我给出的内容回答我的问题，内容是：%s,问题是:%s", content, prompt))
                        .build()))
                .reply_constraints(ChatReq.ReplyConstraints.builder()
                        .sender_name("hello_bike数科智能助手")
                        .sender_type("BOT")
                        .build())
                .plugins(null)
                .beam_width(1)
                .bot_setting(Lists.list(ChatReq.BotSetting.builder()
                        .bot_name("hello_bike数科智能助手")
                        .content("hello_bike数科智能助手是一款基于矢量检索+知识库+llm实现的问答智能机器人")
                        .build()))
                .stream(false)
                .temperature(0.9)
                .top_p(0.95)
                .model(Model.ABAB5_CHAT.getName())
                .tokens_to_generate(1024)
                .mask_sensitive_info(false)
                .build();
        ChatRes chat = miniMaxApiClient.chat(chatReq);
        return Results.ok(new String(chat.getReply().getBytes(StandardCharsets.UTF_8)));
    }

}
