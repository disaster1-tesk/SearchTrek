package com.search.trek.infrastructure.client.ai.tengcent;

import com.search.trek.infrastructure.client.ai.AIMsgRole;
import com.search.trek.infrastructure.client.ai.tengcent.chat.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ApiTest {

    private TencentHYApiClient tencentHYApiClient;

    @Before
    public void before() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        tencentHYApiClient = TencentHYApiClient.builder()
                .build();

    }

    @Test
    public void chat() {
        Message message = Message.builder().role(AIMsgRole.user.getRole()).content("你好,1+1=?").build();

        int appid= 1256478749;
        String secretId = "";
        String  secretKey = "";
        int timestamp = (int) (System.currentTimeMillis() / 1000 + 10000);
        String  query_id= "test_query_id_" + UUID.randomUUID().toString();
        ChatAuth chatAuthHeaderForPy = ChatAuth.builder().appId(appid).secretId(secretId).secretKey(secretKey).build();
        ChatReq chatReq = ChatReq.builder().messages(Arrays.asList(message)).queryId(query_id).appId(1256478749).secretId(secretId).stream(0).timestamp(timestamp).expired(timestamp+24 * 60 * 60).build();
        ChatReqForPython chatReqForPython = ChatReqForPython.builder().chatAuth(chatAuthHeaderForPy).chatReq(chatReq).build();
        ChatRes res = tencentHYApiClient.chatForPy(chatReqForPython);


    }

}
