package com.markhub.dailyhub.repository;

import com.markhub.dailyhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByOpenId(String openId);
}
