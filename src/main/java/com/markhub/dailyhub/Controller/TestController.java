package com.markhub.dailyhub.Controller;

import com.markhub.dailyhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @GetMapping("/test")
    public Object test(){
        return userRepository.findAll();
    }

    @GetMapping("/ftl")
    public String ftl(HttpServletRequest req){
        req.setAttribute("user",userRepository.getById(1L));
        return "test";
    }
}
