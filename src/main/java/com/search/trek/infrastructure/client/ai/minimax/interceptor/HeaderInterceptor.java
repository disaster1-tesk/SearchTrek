package com.search.trek.infrastructure.client.ai.minimax.interceptor;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.search.trek.infrastructure.client.ai.minimax.constant.MinimaxConstant;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl httpUrl = original
                .url()
                .newBuilder()
                .addQueryParameter("GroupId", MinimaxConstant.MINI_MAX_GROUP)
                .build();
        Request request = original.newBuilder()
                .header(Header.AUTHORIZATION.getValue(), MinimaxConstant.MINI_MAX_AUTHORIZATION)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .url(httpUrl)
                .method(original.method(), original.body()).build();
        return chain.proceed(request);
    }
}
