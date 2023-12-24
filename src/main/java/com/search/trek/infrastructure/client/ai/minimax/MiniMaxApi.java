package com.search.trek.infrastructure.client.ai.minimax;

import cn.hutool.json.JSONObject;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.entity.knowledge.CreateKnowledgeReq;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.*;



public interface MiniMaxApi {

    @POST("v1/embeddings")
    Single<EmbeddingRes> embeddings(@Body EmbeddingReq embeddingReq);


    @POST("v1/text/chatcompletion_pro")
    Single<ChatRes> chat(@Body ChatReq chatReq);

    @POST("/v1/embedding/create_knowledge_base")
    Single<JSONObject> createKnowledge(@Body CreateKnowledgeReq createKnowledgeReq);

    @POST("/v1/embedding/delete_knowledge_base")
    Single<JSONObject> deleteKnowledge(@Body JSONObject deleteKnowledgeReq);

    @GET("/v1/embedding/query_knowledge_base")
    Single<JSONObject> queryKnowledge(@QueryMap JSONObject queryKnowledgeReq);

    @GET("/v1/embedding/list_knowledge_base")
    Single<JSONObject> listKnowledge(@QueryMap JSONObject listKnowledgeReq);

    @POST("/v1/embedding/add_document")
    Single<JSONObject> addDocument(@Body JSONObject addDocument);

    @POST("/v1/embedding/delete_document")
    Single<JSONObject> deleteDocument(@Body JSONObject deleteDocument);

    @POST("/v1/embedding/update_document")
    Single<JSONObject> updateDocument(@Body JSONObject deleteDocument);

    @GET("/v1/embedding/query_document")
    Single<JSONObject> queryDocument(@QueryMap JSONObject listKnowledgeReq);


}
