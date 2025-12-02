package xyz.opcal.demo.service;

import java.time.Duration;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Mono<User> userById(Long id) {
		return userRepository.findById(id);
	}

	public Flux<User> fetchUsers(int pageNum, int pageSize) {
		return userRepository.findBy(PageRequest.of(pageNum, pageSize));
	}

	public Mono<Long> count() {
		return userRepository.count();
	}

	public Flux<User> fetchAll() {
		return userRepository.findAll();
	}

	public Flux<User> fetchUser(Long id) {
		return userRepository.findById(id).delayElement(Duration.ofMillis(50)).flux();
	}

}
