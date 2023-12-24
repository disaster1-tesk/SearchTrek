package com.search.trek.infrastructure.client.ai.baidu.entity.chat;

import com.search.trek.infrastructure.client.ai.baidu.entity.BaseResponse;
import com.search.trek.infrastructure.client.ai.baidu.entity.eb_instant.EbInstantRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRes extends BaseResponse implements Serializable {
    private long created;
    private String id;
    private boolean is_truncated;
    private boolean need_clear_history;
    private String object;
    private String result;
    private EbInstantRes.Usage usage;
    private String error_code;
    private String error_msg;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage implements Serializable{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}
