package com.search.trek.infrastructure.client.ai.openai;

import com.search.trek.infrastructure.client.ai.openai.entity.chat.ChatCompletion;
import com.search.trek.infrastructure.client.ai.openai.entity.chat.ChatCompletionResponse;
import com.search.trek.infrastructure.client.ai.openai.entity.chat.Message;
import com.search.trek.infrastructure.client.ai.openai.entity.embeddings.Embedding;
import com.search.trek.infrastructure.client.ai.openai.entity.embeddings.EmbeddingResponse;
import com.search.trek.infrastructure.client.ai.openai.interceptor.OpenAILogger;
import com.search.trek.infrastructure.client.ai.openai.interceptor.OpenAiResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ApiTest {

    OpenAiClient openAiClient;

    @Before
    public void before() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiResponseInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        openAiClient = OpenAiClient.builder()
                .okHttpClient(okHttpClient)
                .build();
    }

    @Test
    public void chat() {
        Message message = Message.builder().role(Message.Role.USER).content("你好").build();
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(Arrays.asList(message))
                .temperature(0.1)
                .build();
        ChatCompletionResponse response = openAiClient.chatCompletion( chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
//        System.out.println(res);
    }

    @Test
    public void chatCompletion() {
        Message message = Message.builder()
                .content("你好")
                .build();
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(Arrays.asList(message))
                .temperature(0.1)
                .build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
//        System.out.println("chatCompletionResponse = " + chatCompletionResponse);
    }

}
