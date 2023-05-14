package com.markhub.dailyhub.Controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.markhub.dailyhub.base.dto.UserDto;
import com.markhub.dailyhub.base.lang.Consts;
import com.markhub.dailyhub.base.lang.Result;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Controller
public class LoginController extends BaseController{
    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxMpMessageRouter wxMpMessageRouter;

    @GetMapping("/login")
    public String Login(HttpServletRequest req){
        //随机生成四位数
        String code = "DY" + RandomUtil.randomNumbers(4);
        //生成的验证码不会与缓存中已有的验证码冲突，从而保证数据的唯一性
        while (redisUtil.hasKey(code)){
            code = "DY" + RandomUtil.randomNumbers(4);
        }
        String ticket = RandomUtil.randomString(32);
        // 5分钟后过期
        redisUtil.set("ticket-"+code, ticket, 5*60);
        req.setAttribute("code", code);
        req.setAttribute("ticket", ticket);

        return "login";
    }

    @ResponseBody
    @GetMapping("/login-check")
    public Result loginCheck(String code, String ticket){
        if(!redisUtil.hasKey("info-" + code)){
            // 用户扫码后会将验证码和ticket存入Redis
            return Result.failure("用户未登录");
        }
        String ticketBak = redisUtil.get("ticket-" + code).toString();
        if (!ticketBak.equals(ticket)){
            return Result.failure("登录失败");
        }
        // 这里能取到userJson说明用户已经注册登录成功了，在LoginHandler中
        String userJson = redisUtil.get("info-" + code).toString();
        UserDto userDto = JSONUtil.toBean(userJson, UserDto.class);

        // 将用户信息存入session
        req.getSession().setAttribute(Consts.CURRENT_USER, userDto);
        System.out.println(userDto);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/wx/back")
    public String wxCallBack(String signature, String timestamp, String nonce, String echostr) throws IOException {
        if (StrUtil.isNotBlank(echostr)){
            log.info("正在配置回调接口---",echostr);
            return echostr;
        }
        boolean checkSignature = wxMpService.checkSignature(timestamp, nonce, signature);
        if(!checkSignature){
            log.error("前面不合法");
            return null;
        }
        // 处理消息
        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(req.getInputStream());
        // 根据消息 路由到各种handler
        WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(wxMpXmlMessage);
        return outMessage == null ? "" : outMessage.toXml();
    }

    @GetMapping("/autologin")
    public String autologin(String token){

        Object userobj = redisUtil.get("autologin-" + token);
        if(userobj != null){
            UserDto userDto = JSONUtil.toBean(userobj.toString(), UserDto.class);
            req.getSession().setAttribute(Consts.CURRENT_USER, userDto);
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
