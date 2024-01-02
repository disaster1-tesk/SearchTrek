package com.search.trek.infrastructure.client.ai.minimax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.search.trek.infrastructure.client.ai.AIException;
import com.search.trek.infrastructure.client.ai.CommonError;
import com.search.trek.infrastructure.client.ai.StandardOpenApiClient;
import com.search.trek.infrastructure.client.ai.minimax.constant.MinimaxConstant;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.text_to_speech.TextToSpeechReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.text_to_speech.TextToSpeechRes;
import com.search.trek.infrastructure.client.ai.minimax.interceptor.HeaderInterceptor;
import com.search.trek.infrastructure.client.ai.minimax.interceptor.ResponseValidateInterceptor;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSourceListener;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MiniMaxApiClient extends StandardOpenApiClient {
    @Getter
    private MiniMaxApi miniMaxApi;


    private MiniMaxApiClient(MiniMaxApiClient.Builder builder) {
        if (StrUtil.isEmpty(builder.apiHost)) throw new AIException(CommonError.API_HOST_NOT_FOUND);
        apiHost = builder.apiHost;
        if (Objects.isNull(builder.okHttpClient)) {
            builder.okHttpClient = this.okHttpClient();
        }
        okHttpClient = builder.okHttpClient;
        this.miniMaxApi = new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(MiniMaxApi.class);
    }


    /**
     * 构造器
     */
    public static MiniMaxApiClient.Builder builder() {
        return new MiniMaxApiClient.Builder();
    }


    @SneakyThrows
    @Deprecated
    public void chatCompletion(ChatReq chatReq, EventSourceListener eventSourceListener) {
        if (Objects.isNull(eventSourceListener)) {
            log.error("参数异常：EventSourceListener不能为空");
            throw new AIException(CommonError.PARAM_ERROR);
        }
        try {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("api.minimax.chat")
                    .addPathSegment("v1")
                    .addPathSegment("text")
                    .addPathSegment("chatcompletion_pro")
                    .addQueryParameter("GroupId", MinimaxConstant.MINI_MAX_GROUP)
                    .build();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtil.toJsonStr(chatReq));
            Request request = new Request.Builder()
                    .url(httpUrl)
                    //okhttp的巨坑！！
                    .addHeader("Accept", "application/json; charset=utf-8")
//                    .addHeader("Accept", "text/event-stream;charset=UTF-8")
                    .header("Authorization", MinimaxConstant.MINI_MAX_AUTHORIZATION)
                    .post(requestBody)
                    .build();
            //创建事件
            RealEventSource eventSource = new RealEventSource(request, eventSourceListener);
            eventSource.connect(okHttpClient);//真正开始请求的一步
        } catch (Exception e) {
            log.error("请求参数解析异常：", e);
        }
    }

    public ChatRes chat(ChatReq chatReq) {
        return this.miniMaxApi.chat(chatReq).blockingGet();
    }

    public EmbeddingRes embeddings(EmbeddingReq embeddingReq) {
        return this.miniMaxApi.embeddings(embeddingReq).blockingGet();
    }

    public TextToSpeechRes t2SpeechPro(TextToSpeechReq textToSpeechReq){
       return this.miniMaxApi.t2aPro(textToSpeechReq).blockingGet();
    }

    @SneakyThrows
    public TextToSpeechRes textToSpeech(TextToSpeechReq textToSpeechReq) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtil.toJsonStr(textToSpeechReq));
        Request request = new Request.Builder()
                .url(this.apiHost + "/v1/text_to_speech")
                .header("GroupId", MinimaxConstant.MINI_MAX_GROUP)
                .post(requestBody)
                .build();
        Response response = this.okHttpClient.newCall(request).execute();
        ResponseBody body = response.body();
        MediaType mediaType = body.contentType();
        String type = mediaType.type();
        if (!type.equals("audio/mpeg")) return JSONUtil.toBean(body.string(),TextToSpeechRes.class);
        URL resource = this.getClass().getResource("/");
        String file1 = resource.getFile();
        File file = new File(file1 + "/tmp/text_to_speech" + UUID.randomUUID() + ".mp3");
        File content = FileUtil.writeFromStream(body.byteStream(), file);
        TextToSpeechRes textToSpeechRes = TextToSpeechRes.builder()
                .content(content)
                .build();
        file.deleteOnExit();
        return textToSpeechRes;
    }

    public static final class Builder {
        /**
         * api请求地址，结尾处有斜杠
         */
        private String apiHost;
        /**
         * 自定义OkhttpClient
         */
        private OkHttpClient okHttpClient;

        public Builder init() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> log.info("OkHttp-------->:{}", message));
            //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
            //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            this.okHttpClient = new OkHttpClient
                    .Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new ResponseValidateInterceptor())
                    .build();

            return this;
        }

        public Builder() {
        }

        public MiniMaxApiClient.Builder apiHost(String val) {
            apiHost = val;
            return this;
        }

        public MiniMaxApiClient.Builder okHttpClient(OkHttpClient val) {
            okHttpClient = val;
            return this;
        }

        public MiniMaxApiClient build() {
            return new MiniMaxApiClient(this);
        }

        public MiniMaxApi buildApi() {
            return new MiniMaxApiClient(this).getMiniMaxApi();
        }

    }
}
