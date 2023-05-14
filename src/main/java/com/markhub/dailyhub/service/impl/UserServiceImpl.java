package com.markhub.dailyhub.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.markhub.dailyhub.base.dto.UserDto;
import com.markhub.dailyhub.entity.User;
import com.markhub.dailyhub.mapstruct.UserMapper;
import com.markhub.dailyhub.repository.UserRepository;
import com.markhub.dailyhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto register(String openId) {
        // 判断用户是否已经注册过
        Assert.isTrue(openId != null, "不合法注册条件！");
        User user = userRepository.findByOpenId(openId);
        if(user == null){
            user = new User();
            user.setUsername("Hub-" + RandomUtil.randomString(5));
            user.setAvatar("http://localhost:8080/images/logo.jpeg");
            user.setCreated(LocalDateTime.now());
            user.setOpenId(openId);
        } else {
            user.setLasted(LocalDateTime.now());
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
