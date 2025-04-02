package xyz.opcal.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import xyz.opcal.demo.dto.UserDTO;
import xyz.opcal.demo.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	UserDTO toDto(User user);

	List<UserDTO> toDtoList(List<User> users);

}
