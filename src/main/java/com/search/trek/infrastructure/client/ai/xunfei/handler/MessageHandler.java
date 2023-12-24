package com.search.trek.infrastructure.client.ai.xunfei.handler;

import org.java_websocket.handshake.ServerHandshake;

public interface MessageHandler {
    void onOpen(ServerHandshake serverHandshake);

    void onMessage(String s);

    void onClose(int i, String s, boolean b);

    void onError(Exception e);
}
