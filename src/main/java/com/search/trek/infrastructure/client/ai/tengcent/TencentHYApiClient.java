package com.search.trek.infrastructure.client.ai.tengcent;

import cn.hutool.core.util.StrUtil;
import com.search.trek.infrastructure.client.ai.StandardOpenApiClient;
import com.search.trek.infrastructure.client.ai.AIException;
import com.search.trek.infrastructure.client.ai.CommonError;
import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatReqForPython;
import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatRes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Objects;


@Slf4j
public class TencentHYApiClient extends StandardOpenApiClient {

    @Getter
    private TencentHYApi tencentHYApi;


    /**
     * 构造器
     */
    public static Builder builder() {
        return new Builder();
    }



    /**
     * 聊天接口
     * @param chatReq
     * @return
     */
    public ChatRes chat(ChatReq chatReq){
        return this.tencentHYApi.chat(chatReq).blockingGet();
    }

    /**
     * python聊天接口
     * @return
     */
    public ChatRes chatForPy(ChatReqForPython chatReqForPython){
        return this.tencentHYApi.chatPython(chatReqForPython).blockingGet();
    }



    private TencentHYApiClient(Builder builder) {
        if (StrUtil.isEmpty(builder.apiHost)) throw new AIException(CommonError.API_HOST_NOT_FOUND);
        apiHost = builder.apiHost;
        if (Objects.isNull(builder.okHttpClient)) {
            builder.okHttpClient = this.okHttpClient();
        }
        okHttpClient = builder.okHttpClient;
        this.tencentHYApi = new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(TencentHYApi.class);
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

        public TencentHYApiClient build() {
            return new TencentHYApiClient(this);
        }

        public TencentHYApi buildApi() {
            return new TencentHYApiClient(this).getTencentHYApi();
        }

    }
}
