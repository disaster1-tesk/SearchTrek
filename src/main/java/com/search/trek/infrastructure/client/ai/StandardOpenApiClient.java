package com.search.trek.infrastructure.client.ai;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public abstract class StandardOpenApiClient {
    /**
     * 自定义api host使用builder的方式构造client
     */
    @Getter
    protected String apiHost;
    /**
     * 自定义的okHttpClient
     * 如果不自定义 ，就是用sdk默认的OkHttpClient实例
     */
    @Getter
    protected OkHttpClient okHttpClient;

    /**
     * 创建默认OkHttpClient
     *
     * @return
     */
    protected OkHttpClient okHttpClient() {
        return new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).build();
    }
}
