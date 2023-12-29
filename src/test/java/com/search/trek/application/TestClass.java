package com.search.trek.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestClass {
    private List<Integer> arr;
    private String value;
    private LocalDateTime createTime;

    public TestClass(List<Integer> arr, String value) {
        this.arr = arr;
        this.value = value;
        this.createTime = LocalDateTime.now();
    }
}
