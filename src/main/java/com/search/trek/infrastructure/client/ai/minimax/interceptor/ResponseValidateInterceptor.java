package com.search.trek.infrastructure.client.ai.minimax.interceptor;

import com.search.trek.infrastructure.client.ai.AIException;
import com.search.trek.infrastructure.client.ai.CommonError;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class ResponseValidateInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Response response = chain.proceed(original);
        if (!response.isSuccessful()) {
            String errorMsg = response.body().string();
            log.error("请求异常：{}", errorMsg);
            throw new AIException(CommonError.ERROR);
        }
        return response;
    }
}
