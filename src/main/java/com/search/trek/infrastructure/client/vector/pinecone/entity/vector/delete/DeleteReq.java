package com.search.trek.infrastructure.client.vector.pinecone.entity.vector.delete;

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
public class DeleteReq {
    private List<String> ids;

    private boolean deleteAll;

    private String namespace;

    private Map<String, String> filter;
}
