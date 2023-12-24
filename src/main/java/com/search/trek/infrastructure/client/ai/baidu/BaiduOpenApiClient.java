package com.search.trek.infrastructure.client.ai.baidu;

import cn.hutool.core.util.StrUtil;
import com.search.trek.infrastructure.client.ai.StandardOpenApiClient;
import com.search.trek.infrastructure.client.ai.baidu.config.Model;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.token.TokenReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.token.TokenRes;
import com.search.trek.infrastructure.client.ai.AIException;
import com.search.trek.infrastructure.client.ai.CommonError;
import io.reactivex.rxjava3.core.Single;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class BaiduOpenApiClient extends StandardOpenApiClient {
    /**
     * baiduOpenApi
     */
    @Getter
    private BaiduOpenApi baiduOpenApi;


    /**
     * 构造器
     *
     * @return OpenAiClient.Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 获取token接口
     *
     * @param params
     * @return
     */
    public TokenRes token(Map<String, String> params) {
        Single<TokenRes> token = this.baiduOpenApi.token(params);
        return token.blockingGet();
    }

    /**
     * 获取token接口
     *
     * @param tokenReq
     * @return
     */
    public TokenRes token(TokenReq tokenReq) {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", tokenReq.getClient_id());
        params.put("client_secret", tokenReq.getClient_secret());
        Single<TokenRes> token = this.baiduOpenApi.token(params);
        return token.blockingGet();
    }

    /**
     * 聊天接口
     *
     * @param model
     * @param accessToken
     * @param chatReq
     * @return
     */
    public ChatRes chat(Model model, String accessToken, ChatReq chatReq) {
        return this.baiduOpenApi.chat(model.name(), accessToken, chatReq).blockingGet();
    }

    /**
     * 矢量构建接口
     * @param accessToken
     * @param embeddingReq
     * @return
     */
    public EmbeddingRes embeddings(String accessToken, EmbeddingReq embeddingReq) {
        return this.baiduOpenApi.embeddingV1(accessToken, embeddingReq).blockingGet();
    }


    private BaiduOpenApiClient(Builder builder) {
        if (StrUtil.isEmpty(builder.apiHost)) throw new AIException(CommonError.API_HOST_NOT_FOUND);
        apiHost = builder.apiHost;
        if (Objects.isNull(builder.okHttpClient)) {
            builder.okHttpClient = this.okHttpClient();
        }
        okHttpClient = builder.okHttpClient;
        this.baiduOpenApi = new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(BaiduOpenApi.class);
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


        public Builder() {
        }

        public Builder apiHost(String val) {
            apiHost = val;
            return this;
        }

        public Builder okHttpClient(OkHttpClient val) {
            okHttpClient = val;
            return this;
        }

        public BaiduOpenApiClient build() {
            return new BaiduOpenApiClient(this);
        }

        public BaiduOpenApi buildApi() {
            return new BaiduOpenApiClient(this).getBaiduOpenApi();
        }

    }
}
