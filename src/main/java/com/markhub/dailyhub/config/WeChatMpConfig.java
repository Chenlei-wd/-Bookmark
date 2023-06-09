package com.markhub.dailyhub.config;

import com.markhub.dailyhub.handler.TextHandler;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WeChatMpConfig {
    private String mpAddId;
    private String mpAppSecret;
    private String token;

    @Autowired
    TextHandler textHandler;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage storage = new WxMpInMemoryConfigStorage();
        storage.setAppId(mpAddId);
        storage.setSecret(mpAppSecret);
        storage.setToken(token);
        return storage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService){
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 当消息是字符串时，交给textHandler处理
        router.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(textHandler).end();
        return router;
    }
}
