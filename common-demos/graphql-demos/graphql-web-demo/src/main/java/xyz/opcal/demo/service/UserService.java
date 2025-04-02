package xyz.opcal.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import xyz.opcal.demo.dto.PageDTO;
import xyz.opcal.demo.dto.UserDTO;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.mapper.UserMapper;
import xyz.opcal.demo.repository.UserRepository;

@Service
public class UserService {

	private @Autowired UserRepository userRepository;

	public UserDTO userById(Long id) {
		return userRepository.findById(id).map(UserMapper.MAPPER::toDto).orElse(null);
	}

	public PageDTO<UserDTO> fetchUsers(int pageNum, int pageSize) {
		Page<User> result = userRepository.findAll(PageRequest.of(pageNum, pageSize));
		return new PageDTO<>(UserMapper.MAPPER.toDtoList(result.getContent()), result.getTotalElements(), pageNum, pageSize);
	}

}
