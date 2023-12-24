package com.search.trek.application;

import com.search.trek.infrastructure.client.BaseTest;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RJsonBucket;
import org.redisson.api.RSearch;
import org.redisson.api.RedissonClient;
import org.redisson.api.search.index.FieldIndex;
import org.redisson.api.search.index.IndexOptions;
import org.redisson.api.search.index.IndexType;
import org.redisson.api.search.index.VectorDistParam;
import org.redisson.api.search.query.Document;
import org.redisson.api.search.query.QueryOptions;
import org.redisson.api.search.query.ReturnAttribute;
import org.redisson.api.search.query.SearchResult;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.redisson.api.search.index.VectorTypeParam.Type.FLOAT32;


public class SearchApplicationTest extends BaseTest {
    @Autowired
    RedissonClient redisson;
    RSearch rSearch;

    @Before
    public void setUp() {
        rSearch = redisson.getSearch();
    }

    @Test
    public void search() {
        EmbeddingRes embeddings = client.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(Lists.list("怎么处理线上问题?"))
                .build());

        //FT.CREATE index_name ON HASH PREFIX 1 doc: SCHEMA title TEXT SORTABLE embedding SCALAR
        RJsonBucket<SearchContext> b = redisson.getJsonBucket("test:1", new JacksonCodec<>(SearchContext.class));

        SearchContext searchContext = b.get();

        System.out.println("searchContext = " + searchContext);
        b.set(SearchContext.builder()
                .title("如何处理线上问题？")
                .context("询问老员工")
                .vectors(embeddings.getVectors().get(0))
                .build());

        HashMap<String, Object> params = new HashMap<>();
        params.put("value", b.get().getVectors());
        SearchResult r = rSearch.search("idx",
                String.format("(*)=>[KNN %d @vectors $value]", 3),
                QueryOptions.defaults()
                        .params(params)
                        .returnAttributes(
                                new ReturnAttribute("vectors"),
                                new ReturnAttribute("context"),
                                new ReturnAttribute("title"))
                        .dialect(2)
                        .sortBy("vectors")
                        .limit(1, 10)
        );
        SearchResult search1 = rSearch.search("idx",
                "*",
                QueryOptions.defaults()
                        .returnAttributes(
                                new ReturnAttribute("vectors"),
                                new ReturnAttribute("context"),
                                new ReturnAttribute("title"))
                        .dialect(2)
                        .sortBy("vectors")
                        .limit(1, 10)
        );
        List<Document> documents = r.getDocuments();
        List<Document> documents1 = search1.getDocuments();
        System.out.println("documents = " + documents);
        System.out.println("documents1 = " + documents1);
    }

    @Test
    public void dropIndex() {
        RSearch search = redisson.getSearch(StringCodec.INSTANCE);
        search.dropIndex("idx");
    }

    @Test
    public void createIndex() {
        rSearch.createIndex("idx", IndexOptions.defaults()
                        .on(IndexType.JSON)
                        .prefix(Arrays.asList("test:")),
                FieldIndex.flatVector("$..vectors")
                        .as("vectors")
                        .type(FLOAT32)
                        .dim(768)
                        .distance(VectorDistParam.DistanceMetric.COSINE),
                FieldIndex.text("$..context").as("context"),
                FieldIndex.text("$..title").as("title")
        );
    }

    @Test
    public void jsonBucket() {
        RJsonBucket<TestClass> b = redisson.getJsonBucket("doc:1", new JacksonCodec<>(TestClass.class));

        b.set(new TestClass(Arrays.asList(1, 2, 3), "hello"));

//        s.createIndex("idx", IndexOptions.defaults()
//                        .on(IndexType.JSON)
//                        .prefix(Arrays.asList("doc:")),
//                FieldIndex.numeric("$..arr").as("arr"),
//                FieldIndex.text("$..value").as("val"));

        SearchResult r = rSearch.search("idx", "*", QueryOptions.defaults()
                .returnAttributes(new ReturnAttribute("arr"), new ReturnAttribute("val")));
        System.out.println("r = " + r.getDocuments().get(0).getAttributes());
    }

}
