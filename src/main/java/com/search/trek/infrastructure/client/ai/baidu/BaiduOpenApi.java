package com.search.trek.infrastructure.client.ai.baidu;

import com.search.trek.infrastructure.client.ai.baidu.constant.BaiduAiConstant;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.baidu.entity.token.TokenRes;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;

import java.util.Map;

public interface BaiduOpenApi {

    @Headers({"Content-Type:application/json"})
    @POST(BaiduAiConstant.ERNIE_BOT)
    Single<ChatRes> completions(@Query("access_token") String access_token, @Body ChatReq chatReq);

    @POST(BaiduAiConstant.TOKEN)
    @Headers({"Content-Type:application/json"})
    Single<TokenRes> token(@QueryMap Map<String,String> query);

    @POST(BaiduAiConstant.ERNIE_BOT_TURBO)
    @Headers({"Content-Type:application/json"})
    Single<ChatRes> ebInstant(@Query("access_token") String access_token, @Body ChatReq chatReq);

    @POST(BaiduAiConstant.EMBEDDING_V1)
    @Headers({"Content-Type:application/json"})
    Single<EmbeddingRes> embeddingV1(@Query("access_token") String access_token, @Body EmbeddingReq embeddingReq);

    @POST(BaiduAiConstant.CHAT_URL)
    @Headers({"Content-Type:application/json"})
    Single<ChatRes> chat(@Path("module") String module, @Query("access_token") String access_token, @Body ChatReq chatReq);




}
