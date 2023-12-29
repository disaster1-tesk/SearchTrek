package com.search.trek.infrastructure.client.vector.redissearch;

import com.search.trek.infrastructure.client.BaseTest;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import com.search.trek.infrastructure.utils.DataTypeUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.redisson.api.RMap;
import org.redisson.api.listener.MapPutListener;
import org.redisson.api.search.index.*;
import org.redisson.api.search.query.Document;
import org.redisson.api.search.query.QueryOptions;
import org.redisson.api.search.query.ReturnAttribute;
import org.redisson.api.search.query.SearchResult;

import java.util.*;

public class RedisSearchTest extends BaseTest {


    @Test
    public void createIndex() {
        rSearch.createIndex("disaster1", IndexOptions.defaults()
                        .on(IndexType.HASH)
                        .prefix("doc:"),
                FieldIndex.tag("tag"),
                FieldIndex.text("content"),
                FieldIndex.hnswVector("vector")
                        .type(VectorTypeParam.Type.FLOAT32)
                        .dim(2)
                        .distance(VectorDistParam.DistanceMetric.COSINE)
        );
    }


    @Test
    public void putDataTest() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(Lists.list("怎么处理线上问题?"))
                .build());

//        RJsonBucket<HashEntity> jsonBucket = redisson.getJsonBucket("doc:3", new JacksonCodec<>(HashEntity.class));
//        jsonBucket.set(HashEntity.builder().build());
        RMap<String, Object> map = redisson.getMap("doc:3");
        map.addListener((MapPutListener) data -> {
            System.out.println("data = " + data);
        });
        byte[] bytes = DataTypeUtil.floatArrayToByteArray(embeddings.getVectors().get(0));
        map.put("tag", "disaster");
        map.put("content", "怎么处理线上问题？");
        map.put("vector", bytes);
    }

    @Test
    public void getDataTest(){
        RMap<Object, Object> map = redisson.getMap("doc:3");
        Collection<Object> values = map.values();
        Iterator<Object> iterator = values.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        Set<Map.Entry<Object, Object>> entries = map.readAllEntrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(String.format("key = {}, value = {}", entry.getKey(), entry.getValue()));
        }
    }


    @Test
    public void searchTest() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(Lists.list("怎么处理线上问题?"))
                .build());
        HashMap<String, Object> param = new HashMap<>();
        param.put("vector", DataTypeUtil.floatArrayToByteArray(embeddings.getVectors().get(0)));
        SearchResult search = rSearch.search("disaster1", "*=>[KNN 10 @vector $vector AS score]", QueryOptions.defaults()
                .params(param)
                .returnAttributes(
                        new ReturnAttribute("vector"),
                        new ReturnAttribute("content"))
                .dialect(2)
                .sortBy("score")
                .limit(1, 10)
        );
        for (Document document : search.getDocuments()) {
            System.out.println("document = " + document);
        }
    }
}
