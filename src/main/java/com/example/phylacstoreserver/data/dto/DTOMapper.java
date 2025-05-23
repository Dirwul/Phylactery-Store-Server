package com.example.phylacstoreserver.data.dto;

import com.example.phylacstoreserver.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DTOMapper {
	UserDTO toDTO(User user);
	User toEntity(UserDTO userDTO);
}
