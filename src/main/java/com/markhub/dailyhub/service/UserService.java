package com.markhub.dailyhub.service;

import com.markhub.dailyhub.base.dto.UserDto;

public interface UserService {
    UserDto register(String openId);
}
