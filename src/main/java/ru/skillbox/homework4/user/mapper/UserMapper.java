package ru.skillbox.homework4.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.dto.UserDTO;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDto(User user);

    User toUser(UserDTO userDto);
}
