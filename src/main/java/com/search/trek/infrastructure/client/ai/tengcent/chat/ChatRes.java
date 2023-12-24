package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.search.trek.infrastructure.client.ai.BaseRes;
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
public class ChatRes extends BaseRes implements Serializable {

    /**
     * 结果
     */
    private List<ResponseChoices> choices;

    /**
     * unix 时间戳的字符串
     */
    private String created;

    /**
     * 会话 id
     */
    private String id;


    /**
     * token 数量
     */
    private ResponseUsage usage;

    /**
     * 错误信息
     * 注意：此字段可能返回 null，表示取不到有效值
     */
    private ResponseError error;

    /**
     * 注释
     */
    private String note;

    /**
     * 唯一请求 ID，每次请求都会返回。用于反馈接口入参
     */
    private String req_id;
}
