package com.search.trek.infrastructure.client.ai.baidu;


import com.search.trek.infrastructure.client.ai.baidu.constant.BaiduAiConstant;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.token.TokenReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.token.TokenRes;
import com.search.trek.infrastructure.client.ai.openai.interceptor.OpenAILogger;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApiTest {

    OkHttpClient okHttpClient;
    BaiduOpenApiClient baiduOpenApiClient;
    BaiduOpenApi baiduOpenApi;
    String access_token;

    @Before
    public void setup() throws Exception {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        baiduOpenApiClient = BaiduOpenApiClient.builder()
                .okHttpClient(okHttpClient)
                .apiHost(BaiduAiConstant.HOST)
                .build();
        baiduOpenApi = BaiduOpenApiClient.builder()
                .okHttpClient(okHttpClient)
                .apiHost(BaiduAiConstant.HOST)
                .buildApi();
        token();
    }

    @Test
    public void token() {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", "1DGkrPe8GiqO7clikbMEsCkd");
        params.put("client_secret", "R4XeFlFZtgGSpUGLX5NHajw7lXWOCCDU");
        TokenRes token = baiduOpenApiClient.token(params);
        access_token = token.getAccess_token();
//        System.out.println("token = " + token);
    }

    @Test
    public void token1() {
        TokenReq tokenReq = TokenReq.builder()
                .client_secret("1DGkrPe8GiqO7clikbMEsCkd")
                .client_id("R4XeFlFZtgGSpUGLX5NHajw7lXWOCCDU")
                .build();
        TokenRes token = baiduOpenApiClient.token(tokenReq);

//        System.out.println("token = " + token);
    }

    @Test
    public void chat() {
        ChatReq.Message message = ChatReq.Message.builder()
                .role("user")
                .content("介绍一下你自己")
                .build();
        ArrayList<ChatReq.Message> messages = new ArrayList<>();
        messages.add(message);
        Single<ChatRes> completions = baiduOpenApi.completions(access_token, ChatReq.builder()
                .messages(messages)
                .temperature(0.5f)
                .build());
        ChatRes ernieRes = completions.blockingGet();
//        System.out.println("ernieRes = " + ernieRes);
    }

    @Test
    public void ebInstant() {
        ChatReq.Message message = ChatReq.Message.builder()
                .role("user")
                .content("介绍一下你自己")
                .build();
        List<ChatReq.Message> messages = new ArrayList<>();
        messages.add(message);
        Single<ChatRes> completions = baiduOpenApi.ebInstant(access_token, ChatReq.builder()
                .messages(messages)
                .temperature(0.5f)
                .penalty_score(1)
                .build());
        ChatRes ebInstantRes = completions.blockingGet();
//        System.out.println("ebInstantRes = " + ebInstantRes);
    }

    @Test
    public void embeddingV1() {
        ArrayList<String> input = new ArrayList<>();
        input.add("推荐一些美食");
        input.add("给我讲个故事");
        EmbeddingRes ebInstantRes = baiduOpenApi.embeddingV1(access_token, EmbeddingReq.builder()
                .input(input)
                .build()).blockingGet();
        System.out.println("ebInstantRes = " + ebInstantRes);
    }

    @Test
    public void chatFinal(){
        ChatReq.Message message = ChatReq.Message.builder()
                .role("user")
                .content("介绍一下你自己")
                .build();
        ArrayList<ChatReq.Message> messages = new ArrayList<>();
        messages.add(message);

    }



}
