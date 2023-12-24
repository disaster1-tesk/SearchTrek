package com.search.trek.infrastructure.client.ai.xunfei.websocket;

import com.search.trek.infrastructure.client.ai.xunfei.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

@Slf4j
public class XunFeiSparkChatWebSocket extends WebSocketClient {
    private MessageHandler messageHandler;

    public XunFeiSparkChatWebSocket(URI serverUri, MessageHandler messageHandler) {
        super(serverUri);
        this.messageHandler = messageHandler;
    }

    public XunFeiSparkChatWebSocket(URI serverUri, Draft protocolDraft, MessageHandler messageHandler) {
        super(serverUri, protocolDraft);
        this.messageHandler = messageHandler;
    }

    public XunFeiSparkChatWebSocket(URI serverUri, Map<String, String> httpHeaders, MessageHandler messageHandler) {
        super(serverUri, httpHeaders);
        this.messageHandler = messageHandler;
    }

    public XunFeiSparkChatWebSocket(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, MessageHandler messageHandler) {
        super(serverUri, protocolDraft, httpHeaders);
        this.messageHandler = messageHandler;
    }

    public XunFeiSparkChatWebSocket(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout, MessageHandler messageHandler) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        this.messageHandler.onOpen(serverHandshake);
    }

    @Override
    public void onMessage(String s) {
        this.messageHandler.onMessage(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        this.messageHandler.onClose(i, s, b);
    }

    @Override
    public void onError(Exception e) {
        this.messageHandler.onError(e);
    }
}
