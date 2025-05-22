package com.example.phylacstoreserver.utils;

import com.example.phylacstoreserver.api.dto.UserDTO;
import com.example.phylacstoreserver.data.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DTOMapper {
	UserDTO toDTO(User user);
	User toEntity(UserDTO userDTO);
}
