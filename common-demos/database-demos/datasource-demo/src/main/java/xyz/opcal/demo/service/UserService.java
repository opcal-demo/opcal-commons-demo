package xyz.opcal.demo.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.opcal.demo.entity.UserEntity;
import xyz.opcal.demo.repository.UserRepository;

@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(UserEntity user) {
		userRepository.save(user);
	}

	public void saveInLambda(List<UserEntity> users) {
		users.forEach(userRepository::save);
	}

	public void saveErrorAfter(List<UserEntity> users) {
		users.forEach(userRepository::save);
		throw new RuntimeException("some error in code");
	}

	public void saveErrorInForeach(List<UserEntity> users) {
		var random = new SecureRandom();
		users.forEach(user -> {
			userRepository.save(user);
			if (random.nextInt(users.size()) % 3 == 0) {
				throw new RuntimeException("some error in code");
			}
		});
	}

	public long countAll() {
		return userRepository.count();
	}
}
