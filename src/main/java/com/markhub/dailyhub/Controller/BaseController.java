package com.markhub.dailyhub.Controller;

import com.markhub.dailyhub.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Resource
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;
}
