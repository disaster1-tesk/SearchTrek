package com.search.trek.infrastructure.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.search.trek.infrastructure.client.ai.baidu.BaiduOpenApiClient;
import com.search.trek.infrastructure.client.ai.baidu.constant.BaiduAiConstant;
import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApiClient;
import com.search.trek.infrastructure.client.ai.minimax.constant.MinimaxConstant;
import com.search.trek.infrastructure.client.ai.openai.interceptor.OpenAILogger;
import com.search.trek.infrastructure.client.vector.pinecone.PineConeApiClient;
import com.search.trek.infrastructure.client.vector.pinecone.constant.PineConeConstant;
import com.search.trek.infrastructure.utils.ClientUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class SearchTrekConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson() {
        return builder -> builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(ClientUtil.HOST)
                .setPassword(ClientUtil.PASSWORD)
                .setDatabase(0);
        return Redisson.create(config);
    }

    @Bean
    public MiniMaxApiClient miniMaxApiClient() {
        return MiniMaxApiClient.builder()
                .apiHost(MinimaxConstant.MINI_MAX_API)
                .init()
                .build();
    }


    @Bean
    public PineConeApiClient pineConeApiClient() {
        return PineConeApiClient.builder()
                .apiHost(PineConeConstant.HOST)
                .init()
                .build();
    }

    @Bean
    public BaiduOpenApiClient baiduOpenApiClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return BaiduOpenApiClient.builder()
                .okHttpClient(
                        new OkHttpClient.Builder()
                                .addInterceptor(httpLoggingInterceptor)
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(120, TimeUnit.SECONDS)
                                .readTimeout(120, TimeUnit.SECONDS)
                                .build()
                )
                .apiHost(BaiduAiConstant.HOST)
                .build();
    }
}
