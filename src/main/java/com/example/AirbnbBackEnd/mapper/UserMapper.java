package com.example.AirbnbBackEnd.mapper;

import com.example.AirbnbBackEnd.dto.ReadUserDTO;
import com.example.AirbnbBackEnd.entity.Authority;
import com.example.AirbnbBackEnd.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface UserMapper {
    ReadUserDTO readUserDTOUser(User user);
    default String mapAuthoritiesToString(Authority authority){return authority.getName();}
}
