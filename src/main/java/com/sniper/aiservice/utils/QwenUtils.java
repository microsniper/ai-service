package com.sniper.aiservice.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sniper
 * 2025/5/20 下午5:17
 */
@Component
public class QwenUtils {

    @Value("${qwen.apiKey}")
    private String apiKey;

    @Value("${qwen.model}")
    private String model;


    public String request(String content) throws Exception{
        List<Message> messages = new ArrayList<>();
        messages.add(createMessage(Role.SYSTEM, "你是一个助手，帮我回答一些技术方面或者生活方面的问题"));
        messages.add(createMessage(Role.USER, content));
        GenerationParam param = createGenerationParam(messages);
        GenerationResult result = callGenerationWithMessages(param);
        Message message = result.getOutput().getChoices().get(0).getMessage();
        messages.add(message);
        return message.getContent();
    }

    private static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }

    public GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .apiKey(apiKey)
                .model(model)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
    }

    public GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        return gen.call(param);
    }
}
