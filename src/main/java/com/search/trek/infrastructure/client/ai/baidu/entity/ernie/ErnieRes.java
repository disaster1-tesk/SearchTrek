package com.search.trek.infrastructure.client.ai.baidu.entity.ernie;

import com.search.trek.infrastructure.client.ai.baidu.entity.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErnieRes extends BaseResponse implements Serializable {
    private long created;
    private String id;
    private boolean is_truncated;
    private boolean need_clear_history;
    private String object;
    private String result;
    private Usage usage;
    private String error_code;
    private String error_msg;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage implements Serializable{
        private long completion_tokens;
        private long prompt_tokens;
        private long total_tokens;
    }
}
