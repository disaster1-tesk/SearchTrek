package com.search.trek.infrastructure.client.vector.pinecone.entity.vector.upset;

import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.update.UpdateReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpSertReq {
    /**
     *  需要插入的文本的向量库
     */
    private List<UpdateReq> vectors;

    /**
     *  命名空间，用于区分每个文本
     */
    private String namespace;
}
