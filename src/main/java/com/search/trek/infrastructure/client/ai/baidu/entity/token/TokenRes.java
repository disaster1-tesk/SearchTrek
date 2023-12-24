package com.search.trek.infrastructure.client.ai.baidu.entity.token;

import com.search.trek.infrastructure.client.ai.baidu.entity.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes extends BaseResponse implements Serializable {
    private String access_token;
    private long expires_in;
    private String refresh_token;
    private String scope;
    private String session_key;
    private String session_secret;
}
