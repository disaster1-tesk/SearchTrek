package com.search.trek.infrastructure.client.ai.openai.utils;

import com.search.trek.infrastructure.client.ai.openai.entity.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * 描述：token计算工具类
 *
 * @author 
 * @since 2023-04-04
 */
@Slf4j
public class TikTokensUtil {



    /**
     * 通过模型名称, 计算指定字符串的tokens
     *
     * @param modelName 模型名称
     * @param text      文本信息
     * @return tokens数量
     */
    public static int tokens(@NotNull String modelName, String text) {
        return 0;
    }


    /**
     * 通过模型名称计算messages获取编码数组
     * 参考官方的处理逻辑：
     * <a href=https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb>https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb</a>
     *
     * @param modelName 模型名称
     * @param messages  消息体
     * @return tokens数量
     */
    public static int tokens(@NotNull String modelName, @NotNull List<Message> messages) {
       return 0;
    }
}
