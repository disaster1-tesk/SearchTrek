package com.search.trek.infrastructure.client.ai.tengcent;


import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatReqForPython;
import com.search.trek.infrastructure.client.ai.tengcent.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.tengcent.constant.TencentAiConstant;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface TencentHYApi {

    @Headers({"Content-Type:application/json"})
    @POST(TencentAiConstant.CHAT_URL)
    Single<ChatRes> chat(@Body ChatReq chatReq);

    @Headers({"Content-Type:application/json"})
    @POST(TencentAiConstant.PYTHON_CHAT_URL)
    Single<ChatRes> chatPython( @Body ChatReqForPython chatReqForPython);
}
