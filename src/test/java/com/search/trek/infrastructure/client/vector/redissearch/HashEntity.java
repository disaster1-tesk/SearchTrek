package com.search.trek.infrastructure.client.vector.redissearch;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HashEntity {
    private String content;
    private List<Float> vector;
}
