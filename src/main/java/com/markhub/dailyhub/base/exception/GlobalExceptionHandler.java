package com.markhub.dailyhub.base.exception;

import cn.hutool.json.JSONUtil;
import com.markhub.dailyhub.base.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException) {
            log.error(ex.getMessage());
        } else {
            log.error(ex.getMessage(), ex);
        }
        // ajax请求
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSONUtil.toJsonStr(Result.failure(ex.getMessage())));
            } catch (IOException e) {
                // do something
            }
            return new ModelAndView();
        } else {
            request.setAttribute("message", "系统异常，请稍后再试！");
        }
        return new ModelAndView("error");
    }
}