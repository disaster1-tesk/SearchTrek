package com.search.trek.infrastructure.client.ai.tengcent.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatReqForPython implements Serializable {
    private ChatAuth chatAuth;
    private ChatReq chatReq;
}
