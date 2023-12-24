package com.search.trek.infrastructure.client.vector.pipecone;

import cn.hutool.json.JSONUtil;
import com.search.trek.infrastructure.client.BaseTest;
import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApiClient;
import com.search.trek.infrastructure.client.ai.minimax.constant.MinimaxConstant;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import com.search.trek.infrastructure.client.vector.pinecone.PineConeApiClient;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query.QueryRes;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.update.UpdateReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.upset.UpSertReq;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class PineConeApiTest extends BaseTest {

    private PineConeApiClient pineConeApiClient;

    @Before
    public void before() {
        pineConeApiClient = PineConeApiClient.builder()
                .apiHost("https://hellobike-e39rlbk.svc.gcp-starter.pinecone.io")
                .init()
                .build();
        client = MiniMaxApiClient.builder()
                .init()
                .apiHost(MinimaxConstant.MINI_MAX_API)
                .build();
        miniMaxApi = client.getMiniMaxApi();
    }


    @Test
    public void update() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(
                        Lists.list("hello world")
                )
                .build());
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("content", "hello world");
        UpdateReq updateReq = UpdateReq.builder()
                .id("text")
                .values(embeddings.getVectors().get(0))
                .metadata(metaData)
                .build();
        Map update = pineConeApiClient.update(updateReq);
        System.out.println("update = " + update);
    }

    @Test
    public void query() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(
                        Lists.list("hello world")
                )
                .build());

        QueryReq queryReq = QueryReq.builder()
                .topK(20)
                .includeValues(true)
                .includeMetadata(true)
                .vector(embeddings.getVectors().get(0))
                .namespace("userIdsessionId_1")
                .build();
        QueryRes query = pineConeApiClient.query(queryReq);
        System.out.println("query = " + query);
    }

    @Test
    public void upset() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(
                        Lists.list("hello world")
                )
                .build());

        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("content","hello world");
        UpdateReq updateReq = UpdateReq.builder()
                .id("text")
                .values(embeddings.getVectors().get(0))
                .metadata(metaData)
                .build();
        UpSertReq upSertReq = UpSertReq.builder()
                .vectors(Lists.list(updateReq))
                .namespace("userIdsessionId_" + 1)
                .build();
        Map upset = pineConeApiClient.upsert(upSertReq);
        System.out.println("upset = " + upset);
    }


    @Test
    public void json() {
        int count = 1;
        List<UpdateReq> vectors = new ArrayList<>();
        List<List<Float>> values = generateFloatVectors(count);
        List<String> ids = generateIDs(count);
        List<Map<String, String>> contents = generateContent(count);

        for (int i = 0; i < count; i++) {
            UpdateReq upSetReq = UpdateReq.builder()
                    .id(ids.get(i))
                    .values(values.get(i))
                    .metadata(contents.get(i))
                    .build();
            System.out.println(JSONUtil.toJsonStr(upSetReq));
            vectors.add(upSetReq);
        }
        UpSertReq upSertReq = UpSertReq.builder().vectors(vectors).namespace("userIdsessionId_" + 1).build();
        System.out.println(JSONUtil.toJsonStr(upSertReq));
    }


    private List<List<Float>> generateFloatVectors(int count) {
        Random ran = new Random();
        List<List<Float>> vectors = new ArrayList<>();
        for (int n = 0; n < count; ++n) {
            List<Float> vector = new ArrayList<>();
            for (int i = 0; i < 1536; ++i) {
                vector.add(ran.nextFloat());
            }
            vectors.add(vector);
        }

        return vectors;
    }

    private List<String> generateIDs(int count) {
        List<String> ids = new ArrayList<>();
        for (long i = 0L; i < count; ++i) {
            ids.add("id_" + i);
        }
        return ids;
    }

    private List<Map<String, String>> generateContent(int count) {
        List<Map<String, String>> contents = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("content", "content: " + i);
            contents.add(map);
        }
        return contents;
    }
}
