package com.search.trek.infrastructure.client.ai.minimax.entity.text_to_speech;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextToSpeechReq {
    private String model;
    private long audio_sample_rate;
    private long bitrate;
    private long pitch;
    private long speed;
    private String text;
    private List<TimberWeight> timber_weights;
    private String voice_id;
    private long vol;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TimberWeight {
        private String voice_id;
        private long weight;
    }
}
