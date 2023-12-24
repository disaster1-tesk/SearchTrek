package com.search.trek.infrastructure.client.ai.minimax.listener;

import com.search.trek.infrastructure.client.ai.minimax.entity.chat.ChatRes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
public class MiniMaxChatResListener extends EventSourceListener {
    private SseEmitter sseEmitter;
    private ChatRes chatRes;

    public MiniMaxChatResListener() {
    }

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("onOpen: " + eventSource);
        super.onOpen(eventSource, response);
    }

    @Override
    @SneakyThrows
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("receive data = {}",data);
        System.out.println("data = " + data);
    }

    @Override
    public void onClosed(EventSource eventSource) {
        log.info("onClosed = {}",eventSource);
        super.onClosed(eventSource);
    }

    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        log.info("onFailure = {}",t);
        super.onFailure(eventSource, t, response);
    }
}
