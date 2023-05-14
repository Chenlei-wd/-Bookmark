package com.markhub.dailyhub.handler;

import cn.hutool.core.util.StrUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextHandler implements WxMpMessageHandler {

    @Autowired
    LoginHandler loginHandler;



    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 用户的身份ID
        String openId = wxMpXmlMessage.getFromUser();
        // 用户输入的内容
        String content = wxMpXmlMessage.getContent();

        String result = "无法识别字符串！";

        if(StrUtil.isNotBlank(content)){
            content = content.toUpperCase().trim();
            if(content.indexOf("DY") == 0){
                // 交给登录处理器
                result = loginHandler.handle(openId, content, wxMpService);
            }
        }
        return WxMpXmlOutMessage.TEXT()
                .content(result)
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser())
                .build();
    }
}
