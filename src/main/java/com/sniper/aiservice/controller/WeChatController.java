package com.sniper.aiservice.controller;

import com.sniper.aiservice.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * sniper
 * 2025/5/25 上午12:10
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WeChatController {


    @Resource
    private WeChatService weChatService;

    /**
     * 微信服务器验证（必须实现）
     */
    @GetMapping
    public String authGet(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {

        return weChatService.authGet(signature, timestamp, nonce, echostr);
    }

    @PostMapping
    public String handleMessage (
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce) throws Exception{

        return weChatService.handleMessage(requestBody,signature,timestamp,nonce);
    }
}
