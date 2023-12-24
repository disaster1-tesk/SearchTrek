package com.search.trek.infrastructure.client.vector.pinecone;

import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryRes;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.update.UpdateReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.upset.UpSertReq;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Map;

public interface PineConeApi {

    @POST("/vectors/update")
     Single<Map> update(@Body UpdateReq updateReq);

    @POST("/vectors/upsert")
     Single<Map> upsert(@Body UpSertReq upSertReq);

    @POST("/query")
    Single<QueryRes> query(@Body QueryReq queryReq);

}
