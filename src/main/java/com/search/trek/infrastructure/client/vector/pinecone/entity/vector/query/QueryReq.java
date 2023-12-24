package com.search.trek.infrastructure.client.vector.pinecone.entity.vector.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryReq {
    /**
     *  命名空间
     */
    private String namespace;

    /**
     *  需要最相似的前K条向量
     */
    private Integer topK;

    /**
     *  可用于对metadata进行过滤
     */
    private Map<String, String> filter;

    /**
     *  指示响应中是否包含向量值

     */
    private Boolean includeValues;

    /**
     *  指示响应中是否包含元数据以及id
     */
    private Boolean includeMetadata = true;

    /**
     *  查询向量
     */
    private List<Float> vector;

    /**
     *  向量稀疏数据。表示为索引列表和对应值列表，它们必须具有相同的长度
     */
    private Map<String, String> sparseVector;

    /**
     *  每条向量独一无二的id
     */
    private String id;
}
