package com.markhub.dailyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "m_user")
public class User implements Serializable {   //这里序列化是因为user对象要被序列化传输
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String avatar;

    private String openId;

    private LocalDateTime lasted;
    private LocalDateTime created;
}
