package com.search.trek.infrastructure.client.ai.minimax.entity.text_to_speech;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextToSpeechRes {
    private EmbeddingRes.BaseResp base_resp;
    private String trace_id;
    private File content;
    /**
     * 音频文件下载链接
     */
    private String audio_file;
    private ExtraInfo extra_info;
    /**
     * 字幕文件下载链接
     */
    private String subtitle_file;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExtraInfo {
        /**
         * 音频时长，毫秒
         */
        private long audio_length;
        /**
         * 采样率（根据客户入参选择）
         */
        private long audio_sample_rate;
        /**
         * 音频大小，字节
         */
        private long audio_size;
        /**
         * 比特率（根据客户入参选择）
         */
        private long bitrate;
        /**
         * 非法字符占比
         */
        private double invisible_character_ratio;
        /**
         * 可读字数（不算标点等其他符号，包含汉字数字字母）
         */
        private long word_count;
    }
}
