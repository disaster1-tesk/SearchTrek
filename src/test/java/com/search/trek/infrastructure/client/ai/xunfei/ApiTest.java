package com.search.trek.infrastructure.client.ai.xunfei;

import com.search.trek.infrastructure.client.ai.HttpLogger;
import com.search.trek.infrastructure.client.ai.baidu.constant.BaiduAiConstant;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ApiTest {
    OkHttpClient okHttpClient;
    WeaverXunFeiOpenAiClient weaverXunFeiOpenAiClient;

    @Before
    public void before() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new OpenAiResponseInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        weaverXunFeiOpenAiClient = WeaverXunFeiOpenAiClient.builder()
                .okHttpClient(okHttpClient)
                .apiHost(BaiduAiConstant.HOST)
                .build();
    }

    @Test
    public void chat(){
        WebSocket chat = weaverXunFeiOpenAiClient.chat("dd3dab700d16f268a548376b69137cf8", "MzZmMjk0OTMxNWVhM2U0NjZhYTA1YzRl", new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                log.info(text);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t,  Response response) {
                super.onFailure(webSocket, t, response);
            }
        });
//        LockSupport.park();
    }

}
