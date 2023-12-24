package com.search.trek.infrastructure.client.ai.tengcent.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatAuth implements Serializable {
    private Integer appId;
    private String secretId;
    private String secretKey;
}
