package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatReq implements Serializable {

    private List<Message> messages;

    /**
     * 较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定
     * 默认1.0，取值区间为[0.0,2.0]，非必要不建议使用,不合理的取值会影响效果
     * 建议该参数和top_p只设置1个，不要同时更改
     */
    @Builder.Default
    private Float temperature = 1F;

    /**
     * 影响输出文本的多样性，取值越大，生成文本的多样性越强
     * 默认1.0，取值区间为[0.0, 1.0]，非必要不建议使用, 不合理的取值会影响效果
     * 建议该参数和 temperature 只设置1个，不要同时更改
     */
    @JsonProperty("top_p")
    @Builder.Default
    private Float topP = 0.8F;


    /**
     * 是否流式输出 1：流式 0:同步
     * 注意 ：同步模式和流式模式，响应参数返回不同
     * 同步模式：响应参数为完整 json 包
     * 流式模式：响应参数为 data: {响应参数}
     */
    private Integer stream;
    /**
     * 腾讯云账号的 APPID
     */
    @JsonProperty("app_id")
    private Integer appId;

    /**
     * 官网 SecretId
     */
    @JsonProperty("secret_id")
    private String secretId;

    /**
     * 当前 UNIX 时间戳，单位为秒，可记录发起 API 请求的时间。例如1529223702，如果与当前时间相差过大，会引起签名过期错误
     */
    private Integer timestamp;

    /**
     * 签名的有效期，是一个符合 UNIX Epoch 时间戳规范的数值，单位为秒；Expired 必须大于 Timestamp 且 Expired-Timestamp 小于90天
     */
    private Integer expired;

    /**
     * 请求 ID，用于问题排查
     */
    @JsonProperty("query_id")
    private String queryId;



}
