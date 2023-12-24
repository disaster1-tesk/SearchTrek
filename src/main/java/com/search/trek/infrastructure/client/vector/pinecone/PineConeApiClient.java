package com.search.trek.infrastructure.client.vector.pinecone;


import cn.hutool.core.util.StrUtil;
import com.search.trek.infrastructure.client.ai.AIException;
import com.search.trek.infrastructure.client.ai.CommonError;
import com.search.trek.infrastructure.client.ai.minimax.interceptor.ResponseValidateInterceptor;
import com.search.trek.infrastructure.client.vector.StandardVectorApiClient;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryRes;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.update.UpdateReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.upset.UpSertReq;
import com.search.trek.infrastructure.client.vector.pinecone.interceptor.HeaderInterceptor;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Map;
import java.util.Objects;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PineConeApiClient extends StandardVectorApiClient {
    @Getter
    private PineConeApi pineConeApi;


    private PineConeApiClient(PineConeApiClient.Builder builder) {
        if (StrUtil.isEmpty(builder.apiHost)) throw new AIException(CommonError.API_HOST_NOT_FOUND);
        apiHost = builder.apiHost;
        if (Objects.isNull(builder.okHttpClient)) {
            builder.okHttpClient = this.okHttpClient();
        }
        okHttpClient = builder.okHttpClient;
        this.pineConeApi = new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(PineConeApi.class);
    }


    /**
     * 构造器
     */
    public static PineConeApiClient.Builder builder() {
        return new PineConeApiClient.Builder();
    }

    public QueryRes query(QueryReq queryReq){
        return this.pineConeApi.query(queryReq).blockingGet();
    }

    public Map update(UpdateReq updateReq){
        return this.pineConeApi.update(updateReq).blockingGet();
    }

    public Map upsert(UpSertReq upSertReq){
        return this.pineConeApi.upsert(upSertReq).blockingGet();
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



        public PineConeApiClient.Builder init() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    log.info("OkHttp-------->:{}", message);
                }
            });
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

        public PineConeApiClient.Builder apiHost(String val) {
            apiHost = val;
            return this;
        }

        public PineConeApiClient.Builder okHttpClient(OkHttpClient val) {
            okHttpClient = val;
            return this;
        }

        public PineConeApiClient build() {
            return new PineConeApiClient(this);
        }

        public PineConeApi buildApi() {
            return new PineConeApiClient(this).getPineConeApi();
        }

    }
}
