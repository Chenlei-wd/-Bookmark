package com.markhub.dailyhub.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;

    private String username;
    private String avatar;

    private LocalDateTime lasted;
    private LocalDateTime created;
}
