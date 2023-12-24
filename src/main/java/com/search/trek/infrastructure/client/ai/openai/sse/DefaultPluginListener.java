package com.search.trek.infrastructure.client.ai.openai.sse;

import com.search.trek.infrastructure.client.ai.openai.OpenAiStreamClient;
import com.search.trek.infrastructure.client.ai.openai.entity.chat.ChatCompletion;
import com.search.trek.infrastructure.client.ai.openai.plugin.PluginAbstract;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSourceListener;

/**
 * 描述： 插件开发返回信息收集sse监听器
 *
 * @author 
 * 2023-08-18
 */
@Slf4j
public class DefaultPluginListener extends PluginListener {

    public DefaultPluginListener(OpenAiStreamClient client, EventSourceListener eventSourceListener, PluginAbstract plugin, ChatCompletion chatCompletion) {
        super(client, eventSourceListener, plugin, chatCompletion);
    }
}
