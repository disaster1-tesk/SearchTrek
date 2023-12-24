package com.search.trek.infrastructure.client.ai.baidu.entity.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenReq implements Serializable {
    private String client_id;
    private String client_secret;
}
