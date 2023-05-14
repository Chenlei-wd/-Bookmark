package com.markhub.dailyhub.mapstruct;

import com.markhub.dailyhub.base.dto.UserDto;
import com.markhub.dailyhub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);
}
