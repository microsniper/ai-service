package com.sniper.aiservice.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sniper
 * 2025/5/25 下午7:18
 */
@Configuration
public class WxMpConfig {

    @Value("${wechat.mp.appId}")
    private String appId;

    @Value("${wechat.mp.secret}")
    private String appSecret;

    @Value("${wechat.mp.token}")
    private String token;


    @Bean
    public WxMpService wxMpService() {
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(appId);
        config.setSecret(appSecret);
        config.setToken(token);

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config); // 通过 setter 注入配置
        return wxMpService;
    }
}
