package com.sniper.aiservice.service;

import com.alibaba.fastjson.JSON;
import com.sniper.aiservice.common.service.RedisService;
import com.sniper.aiservice.constant.RedisConstant;
import com.sniper.aiservice.utils.QwenUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * sniper
 * 2025/5/25 下午10:08
 */
@Slf4j
@Service
public class WeChatService {

    @Resource
    private WxMpService wxMpService;
    @Resource
    private QwenUtils qwenUtils;
    @Resource
    private RedisService redisService;

    public String authGet(String signature,
                          String timestamp,
                          String nonce,
                          String echostr) {
        log.info("微信服务器验证 -> signature:{},timestamp:{},nonce:{},echostr:{}",
                signature,timestamp,nonce,echostr);

        // 验证签名
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr; // 验证成功返回echostr
        }
        return "非法请求";
    }


    public String handleMessage(String requestBody,
                              String signature,
                              String timestamp,
                              String nonce) throws Exception {


        log.info("处理消息 -> signature:{},timestamp:{},nonce:{},requestBody:{}",
                signature, timestamp, nonce, JSON.toJSONString(requestBody));

        try {
            // 验证签名
            if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
                throw new IllegalArgumentException("签名验证失败");
            }

            // 解析用户消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            String userInput = inMessage.getContent();
            String sessionId = inMessage.getSessionId();


            // 调用Qwen获取回复
            MutablePair<String, String> mutablePair = qwenUtils.requestMyQwen(userInput, sessionId);
            log.info("ai回答 -> {} sessionId:{}", mutablePair.left, mutablePair.right);


            // 构造回复消息
            WxMpXmlOutTextMessage outMessage = WxMpXmlOutMessage.TEXT()
                    .content(mutablePair.left)
                    .fromUser(inMessage.getToUser())
                    .toUser(inMessage.getFromUser())
                    .build();

            return outMessage.toXml();
        } catch (Exception e) {
            log.info("处理消息 -> 异常:", e);
        }
        return "";
    }
}
